import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ContactManagerImpl implements ContactManager, Serializable
{
    private static final File dataOnDisk = new File("./contacts.txt");
    public Set<Contact> contactSet = new HashSet<Contact>();
    public Set<Meeting> meetingSet = new HashSet<Meeting>();
    public List<FutureMeeting> futureMeetings = new ArrayList<FutureMeeting>();
    public List<PastMeeting> pastMeetings = new ArrayList<PastMeeting>();
    /** @param firstRun a flag so the program can distinguish between contacts.txt being absent due to a first run OR an error.
     *  In full program user would call program with command line options -n [short] or --new [long], which would
     *  set firstRun to true before calling the constructor. In this case brand new sets and lists would be created and
     *  a FileNotFoundException would not be thrown. If user does not launch program with one of the command line flags,
     *  the error message of the FileNotFoundException informs them to do so if this is their first time */
    private static boolean firstRun = false;

    public ContactManagerImpl()
    {
       /** method load reads in data objects from disk (or instantiates new ones) */
        this.load();
    }

    public int addFutureMeeting(Set<Contact> contacts, Calendar date)
    {
        /** @param currentDate an instance of Calendar to get the
         current date in order to see if the date provided is valid */
        Calendar currentDate = GregorianCalendar.getInstance();
        if (currentDate.after(date))       // i.e if user's date is in the past
        {
            throw new IllegalArgumentException("Specified date is in the past! Please try again.");
        }
        for (Iterator<Contact> itr = contacts.iterator(); itr.hasNext();)
        {
            if (!contactSet.contains(itr.next()))      // if contactSet does NOT contain itr.next()
            {
                throw new IllegalArgumentException("Contact \"" + itr.next().getName() + "\" does not exist! Please try again.");
            }
        }
        /** if neither exception thrown, FutureMeeting object can be instantiated */
        FutureMeeting tmp = new FutureMeetingImpl(meetingIdAssigner(), contacts, date);
        meetingSet.add(tmp);
        futureMeetings.add(tmp);
        /** @return the ID for the meeting by calling getId() */
        System.out.println("Success - Meeting Scheduled!");
        return tmp.getId();
    }

    /** used to get a unique id for a newly created meeting, based on meetingSet.size(), as meetingSet is
     *  not visible from MeetingImpl. @return id to pass into the MeetingImpl constructor*/
    private int meetingIdAssigner()
    {
        int uniqueId = (meetingSet.size() + 1);
        return uniqueId;
    }

    public PastMeeting getPastMeeting(int id)
    {
        char flag = 'p';                        // 'p' for past meeting
        return (PastMeeting)MeetingImpl.returnMeeting(meetingSet, id, flag);        // cast to correct type on return
    }

    public FutureMeeting getFutureMeeting(int id)
    {
        char flag = 'f';                        // 'f' for future meeting
        return (FutureMeeting)MeetingImpl.returnMeeting(meetingSet, id, flag);     // cast to correct type on return
    }

    public Meeting getMeeting(int id)
    {
        char flag = 'm';                        // 'm' for simply meeting
        return MeetingImpl.returnMeeting(meetingSet, id, flag);                     // no need for casting here
    }

    public List<Meeting> getFutureMeetingList(Contact contact)
    {
        /** @throws IllegalArgumentException if the contact does not exist */
        if (!contactSet.contains(contact))
        {
            throw new IllegalArgumentException("Contact \"" + contact.getName() + "\" does not exist! Please try again");
        }
        else
        {
            /** @param list a list to store any matching Meetings; will be returned empty if no matches */
            List<Meeting> list = new ArrayList<Meeting>();
            for (Meeting m : meetingSet)
            {
                if (m.getContacts().contains(contact))
                {
                    /** each time a matching Meeting is found, it is added to the list. */
                    list.add(m);
                }
            }
            /** call custom comparator in MeetingImpl to chronologically sort */
            Collections.sort(list, MeetingImpl.MeetingComparator);
            return list;
        }
    }

    /** THIS METHOD GETS BOTH PAST AND FUTURE MEETINGS DEPENDING ON DATE GIVEN */
    public List<Meeting> getFutureMeetingList(Calendar date)
    {
        /** @param list a list to store any matching Meetings; will be returned empty if no matches */
        List<Meeting> list = new ArrayList<Meeting>();

        for (Meeting m : meetingSet)
        {
            if (m.getDate().equals(date))
            {
                /** each time a matching Meeting is found, it is added to the list. */
                list.add(m);
            }
        }
        /** call custom comparator in MeetingImpl to chronologically sort */
        Collections.sort(list, MeetingImpl.MeetingComparator);
        return list;
    }

    public List<PastMeeting> getPastMeetingList(Contact contact)
    {
        /** @throws IllegalArgumentException if the contact does not exist */
        if (!contactSet.contains(contact))
        {
            throw new IllegalArgumentException("Contact \"" + contact.getName() + "\" does not exist! Please try again");
        }
        else
        {
            /** @param list a list to store any matching PastMeetings; will be returned empty if no matches */
            List<PastMeeting> list = new ArrayList<PastMeeting>();

            for (PastMeeting pm : pastMeetings)
            {
                if (pm.getContacts().contains(contact))
                {
                    list.add(pm);
                }
            }
            /** call custom comparator in MeetingImpl to chronologically sort */
            Collections.sort(list, MeetingImpl.MeetingComparator);
            return list;
        }
    }

    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text)
    {
        if (contacts.isEmpty() || !contactSet.containsAll(contacts))
        {
            throw new IllegalArgumentException("One or more Contacts do not exist OR set is empty");
        }
        else if (contacts == null || date == null || text == null)
        {
            throw new NullPointerException("One or more arguments are null");
        }
        else
        {
            PastMeeting pastMeeting = new PastMeetingImpl(meetingIdAssigner(), contacts, date);
            /** use method addNotes to add notes to avoid unnecessary code duplication */
            ((PastMeetingImpl)pastMeeting).addNotes(text);
            meetingSet.add(pastMeeting);                           // add to main meeting set AFTER notes are added
            pastMeetings.add(pastMeeting);                         // add to list of past meetings AFTER notes are added
        }
    }

    /** This method is used when a future meeting takes place, and is then converted to a past meeting (with notes), or to
     * add notes to a past meeting at a later date. @throws IllegalArgumentException if specified meeting is null OR not found */
    public void addMeetingNotes(int id, String text)
    {
        Set<Contact> errSet = new HashSet<Contact>();     // to use for our empty convertedMeeting, to avoid NullPException
        Meeting meeting = getMeeting(id);
        Calendar presentDate = new GregorianCalendar();
        if (meeting == null)
        {
            throw new IllegalArgumentException("Specified meeting does not exist!");
        }
        else if (text == null)
        {
            throw new NullPointerException("Cannot add null string of notes");
        }
        else if (meeting.getDate().after(presentDate))
        {
            throw new IllegalStateException("Meeting set for date in the future - not eligible for conversion!");
        }
        else if (meeting instanceof FutureMeeting)      // we know it's a future meeting needing conversion
        {
           /** @param convertedMeeting name to indicate the original FutureMeeting type is now a PastMeeting
            * the 0 id field (an impossible id) is a flag to let us know whether or not it goes through the for loop if statement */
            PastMeeting convertedMeeting = new PastMeetingImpl(0, errSet, null);
            for (FutureMeeting fm : futureMeetings)
            {
                if (fm.getId() == id)
                {
                    convertedMeeting = new PastMeetingImpl(fm.getId(), fm.getContacts(), fm.getDate());
                }
            }
            if (convertedMeeting.getId() == 0)   // we haven't been through the for loop and/or if statement, so no point adding
            {
                throw new IllegalArgumentException("Couldn't find meeting and/or list of meetings!");
            }
            else  // we know that convertedMeeting has been through the for loop and/or if statement, so can add it to our set/list
            {
                meetingSet.remove(meeting);
                futureMeetings.remove(meeting);
                pastMeetings.add(convertedMeeting);
                meetingSet.add(convertedMeeting);
                addMeetingNotes(convertedMeeting.getId(), text);        // add the notes
            }
        }
        else if (meeting instanceof PastMeeting)    // this will catch cases where we just want to add notes to a PastMeeting (including the convertedMeeting)
        {
            for (PastMeeting pm : pastMeetings)
            {
                if (pm.getId() == id)
                {
                    meetingSet.remove(meeting);
                    pastMeetings.remove(pm);
                    ((PastMeetingImpl)pm).addNotes(text);
                    pastMeetings.add(pm);
                    meetingSet.add(pm);
                }
            }


            /** @param updatedMeeting name to indicate the updated PastMeeting object which will now have notes *//*
            PastMeeting updatedMeeting = getPastMeeting(id);
            ((PastMeetingImpl)updatedMeeting).addNotes(text);              // add notes to updatedMeeting
            meetingSet.remove(meeting);                                    // remove old. note-less meeting from meeting set
            meetingSet.add(updatedMeeting);                  // add the updated meeting back to meeting set
            pastMeetings.remove(meeting);                                  // remove the old meeting from list of past meetings
            pastMeetings.add(updatedMeeting);               // add our new PastMeeting to the past meetings list*/
        }
    }

    public void addNewContact(String name, String notes)
    {
        /** @param uniqueId a unique Id constructed by adding 1
         *  to the current size of the HashSet */
        int uniqueId = (this.contactSet.size() + 1);
        Contact tmp = new ContactImpl(name, notes, uniqueId);    // construct a Contact object by calling ContactImpl constructor
        contactSet.add(tmp);                                     // add to set of contacts
    }

    public Set<Contact> getContacts(int... ids)
    {
        boolean isRealId = false;             /** @param isRealId stores whether or not we found a contact with the id */
        int offendingId = 0;                  /** @param offendingId stores the id that does not correspond to a real contact */
        Set<Contact> setToReturn = new HashSet<Contact>();
        if (contactSet.isEmpty())
        {
            throw new NullPointerException("No Contacts in set!");
        }
        else
        {
            for (int id : ids)
            {
                for (Contact contact : contactSet)
                {
                    if (id == contact.getId())
                    {
                        isRealId = true;
                        setToReturn.add(contact);
                        break;
                    }
                    else
                        isRealId = false;
                        offendingId = id;
                }
                if (!isRealId)
                {
                    throw new IllegalArgumentException("Contact with id " + offendingId + " does not exist");
                }
            }
            return setToReturn;
        }
    }

    public Set<Contact> getContacts(String name)
    {
        /** @param lowerCaseContactName, @param lowerCaseInput - I convert both strings into lower case before the string
         *  comparison, so it does not matter if user gives this method "ann" when they want to find "Ann Smith" */
        String lowerCaseContactName;
        String lowerCaseInput = name.toLowerCase();
        Set<Contact> setToReturn = new HashSet<Contact>();

        if (name == null)
        {
            throw new NullPointerException("Cannot compare contact names against a null string");
        }
        else
        {
            for (Contact contact : contactSet)
            {
                lowerCaseContactName = contact.getName().toLowerCase();
                if (lowerCaseContactName.contains(lowerCaseInput))       // we have a match!
                    setToReturn.add(contact);
            }
        }
        return setToReturn;
    }

    public void flush()
    {
        try
        {
           /** clear data from file so we write the most up-to-date, canonical data structures,
            *  not merely appending which would result in duplicated or old data being read in */
            //dataOnDisk.delete();
            ObjectOutputStream objectOut =
                    new ObjectOutputStream(                                        // written over several lines
                            new BufferedOutputStream(                              // for extra clarity
                                    new FileOutputStream(dataOnDisk)));

            objectOut.writeObject(this.contactSet);      // writes the HashSet containing contacts to disk
            objectOut.writeObject(this.meetingSet);      // writes the HashSet containing meetings to disk
            objectOut.writeObject(this.pastMeetings);
            objectOut.writeObject(this.futureMeetings);
            objectOut.close();
        }
        catch (FileNotFoundException fnfex)
        {
            System.err.println("Contacts.txt file not found. Please make sure directory is writeable and try again");
        }
        catch (IOException ioex)
        {
            System.err.println("Problem writing to disk. See stack trace for details and/or please try again");
            ioex.printStackTrace();
        }
    }

    public void load()
    {
        if (firstRun || !dataOnDisk.exists())           // temporarily added hack to fix serialization until i solve main issue
        {
            /** make new empty sets and call the other constructor; for when dataOnDisk doesn't exist
             *  not due to error, but because program is being run for the first time */
            this.contactSet = new HashSet<Contact>();
            this.meetingSet = new HashSet<Meeting>();
            this.pastMeetings = new ArrayList<PastMeeting>();
            this.futureMeetings = new ArrayList<FutureMeeting>();
            this.flush();           // immediately flush to create contacts.txt
            firstRun = false;       // set firstRun to false now that we have created new data structures and flushed
        }
        else
        {
            try
            {
                ObjectInputStream objectIn =
                        new ObjectInputStream(                                      // written over several lines
                                new BufferedInputStream(                            // for extra clarity
                                        new FileInputStream(dataOnDisk)));

                this.contactSet = (HashSet<Contact>) objectIn.readObject();         // read the HashSet containing contacts from disk
                this.meetingSet = (HashSet<Meeting>) objectIn.readObject();         // read the HashSet containing meetings from disk
                this.pastMeetings = (ArrayList<PastMeeting>) objectIn.readObject();
                this.futureMeetings = (ArrayList<FutureMeeting>) objectIn.readObject();
                objectIn.close();
                dataOnDisk.delete();        // temporarily added hack to fix serialization until i solve main issue

            }
            catch (FileNotFoundException fnfex)
            {
                System.err.println("Contacts.txt file not found. Please make sure directory is readable and/or " +
                        "\nthat you have flushed at least once previously, and then try again. If this is your first " +
                        "\nrun of the program, please run again with flag '-n' or '--new'");
            }
            catch (ClassNotFoundException cnfex)
            {
                System.err.println("Could not load a required class. Please make sure directory is readable and/or " +
                        "\nthat you have flushed at least once previously, and then try again." +
                        "\n If you are working in a different directory, make sure your CLASSPATH includes the required class:\n\n");
                System.out.print(cnfex.getCause().toString());       // will hopefully print the class(es) that caused the exception
            }
            catch (IOException ioex)
            {
                System.err.println("Problem writing to disk. See stack trace for details and/or please try again");
                ioex.printStackTrace();
            }
        }
    }
}
