import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class ContactManagerImpl implements ContactManager, Serializable
{
    private File dataOnDisk = new File("./contactData");
    private List<ContactImpl> contactsList = new ArrayList<ContactImpl>(1);

    public ContactManagerImpl(){}

    public ContactManagerImpl(List<ContactImpl> list)
    {
        this.contactsList = list;
    }

    public int addFutureMeeting(Set<Contact> contacts, Calendar date)
    {

    }

    public PastMeeting getPastMeeting(int id)
    {

    }

    public FutureMeeting getFutureMeeting(int id)
    {

    }

    public Meeting getMeeting(int id)
    {

    }

    public List<Meeting> getFutureMeetingList(Contact contact)
    {

    }

    public List<Meeting> getFutureMeetingList(Calendar date)
    {

    }

    public List<PastMeeting> getPastMeetingList(Contact contact)
    {

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
        ContactImpl tmp = new ContactImpl(name, notes, uniqueId);       // split into 2 lines
        contactsList.add(tmp);                                          // for clarity
    }

    public Set<Contact> getContacts(int... ids)
    {

    }

    public Set<Contact> getContacts(String name)
    {

    }

    public void flush()
    {
        try
        {
            ObjectOutputStream objectOut =
                    new ObjectOutputStream(
                            new BufferedOutputStream(
                                    new FileOutputStream(dataOnDisk)));

            objectOut.writeObject(contactsList);
            objectOut.close();
        }
        catch (FileNotFoundException fnfex)
        {
            System.err.println("File not found. Please make sure you are in correct directory and try again");
        }
        catch (IOException ioex)
        {
            System.err.println("Problem writing to disk. See stack trace for details and/or please try again");
            ioex.printStackTrace();
        }
    }
}
