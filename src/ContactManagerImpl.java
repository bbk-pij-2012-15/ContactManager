import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class ContactManagerImpl implements ContactManager, Serializable
{
    private File dataOnDisk = new File("./data");
    private List<ContactImpl> contactsList = new ArrayList<ContactImpl>(1);

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
        catch (IOException ioex)
        {
            System.err.println("Problem writing to disk. See stack trace for details and/or please try again");
            ioex.printStackTrace();
        }
    }
}
