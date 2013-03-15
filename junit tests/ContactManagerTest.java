import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Set;

public class ContactManagerTest
{
    ContactManager conman;
    Set<Contact> cset;
    File data = new File("./contacts.txt");

    @Before
    public void setUpTest()
    {
        conman = new ContactManagerImpl();
        Contact c1 = new ContactImpl("ann", "contact 1", 1);
        Contact c2 = new ContactImpl("bob", "contact 2", 2);
        Contact c3 = new ContactImpl("cal", "contact 3", 3);
        cset.add(c1);cset.add(c2);cset.add(c3);
    }

    @After
    public void cleanUp()
    {
        conman = null;
        data.delete();
    }

    @Test
    public void testAddFutureMeeting()
    {
         conman.addFutureMeeting()
    }

    @Test
    public void testGetPastMeeting()
    {

    }

    @Test
    public void testGetFutureMeeting()
    {

    }

    @Test
    public void testGetMeeting()
    {

    }

    @Test
    public void testGetFutureMeetingListContact()
    {

    }

    @Test
    public void testGetFutureMeetingListCal()
    {

    }

    @Test
    public void testGetPastMeetingList()
    {

    }

    @Test
    public void testAddNewPastMeeting()
    {

    }

    @Test
    public void testAddMeetingNotes()
    {

    }

    @Test
    public void testAddNewContact()
    {

    }

    @Test
    public void testGetContactsInt()
    {

    }

    @Test
    public void testGetContactsStr()
    {

    }

    @Test
    public void testFlush()
    {

    }
}
