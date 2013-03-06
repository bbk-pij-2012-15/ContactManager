import org.junit.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class ContactManagerImplTest
{
    ContactManagerImpl conman = new ContactManagerImpl();

    @Test
    public void testAddNewContact()
    {
        conman.addNewContact("Ann Andrews", "CTO at Canonical UK");
        conman.addNewContact("Bob Bobbit", "Organize drinks soon");
        conman.addNewContact("Cal Callerson", "Here is a note about Cal");
        Set<Contact> set = conman.getContacts(1, 2, 3);
        assertTrue(set.size() == 3);
    }

    @After
    public void cleanUp()
    {
        conman = null;
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
        conman.addNewContact("Ann Andrews", "CTO at Canonical UK");
        conman.addNewContact("Bob Bobbit", "Organize drinks soon");
        conman.addNewContact("Cal Callerson", "Here is a note about Cal");
        Set<Contact> set = conman.getContacts(3, 2, 1);
        assertTrue(set.size() == 3);
    }

    @Test
    public void testGetContactsString()
    {
        conman.addNewContact("Ann Andrews", "CTO at Canonical UK");
        conman.addNewContact("Bob Bobbit", "Organize drinks soon");
        conman.addNewContact("Cal Callerson", "Here is a note about Cal");
        String nullString = null;
        Set<Contact> set = conman.getContacts("Calvin");
        assertTrue(set.isEmpty();
    }

    @Test
    public void testFlush()
    {
        /*conman.addNewContact("Ann Andrews", "CTO at Canonical UK");
        conman.addNewContact("Bob Bobbit", "Organize drinks soon");
        conman.flush();*/
    }

    @Test
    public void testLoad()
    {
        /*ContactManager emptyConMan = new ContactManagerImpl();
        ((ContactManagerImpl)emptyConMan).load();*/
    }
}
