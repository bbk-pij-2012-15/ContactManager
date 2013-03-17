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
import java.util.Scanner;
import java.util.Set;

public class ContactManagerImpl implements ContactManager, Serializable
{
    private static File dataOnDisk = new File("./contacts.txt");
    public Set<Contact> contactSet = new HashSet<Contact>();
    private Set<Meeting> meetingSet = new HashSet<Meeting>();
    private List<FutureMeeting> futureMeetings = new ArrayList<FutureMeeting>();
    private List<PastMeeting> pastMeetings = new ArrayList<PastMeeting>();

    public ContactManagerImpl()
    {
        /** contacts.txt is found on disk and we ask user whether to use the file to load their ContactManager
         *  OR whether they wish to create a new ContactManager with empty contacts and meeting data */
        if (dataOnDisk.exists())
        {
            System.out.println("contacts.txt found. Would you like to use what is already in it" +
                    " to load your Contact Manager? [y/n]");
            Scanner in = new Scanner(System.in);
            char answer = in.next().charAt(0);
            if (answer == 'y' || answer == 'Y')     // i.e. they want to use the old data
            {
                this.load();       // we can call load() now to load the data from contacts.txt
            }
            else          // i.e. they want to start afresh with an empty Contact Manager
            {
                /** immediately flush() the empty data fields created with the ContactManager object to overwrite contacts.txt */
                System.out.println("Creating fresh data structures...");
                this.flush();
                this.load();     // re-load with user's requested fresh data sets
            }
        }
        /** contacts.txt is NOT found on disk and we ask user whether this is their first time running the program
         *  if it is, we can just flush new, empty data structures. if not, we know there has been an error */
        else if (!dataOnDisk.exists())
        {
            System.out.println("contacts.txt not found. Is this your first run of the program? [y/n]");
            Scanner in = new Scanner(System.in);
            char answer = in.next().charAt(0);
            if (answer == 'y' || answer == 'Y')     // i.e. it IS their first run of program
            {
                /** immediately flush() the empty data fields created with the ContactManager object to create contacts.txt */
                System.out.println("Creating empty data structures for first run of program...");
                this.flush();
                this.load();      // load from disk with empty data sets
            }
            else // i.e. it is NOT their first run, so some sort of error has occurred
            {
                /** call load(), which will throw a FileNotFoundException, giving user more information and possible solutions */
                this.load();
            }
        }
    }

    public int addFutureMeeting(Set<Contact> contacts, Calendar date)
    {
        /** @param currentDate an instance of Calendar to get the
         *  current date in order to see if the date provided is valid */
        Calendar currentDate = GregorianCalendar.getInstance();
        if (currentDate.after(date))            // i.e if date param is in the past
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
        System.out.println("Success - Meeting Scheduled!");
        /** @return the ID for the meeting by calling the getId() method of FutureMeetingImpl */
        return tmp.getId();
    }

    /** used to get a unique id for a newly created meeting, based on current size of meetingSet.
     *  This is because meetingSet is not visible from MeetingImpl, being a private field */
    private int meetingIdAssigner()
    {
        /** @return id to pass into the MeetingImpl constructor */
        return (meetingSet.size() + 1);
    }

    /** @see MeetingImpl#returnMeeting(java.util.Set, int, char) */
    public PastMeeting getPastMeeting(int id)
    {
        char flag = 'p';   // 'p' for past meeting
        /** @return Meeting object from returnMeeting(), cast into a PastMeeting */
        return (PastMeeting)MeetingImpl.returnMeeting(meetingSet, id, flag);
    }

    /** @see MeetingImpl#returnMeeting(java.util.Set, int, char) */
    public FutureMeeting getFutureMeeting(int id)
    {
        char flag = 'f';   // 'f' for future meeting
        /** @return Meeting object from returnMeeting(), cast into a FutureMeeting */
        return (FutureMeeting)MeetingImpl.returnMeeting(meetingSet, id, flag);
    }

    /** @see MeetingImpl#returnMeeting(java.util.Set, int, char) */
    public Meeting getMeeting(int id)
    {
        char flag = 'm';   // 'm' for simply meeting
        /** @return Meeting object from returnMeeting(), with no need for casting */
        return MeetingImpl.returnMeeting(meetingSet, id, flag);
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
            /** @see MeetingImpl#MeetingComparator - calls custom comparator in MeetingImpl to chronologically sort */
            Collections.sort(list, MeetingImpl.MeetingComparator);
            return list;
        }
    }

    /** THIS METHOD GETS BOTH PAST AND FUTURE MEETINGS DEPENDING ON DATE GIVEN - see FT forum */
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
        /** @see MeetingImpl#MeetingComparator - calls custom comparator in MeetingImpl to chronologically sort */
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
            /** @see MeetingImpl#MeetingComparator - calls custom comparator in MeetingImpl to chronologically sort */
            Collections.sort(list, MeetingImpl.MeetingComparator);
            return list;
        }
    }

    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text)
    {
        Calendar now = new GregorianCalendar();
        if (contacts.isEmpty() || !contactSet.containsAll(contacts))
        {
            throw new IllegalArgumentException("One or more Contacts do not exist OR set is empty");
        }
        else if (contacts == null || date == null || text == null)
        {
            throw new NullPointerException("One or more arguments are null");
        }
        else if (date.after(now))
        {
            throw new IllegalArgumentException("Cannot create a past meeting with a future date!");
        }
        else    // an exception hasn't been thrown, so we can add the meeting
        {
            PastMeeting pastMeeting = new PastMeetingImpl(meetingIdAssigner(), contacts, date);
            /** @see PastMeetingImpl#addNotes(String) use this method to add notes to avoid unnecessary code duplication */
            ((PastMeetingImpl)pastMeeting).addNotes(text);
            meetingSet.add(pastMeeting);                           // add to main meeting set AFTER notes are added
            pastMeetings.add(pastMeeting);                         // add to list of past meetings AFTER notes are added
        }
    }

    /** This method is used EITHER: when a future meeting happens, and is then converted to a past meeting (with notes),
     *                          OR: to add notes to a past meeting at a date after its creation. */
    public void addMeetingNotes(int id, String text)
    {
        Meeting meeting = getMeeting(id);       // find meeting by id - will be null if doesn't exist

        if (meeting == null)
        {
            throw new IllegalArgumentException("Specified meeting does not exist!");
        }
        else if (text == null)
        {
            throw new NullPointerException("Cannot add null string of notes");
        }
        else if (((MeetingImpl)meeting).inFuture() == true)
        {
            throw new IllegalStateException("Meeting set for date in the future - not eligible for conversion!");
        }
        else if (meeting instanceof FutureMeeting)      // we know it's a future meeting needing conversion
        {
           /** @param convertedMeeting name to indicate the original FutureMeeting type is now a PastMeeting
            *  the 0 id field (an impossible id) is a flag to let us know whether or not it goes through the for loop if statement */
            Set<Contact> set = new HashSet<Contact>();     // to use for our empty convertedMeeting, to avoid a NullPointerException
            PastMeeting convertedMeeting = new PastMeetingImpl(0, set, null);
            for (FutureMeeting fm : futureMeetings)
            {
                if (fm.getId() == id)
                {
                    /** convertedMeeting is now re-constructed with proper values, which shows that we have an id match */
                    convertedMeeting = new PastMeetingImpl(fm.getId(), fm.getContacts(), fm.getDate());
                }
            }
            if (convertedMeeting.getId() == 0)   // i.e. we haven't had an id match (got into the for loop if statement)
            {
                throw new IllegalArgumentException("Couldn't find meeting in list of meetings!");
            }
            else  // we know that convertedMeeting has been through the for loop if statement, so can add it to our sets/lists
            {
                meetingSet.remove(meeting);                    // remove the old FutureMeeting from main meeting set
                futureMeetings.remove(meeting);                // remove the old FutureMeeting from list of future meetings
                pastMeetings.add(convertedMeeting);            // add the new PastMeeting to list of past meetings
                meetingSet.add(convertedMeeting);              // add the new PastMeeting to main meeting set
                /** here we call this method again to add the notes to our new PastMeeting object,
                 *  knowing it will drop through to the else if below (as it is now an instanceof PastMeeting) */
                addMeetingNotes(convertedMeeting.getId(), text);
            }
        }
        else if (meeting instanceof PastMeeting)    // this will catch cases where we just want to add notes to a PastMeeting (including the convertedMeeting)
        {
            for (PastMeeting pm : pastMeetings)
            {
                if (pm.getId() == id)
                {
                    meetingSet.remove(meeting);            // remove the old PastMeeting (without new note) from list of past meetings
                    pastMeetings.remove(pm);               // add the new PastMeeting (without new note) from list of past meetings
                    /** @see PastMeetingImpl#addNotes(String) use this method to actually add notes to avoid unnecessary code duplication */
                    ((PastMeetingImpl)pm).addNotes(text);
                    pastMeetings.add(pm);                  // add the new PastMeeting (with new note) to list of past meetings
                    meetingSet.add(pm);                    // add the new PastMeeting (with new note) to main meeting set
                }
                else
                {
                    throw new IllegalArgumentException("Couldn't find meeting in list of meetings!");
                }
            }
        }
    }

    public void addNewContact(String name, String notes)
    {
        /** @param uniqueId a unique Id constructed by adding 1
         *  to the current size of the HashSet containing contacts */
        if (name == null)
        {
            throw new NullPointerException("Cannot add contact with a null name!");
        }
        else if (notes == null)
        {
            throw new NullPointerException("Cannot add contact with null notes!");
        }

        int uniqueId = (this.contactSet.size() + 1);
        Contact tmp = new ContactImpl(name, notes, uniqueId);    // construct a Contact object by calling ContactImpl constructor
        contactSet.add(tmp);                                     // add to set of contacts
    }

    public Set<Contact> getContacts(int... ids)
    {
        boolean isRealId = false;             /** @param isRealId stores whether or not we found a contact with the id */
        int offendingId = 0;                  /** @param offendingId stores an id that does not correspond to a real contact */
        Set<Contact> setToReturn = new HashSet<Contact>();
        if (contactSet.isEmpty())
        {
            throw new NullPointerException("No Contacts in Contact Manager!");
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
            ObjectOutputStream objectOut =
                    new ObjectOutputStream(                                        // written over several lines
                            new BufferedOutputStream(                              // for extra clarity
                                    new FileOutputStream(dataOnDisk)));

            objectOut.writeObject(this.contactSet);       // write the HashSet containing contacts to disk
            objectOut.writeObject(this.meetingSet);       // write the HashSet containing meetings to disk
            objectOut.writeObject(this.pastMeetings);     // write the ArrayList containing past meetings to disk
            objectOut.writeObject(this.futureMeetings);   // write the ArrayList containing future meetings to disk
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
        try
        {
            ObjectInputStream objectIn =
                    new ObjectInputStream(                                      // written over several lines
                            new BufferedInputStream(                            // for extra clarity
                                    new FileInputStream(dataOnDisk)));

            this.contactSet = (HashSet<Contact>) objectIn.readObject();              // read the HashSet containing contacts from disk
            this.meetingSet = (HashSet<Meeting>) objectIn.readObject();              // read the HashSet containing meetings from disk
            this.pastMeetings = (ArrayList<PastMeeting>) objectIn.readObject();      // read the ArrayList of past meetings from disk
            this.futureMeetings = (ArrayList<FutureMeeting>) objectIn.readObject();  // read the ArrayList of future meetings from disk
            objectIn.close();
        }
        catch (FileNotFoundException fnfex)
        {
            System.err.println("Contacts.txt file not found! Please make sure file and/or directory is readable, and that" +
                    "\nthat you are in the correct directory, and then try again.\n");
            fnfex.printStackTrace();
        }
        catch (ClassNotFoundException cnfex)
        {
            System.err.println("Could not load a required class. Please make sure directory is readable and/or " +
                    "\nthat you have flushed at least once previously, and then try again." +
                    "\n If you are working in a different directory, make sure your $CLASSPATH includes the required class:\n\n");
            System.out.print(cnfex.getCause().toString());       // will hopefully print the class(es) that caused the exception
        }
        catch (IOException ioex)
        {
            System.err.println("Problem writing to disk. See stack trace for details and/or please try again");
            ioex.printStackTrace();
        }
    }
}
