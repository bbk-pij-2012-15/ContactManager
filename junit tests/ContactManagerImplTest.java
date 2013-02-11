import org.junit.*;

import java.io.File;

import static org.junit.Assert.*;

public class ContactManagerImplTest
{
    ContactManagerImpl conman = new ContactManagerImpl();
    private File dataOnDisk = new File("./contacts.txt");


    @Test
    public void testAddNewContact()
    {
        conman.addNewContact("Ann Andrews", "CTO at Canonical UK");
        conman.addNewContact("Bob Bobbit", "Organize drinks soon");
        conman.addNewContact("Cal Callerson", "Here is a note about Cal");
    }

    @After
    public void cleanUp()
    {
        fail("not written yet");
    }

    @Test
    public void testAddFutureMeeting()
    {
        fail("not written yet");
    }

    @Test
    public void testGetPastMeeting()
    {
        fail("not written yet");
    }

    @Test
    public void testGetFutureMeeting()
    {
        fail("not written yet");
    }

    @Test
    public void testGetMeeting()
    {
        fail("not written yet");
    }

    @Test
    public void testGetFutureMeetingListContact()
    {
        fail("not written yet");
    }

    @Test
    public void testGetFutureMeetingListCalendar()
    {
        fail("not written yet");
    }

    @Test
    public void testGetPastMeetingList()
    {
        fail("not written yet");
    }

    @Test
    public void testAddNewPastMeeting()
    {
        fail("not written yet");
    }

    @Test
    public void testAddMeetingNotes()
    {
        fail("not written yet");
    }//

    @Test
    public void testGetContactsInt()
    {
        fail("not written yet");
    }

    @Test
    public void testGetContactsString()
    {
        fail("not written yet");
    }

    @Test
    public void testFlush()
    {
        boolean testSuccess = false;
        conman.addNewContact("Ann Andrews", "CTO at Canonical UK");
        conman.addNewContact("Bob Bobbit", "Organize drinks soon");
        conman.flush();
        if (dataOnDisk.exists())
        {
            testSuccess = true;
        }
        assertTrue(testSuccess);
    }

    @Test
    public void testLoad()
    {

    }
}
