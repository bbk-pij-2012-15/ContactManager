import org.junit.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

public class ContactManagerImplTest
{
    ContactManagerImpl conman = new ContactManagerImpl();
    Calendar futureDate = new GregorianCalendar();
    Calendar presentDate = new GregorianCalendar();  // leave set to present date
    Calendar pastDate = new GregorianCalendar();
    Calendar yesterday = new GregorianCalendar();
    Set<Contact> cset = new HashSet<Contact>();



    @Before
    public void setUpTest()
    {
        conman.addNewContact("Ann Andrews", "CTO at Canonical UK");
        conman.addNewContact("Bob Bobbit", "Organize drinks soon");
        conman.addNewContact("Cal Callerson", "Here is a note about Cal");
        futureDate.set(2013, Calendar.SEPTEMBER, 16);    // set to arbitrary date in the future
        pastDate.set(2012, Calendar.APRIL, 15);          // set to arbitrary date in the past
        cset = conman.getContacts(1,2,3);               // populate a contact set to pass to methods
        yesterday.add(Calendar.DAY_OF_MONTH, -1);       // set to yesterday's date by taking 1 off the day field
    }

    @Test
    public void testAddNewContact()
    {
        assertTrue(cset.size() == 3);
    }

    @After
    public void cleanUp()
    {
        conman = null;
    }

    @Test
    public void testAddFutureMeeting()
    {
        conman.addFutureMeeting(cset, futureDate);
        assertFalse(conman.meetingSet.isEmpty());
        assertFalse(conman.futureMeetings.isEmpty());
        assertTrue(conman.pastMeetings.isEmpty());

        FutureMeeting fm = conman.futureMeetings.get(0);
        System.out.println(((MeetingImpl)fm).getMeetingInfo());     // manually see if everything looks ok

        Calendar storedDate = conman.futureMeetings.get(0).getDate();
        Set<Contact> storedSet = conman.futureMeetings.get(0).getContacts();
        assertTrue(conman.futureMeetings.get(0).getId() == 1);          // should be 1 as that's what listsize + 1 is
        assertEquals(futureDate, storedDate);
        assertEquals(cset, storedSet);
    }

    @Test
    public void testGetPastMeeting()
    {
        conman.addNewPastMeeting(cset, pastDate, "Hugh's 22nd birthday");
        assertTrue(conman.meetingSet.size() == 1 && conman.pastMeetings.size() == 1);   // check add has worked
        assertTrue(conman.pastMeetings.get(0).getId() == 1);     // make sure id is what we expect before calling getPastMeeting()

        PastMeeting pm = conman.getPastMeeting(1);
        assertNotNull(pm);
        System.out.println(((MeetingImpl) pm).getMeetingInfo());  // manual check
    }

    @Test
    public void testGetFutureMeeting()
    {
        conman.addFutureMeeting(cset, futureDate);
        assertTrue(conman.meetingSet.size() == 1 && conman.futureMeetings.size() == 1);   // check add has worked
        assertTrue(conman.futureMeetings.get(0).getId() == 1);     // make sure id is what we expect

        FutureMeeting fm = conman.getFutureMeeting(1);
        assertNotNull(fm);
        System.out.println(((MeetingImpl) fm).getMeetingInfo());  // manual looksee
    }

    @Test
    public void testGetMeeting()
    {
        conman.addFutureMeeting(cset, futureDate);
        conman.addNewPastMeeting(cset, pastDate, "another past meeting");
        assertTrue(conman.futureMeetings.size() == 1 && conman.pastMeetings.size() == 1);   // check add has worked
        assertTrue(conman.futureMeetings.get(0).getId() == 1 );     // make sure id is what we expect
        assertTrue(conman.pastMeetings.get(0).getId() == 2 );     // make sure id is what we expect
        assertEquals(conman.meetingSet.size(), 2);               // make sur meetingSet contains both meetings

        Meeting fm = conman.getMeeting(1);
        Meeting pm = conman.getMeeting(2);
        assertNotNull(fm);
        assertNotNull(pm);
        System.out.println(((MeetingImpl) fm).getMeetingInfo());               // manual
        System.out.println("\n" + ((MeetingImpl) pm).getMeetingInfo());        // checks
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
        conman.addNewPastMeeting(cset, pastDate, "Hugh's 22nd birthday");
        assertFalse(conman.meetingSet.isEmpty());
        assertFalse(conman.pastMeetings.isEmpty());
        assertTrue(conman.futureMeetings.isEmpty());

        PastMeeting pm = conman.pastMeetings.get(0);
        System.out.println(((PastMeetingImpl)pm).getMeetingInfo());     // manually see if everything looks ok

        Calendar storedDate = conman.pastMeetings.get(0).getDate();
        Set<Contact> storedSet = conman.pastMeetings.get(0).getContacts();
        assertTrue(conman.pastMeetings.get(0).getId() == 1);        // should be 1 as that's what listsize + 1 is
        assertEquals(pastDate, storedDate);
        assertEquals(cset, storedSet);
    }

    @Test
    public void testAddMeetingNotes()
    {
        /** test of first function (add notes to an existing past meeting) starts here */
        assertTrue(conman.meetingSet.size() == 0);
        conman.addNewPastMeeting(cset, pastDate, "Hugh's 22nd birthday");

        assertTrue(conman.pastMeetings.get(0).getId() == 1);        // make sure before we use id in method call
        conman.addMeetingNotes(1, "Must buy a present!");

        PastMeeting pm = conman.pastMeetings.get(0);
        System.out.println(((PastMeetingImpl)pm).getMeetingInfo());     // manually see if everything looks ok
        assertTrue(conman.meetingSet.size() == 1);

        /** test of second function (convert a future meeting that has happened + add notes) starts here */
        assertTrue(conman.futureMeetings.isEmpty());
        conman.addFutureMeeting(cset, yesterday);        // create the future meeting to convert
        assertTrue(conman.futureMeetings.size() == 1);

        assertTrue(conman.futureMeetings.get(0).getId() == 2);  // check before method call - also should be 2 cos it's our 2nd meeting
        assertTrue(conman.meetingSet.size() == 2);

        Calendar calfut = conman.futureMeetings.get(0).getDate();
        Calendar now = new GregorianCalendar();
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("dd/MM/yyyy");
        System.out.println(df.format(calfut.getTime()));
        System.out.println(df.format(now.getTime()));

        assertTrue(conman.pastMeetings.size() == 1);
        conman.addMeetingNotes(2, "Meeting took place with general consensus - idea ready to pitch");
        assertTrue(conman.futureMeetings.isEmpty());    // should be empty cos we removed the passed future meeting
        assertTrue(conman.meetingSet.size() == 2);
        assertTrue(conman.pastMeetings.size() == 2);

        PastMeeting newpm = conman.pastMeetings.get(1);
        System.out.println(((PastMeetingImpl)newpm).getMeetingInfo());     // manually see if everything looks ok

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
        /** add a past meeting, then add notes to it */
        conman.addNewPastMeeting(cset, pastDate, "Hugh's 22nd birthday");
        conman.addMeetingNotes(1, "Must buy a present!");
        /** add a future meeting, then convert to past */
        conman.addFutureMeeting(cset, yesterday);        // create the future meeting to convert
        conman.addMeetingNotes(2, "Meeting took place with general consensus - idea ready to pitch");
        /** add a future meeting */
        conman.addFutureMeeting(cset, futureDate);
        /** at this point we should have 2 past meetings and 1 future meeting */
        assertTrue(conman.meetingSet.size() == 3);

        conman.flush();
        ContactManagerImpl loadedConman = new ContactManagerImpl();
        assertTrue(loadedConman.contactSet.size() == 3);
        assertTrue(loadedConman.meetingSet.size() == 3);
        assertTrue(loadedConman.pastMeetings.size() == 2);
        assertTrue(loadedConman.futureMeetings.size() == 1);
        for (Meeting m : loadedConman.meetingSet)
        {
            ((MeetingImpl)m).getMeetingInfo();     // manual check
        }
    }

    @Test
    public void testLoad()
    {
        /*ContactManager emptyConMan = new ContactManagerImpl();
        ((ContactManagerImpl)emptyConMan).load();*/
    }
}
