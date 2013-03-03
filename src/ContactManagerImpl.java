import java.io.*;
import java.util.*;

public class ContactManagerImpl implements ContactManager, Serializable
{
    private File dataOnDisk = new File("./contacts.txt");
    private Set<Contact> contactSet = new HashSet<Contact>();
    private Set<Meeting> meetingSet = new HashSet<Meeting>();
    public static boolean firstRun = true;

    /** First-run constructor which creates empty sets for meetings and contacts
     *  and immediately saves them to disk, so that load() can call the second constructor
     *  in the future. @param firstRun tells load() if this is the first run */
    public ContactManagerImpl()
    {
        this.contactSet = new HashSet<Contact>();
        this.meetingSet = new HashSet<Meeting>();
        firstRun = false;
        this.flush();
    }

    public ContactManagerImpl(Set<Contact> cset, Set<Meeting> mset)
    {
        this.contactSet = cset;
        this.meetingSet = mset;
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
        FutureMeeting tmp = new FutureMeetingImpl(contacts, date);
        meetingSet.add(tmp);
        /** @return the ID for the meeting by calling getId() */
        System.out.println("Success - Meeting Scheduled!");
        return tmp.getId();
    }

    public PastMeeting getPastMeeting(int id)
    {
        char flag = 'p';                        // 'p' for past meeting
        Meeting meeting = new MeetingImpl();
        meeting = ((MeetingImpl)meeting).returnMeeting(meetingSet, id, flag);      // call the method in MeetingImpl
        return (PastMeeting) meeting;        // cast to correct type on return
    }

    public FutureMeeting getFutureMeeting(int id)
    {
        char flag = 'f';                        // 'f' for future meeting
        Meeting meeting = new MeetingImpl();
        meeting = ((MeetingImpl)meeting).returnMeeting(meetingSet, id, flag);       // call the method in MeetingImpl
        return (FutureMeeting) meeting;     // cast to correct type on return
    }

    public Meeting getMeeting(int id)
    {
        char flag = 'm';                        // 'm' for simply meeting
        Meeting meeting = new MeetingImpl();
        meeting = ((MeetingImpl)meeting).returnMeeting(meetingSet, id, flag);       // call the method in MeetingImpl
        return meeting;                     // no need for casting here
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
            List<Meeting> list = MeetingImpl.returnMeetingList(meetingSet, 'f', contact);
            /** call custom comparator in MeetingImpl to chronologically sort */
            Collections.sort(list, MeetingImpl.MeetingComparator);
            return list;
        }

    }

    public List<Meeting> getFutureMeetingList(Calendar date)
    {
        /** @param list a list to store any matching Meetings; will be returned empty if no matches */
        List<Meeting> list = MeetingImpl.returnMeetingList(meetingSet, 'f', date);
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
            /** @param list a list to store any matching Meetings; will be returned empty if no matches */
            List<Meeting> list = MeetingImpl.returnMeetingList(meetingSet, 'f', contact);
            /** although all elements are of type PastMeeting, list is returned as type Meeting,
             so we must populate a new list of PastMeeting in order to return */
            List<PastMeeting> pmList = new ArrayList<PastMeeting>();
            for (Meeting m : list)
            {
                pmList.add((PastMeeting) m);
            }
            /** call custom comparator in MeetingImpl to chronologically sort */
            Collections.sort(pmList, MeetingImpl.MeetingComparator);
            return pmList;
        }
    }

    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text)
    {

    }

    public void addMeetingNotes(int id, String text)
    {

    }

    public void addNewContact(String name, String notes)
    {
        /** @param uniqueId a unique Id constructed by adding 1
         *  to the current size of the HashSet */
        int uniqueId = this.contactSet.size();
        Contact tmp = new ContactImpl(name, notes, uniqueId);    // construct a Contact object by calling ContactImpl constructor
        contactSet.add(tmp);
    }

    public Set<Contact> getContacts(int... ids)
    {
         return null;
    }

    public Set<Contact> getContacts(String name)
    {
            return null;
    }

    public void flush()
    {
        try
        {
            ObjectOutputStream objectOut =
                    new ObjectOutputStream(                                        // written over several lines
                            new BufferedOutputStream(                              // for extra clarity
                                    new FileOutputStream(dataOnDisk)));

            objectOut.writeObject(contactSet);      // writes the HashSet containing contacts to disk
            objectOut.writeObject(meetingSet);      // writes the HashSet containing meetings to disk
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

    public ContactManager load()
    {
        if (firstRun)
        {
            ContactManager tmp = new ContactManagerImpl();
            return tmp;
        }
        else
        {
            try
            {
                ObjectInputStream objectIn =
                        new ObjectInputStream(                                      // written over several lines
                                new BufferedInputStream(                            // for extra clarity
                                        new FileInputStream(dataOnDisk)));

                Set<Contact> contactSet = (HashSet<Contact>) objectIn.readObject();      // read the HashSet containing contacts from disk
                Set<Meeting> meetingSet = (HashSet<Meeting>) objectIn.readObject();      // read the HashSet containing meetings from disk
                objectIn.close();

                ContactManager tmp = new ContactManagerImpl(contactSet, meetingSet);
                /** @return a ContactManager object loaded with the sets of meetings and contacts from disk */
                return tmp;
            }
            catch (FileNotFoundException fnfex)
            {
                System.err.println("Contacts.txt file not found. Please make sure directory is readable and/or " +
                        "\nthat you have flushed at least once previously, and then try again");
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
            return null;
        }
    }
}
