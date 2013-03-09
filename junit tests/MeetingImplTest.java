import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

    }

    @Test
    public void testComparator()
    {
        List<Meeting> unsortedMeetings;
        Meeting m1 = new MeetingImpl()
    }

    @Test
    public void testGetId()
    {

    }

    @Test
    public void testGetDate()
    {

    }

    @Test
    public void testGetNotes()
    {

    }

    @Test
    public void testGetContacts()
    {

    }

    @Test
    public void testGetSetInfo()
    {

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
    public void testAddNotes()
    {

    }

    @Test
    public void testReturnMeeting()
    {

    }
}
