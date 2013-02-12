import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class ContactManagerImpl implements ContactManager, Serializable
{
    private File dataOnDisk = new File("./contacts.txt");
    private List<Contact> contactsList = new ArrayList<Contact>();

    public ContactManagerImpl(){}    // empty constructor to comply with Serialization specification

    public ContactManagerImpl(List<Contact> list)
    {
        this.contactsList = list;
    }

    public int addFutureMeeting(Set<Contact> contacts, Calendar date)
    {
            return 0;
    }

    public PastMeeting getPastMeeting(int id)
    {
             return null;
    }

    public FutureMeeting getFutureMeeting(int id)
    {
               return null;
    }

    public Meeting getMeeting(int id)
    {
                return null;
    }

    public List<Meeting> getFutureMeetingList(Contact contact)
    {
                 return null;
    }

    public List<Meeting> getFutureMeetingList(Calendar date)
    {
                   return null;
    }

    public List<PastMeeting> getPastMeetingList(Contact contact)
    {
                  return null;
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
         *  to the current size of the ArrayList */
        int uniqueId = this.contactsList.size();
        Contact tmp = new ContactImpl(name, notes, uniqueId);    // construct a Contact object by calling ContactImpl constructor
        contactsList.add(tmp);
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

            objectOut.writeObject(contactsList);      // writes the ArrayList containing contacts to disk
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
        try
        {
            ObjectInputStream objectIn =
                    new ObjectInputStream(                                      // written over several lines
                            new BufferedInputStream(                            // for extra clarity
                                    new FileInputStream(dataOnDisk)));

            List<Contact> contactsList = (ArrayList<Contact>) objectIn.readObject();
            objectIn.close();

            ContactManager tmp = new ContactManagerImpl(contactsList);
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
