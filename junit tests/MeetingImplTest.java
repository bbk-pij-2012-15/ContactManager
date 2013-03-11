import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class MeetingImplTest
{
    ContactManagerImpl conman = new ContactManagerImpl();
    Calendar futureDate = new GregorianCalendar(), futureDate2 = new GregorianCalendar(), futureDate3 = new GregorianCalendar();
    Calendar presentDate = new GregorianCalendar();  // leave set to present date
    Calendar pastDate = new GregorianCalendar(), twoDaysLater = new GregorianCalendar();
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
        futureDate2.add(Calendar.DAY_OF_MONTH, 7);      // a week later than present day
        futureDate3.add(Calendar.YEAR, 2);              // 2 years after present day
        twoDaysLater.add(Calendar.DAY_OF_MONTH, 2);     // 2 days after pastDate
    }

    @After
    public void cleanUp()
    {
        conman = null;
    }

    @Test
    public void testComparator()
    {
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("dd/MM/yyyy");
        /** add 7 different meetings with 7 different dates, and a list to store them */
        List<Meeting> unsortedMeetings = new ArrayList<Meeting>();
        Meeting m1 = new MeetingImpl(1, cset, futureDate);
        Meeting m2 = new MeetingImpl(2, cset, pastDate);
        Meeting m3 = new MeetingImpl(3, cset, futureDate3);
        Meeting m4 = new MeetingImpl(4, cset, presentDate);
        Meeting m5 = new MeetingImpl(5, cset, futureDate2);
        Meeting m6 = new MeetingImpl(6, cset, yesterday);
        Meeting m7 = new MeetingImpl(7, cset, twoDaysLater);
        unsortedMeetings.add(m1);
        unsortedMeetings.add(m2);
        unsortedMeetings.add(m3);
        unsortedMeetings.add(m4);
        unsortedMeetings.add(m5);
        unsortedMeetings.add(m6);
        unsortedMeetings.add(m7);
        for (Meeting m : unsortedMeetings)
        {
            Calendar cal = m.getDate();
            System.out.println(df.format(cal.getTime()));
        }
        System.out.println("========================================================================");
        List<Meeting> sortedMeetings = new ArrayList<Meeting>(unsortedMeetings);
        Collections.sort(sortedMeetings, MeetingImpl.MeetingComparator);            // sort using our custom comparator
        Calendar prevCal = null;
        for (Meeting m : sortedMeetings)
        {
            Calendar cal = m.getDate();
            System.out.println(df.format(cal.getTime()));
            if (prevCal != null)
                assertTrue(cal.after(prevCal));         // ensures current calendar is always later than the previous
            prevCal = cal;
        }
    }

    @Test
    public void testGetId()
    {
        Meeting m1 = new MeetingImpl(1, cset, futureDate);
        Meeting m2 = new MeetingImpl(2, cset, pastDate);
        Meeting m3 = new MeetingImpl(3, cset, futureDate3);
        Meeting m4 = new MeetingImpl(4, cset, presentDate);

        int id1 = m1.getId();
        int id2 = m2.getId();
        int id3 = m3.getId();
        int id4 = m4.getId();

        assertEquals(1, id1);
        assertEquals(2, id2);
        assertEquals(3, id3);
        assertEquals(4, id4);


    }

    @Test
    public void testGetDate()
    {
        Meeting m1 = new MeetingImpl(1, cset, futureDate);
        Meeting m2 = new MeetingImpl(2, cset, pastDate);
        Meeting m3 = new MeetingImpl(3, cset, futureDate3);
        Meeting m4 = new MeetingImpl(4, cset, presentDate);

        Calendar cal1 = m1.getDate();
        Calendar cal2 = m2.getDate();
        Calendar cal3 = m3.getDate();
        Calendar cal4 = m4.getDate();

        assertEquals(futureDate, cal1);
        assertEquals(pastDate, cal2);
        assertEquals(futureDate3, cal3);
        assertEquals(presentDate, cal4);
    }

    @Test
    public void testAddAndGetNotes()
    {
        Meeting m1 = new MeetingImpl(1, cset, futureDate);
        ((MeetingImpl)m1).addNotes("this is a note");
        String notes = ((MeetingImpl) m1).getNotes();

        assertEquals("-this is a note\n", notes);
    }

    @Test
    public void testGetContacts()
    {
        Meeting m1 = new MeetingImpl(1, cset, futureDate);
        Set<Contact> conSet = m1.getContacts();

        assertEquals(cset, conSet);
    }

    @Test
    public void testGetSetInfo()
    {
        Meeting m1 = new MeetingImpl(1, cset, futureDate);
        String contactsInfo = ((MeetingImpl)m1).getSetInfo();
        System.out.println(contactsInfo);       // manual inspection for this test, as string too long
    }

    @Test
    public void testGetMeetingInfo()
    {

    }

    @Test
    public void testGetFormattedDate()
    {

    }

    @Test
    public void testInPast()
    {

    }

    @Test
    public void testInFuture()
    {

    }

    @Test
    public void testReturnMeeting()
    {

    }
}
