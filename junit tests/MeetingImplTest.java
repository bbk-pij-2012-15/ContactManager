import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class MeetingImplTest
{
    Calendar futureDate = new GregorianCalendar(), futureDate2 = new GregorianCalendar(), futureDate3 = new GregorianCalendar();
    Calendar presentDate = new GregorianCalendar();  // leave set to present date
    Calendar pastDate = new GregorianCalendar(), twoDaysLater = new GregorianCalendar();
    Calendar yesterday = new GregorianCalendar();
    Set<Contact> cset = new HashSet<Contact>();

    @Before
    public void setUpTest()
    {

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
