import org.junit.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

public class ContactManagerImplTest
{
    ContactManagerImpl conman = new ContactManagerImpl();
    Calendar date = new GregorianCalendar();

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
        date.set(2013, 9, 16);                           // set to arbitrary date in future
        Set<Contact> cset = conman.getContacts(1,2,3);     // populate a contact set to pass to method

        conman.addFutureMeeting(cset, date);
        assertFalse(conman.meetingSet.isEmpty());
        assertFalse(conman.futureMeetings.isEmpty());
        assertTrue(conman.pastMeetings.isEmpty());

        FutureMeeting fm = conman.futureMeetings.get(0);
        System.out.println(((MeetingImpl)fm).getMeetingInfo());     // manually see if everything looks ok

        Calendar storedDate = conman.futureMeetings.get(0).getDate();
        Set<Contact> storedSet = conman.futureMeetings.get(0).getContacts();
        assertTrue(conman.futureMeetings.get(0).getId() == 1);          // should be 1 as that's what listsize + 1 is
        assertEquals(date, storedDate);
        assertEquals(cset, storedSet);
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
        date.set(2012, 4, 15);               // set to arbitrary date in the past
        Set<Contact> cset = conman.getContacts(1,2,3);     // populate a contact set to pass to method

        conman.addNewPastMeeting(cset, date, "Hugh's 22nd birthday");
        assertFalse(conman.meetingSet.isEmpty());
        assertFalse(conman.pastMeetings.isEmpty());
        assertTrue(conman.futureMeetings.isEmpty());

        PastMeeting pm = conman.pastMeetings.get(0);
        System.out.println(((PastMeetingImpl)pm).getMeetingInfo());     // manually see if everything looks ok

        Calendar storedDate = conman.pastMeetings.get(0).getDate();
        Set<Contact> storedSet = conman.pastMeetings.get(0).getContacts();
        assertTrue(conman.pastMeetings.get(0).getId() == 1);        // should be 1 as that's what listsize + 1 is
        assertEquals(date, storedDate);
        assertEquals(cset, storedSet);
    }

    @Test
    public void testAddMeetingNotes()
    {
        /** test of first function (add notes to an existing past meeting) starts here */
        date.set(2012, 4, 15);               // set to arbitrary date in the past
        Set<Contact> cset = conman.getContacts(1,2,3);     // populate a contact set to pass to method
        assertTrue(conman.meetingSet.size() == 0);
        conman.addNewPastMeeting(cset, date, "Hugh's 22nd birthday");

        assertTrue(conman.pastMeetings.get(0).getId() == 1);        // make sure before we use id in method call
        conman.addMeetingNotes(1, "Must buy a present!");

        PastMeeting pm = conman.pastMeetings.get(0);
        System.out.println(((PastMeetingImpl)pm).getMeetingInfo());     // manually see if everything looks ok
        assertTrue(conman.meetingSet.size() == 1);

        /** test of second function (convert a future meeting that has happened + add notes) starts here */
        date.set(2013, 3, 6);          // set yesterday's date for the meeting to have happened
        assertTrue(conman.futureMeetings.isEmpty());
        conman.addFutureMeeting(cset, date);        // create the future meeting to convert
        assertTrue(conman.futureMeetings.size() == 1);

        assertTrue(conman.futureMeetings.get(0).getId() == 2);  // check before method call - also should be 2 cos it's our 2nd meeting
        assertTrue(conman.meetingSet.size() == 2);

        conman.addMeetingNotes(2, "Meeting took place with general consensus - idea ready to pitch");

    }

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
