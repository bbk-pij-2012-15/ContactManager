import org.junit.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

public class ContactManagerImplTest
{
    ContactManagerImpl conman = new ContactManagerImpl();

    @Before
    public void setUpTest()
    {
        conman.addNewContact("Ann Andrews", "CTO at Canonical UK");
        conman.addNewContact("Bob Bobbit", "Organize drinks soon");
        conman.addNewContact("Cal Callerson", "Here is a note about Cal");
    }

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
        Set<Contact> cset = conman.getContacts(1,2,3);

        Calendar date = new GregorianCalendar();
        date.set(2013, 9, 16);
        conman.addFutureMeeting(cset, date);
        System.out.println(conman.meetingSet);
        System.out.println(conman.futureMeetings);
        System.out.println(conman.pastMeetings);
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
        boolean exception = false;
        Set<Contact> set = conman.getContacts(3, 2, 1);
        assertTrue(set.size() == 3);
        try
        {
            conman.getContacts(3, 1, 5);
            fail( "Didn't throw exception on invalid contact id" );
        }
        catch (IllegalArgumentException illargex)
        {
            exception = true;
        }
        assertTrue(exception);
    }

    @Test
    public void testGetContactsString()
    {
        boolean exception = false;
        String nullString = null;
        try
        {
            conman.getContacts(nullString);
            fail( "Didn't throw exception on null string argument" );
        }
        catch (NullPointerException npex)
        {
            exception = true;
        }
        assertTrue(exception);
        Set <Contact> set = conman.getContacts("Calvin");
        assertTrue(set.isEmpty());
        set = conman.getContacts("andrews");
        assertTrue(set.size() == 1);
    }

    @Test
    public void testFlushAndLoad()         // merged test methods as cannot test individually
    {
        conman.flush();
        ContactManagerImpl loadedConman = new ContactManagerImpl();
        Set<Contact> set = loadedConman.getContacts(3,2,1);
        assertTrue(set.size() == 3);
    }

    @Test
    public void testLoad()
    {
        /*ContactManager emptyConMan = new ContactManagerImpl();
        ((ContactManagerImpl)emptyConMan).load();*/
    }
}
