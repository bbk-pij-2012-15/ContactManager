import org.junit.After;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.*;

public class ContactManagerTest
{
    Set<Contact> cset = new HashSet<Contact>();
    File data = new File("./contacts.txt");
    Calendar calendar = new GregorianCalendar();
    static ContactManager conman;

    @BeforeClass
    public static void setUpClass()
    {
        conman = new ContactManagerImpl("s");
    }

    @Before
    public void setUpTest()
    {
        Contact c1 = new ContactImpl("ann", "contact 1", 1);
        Contact c2 = new ContactImpl("bob", "contact 2", 2);
        Contact c3 = new ContactImpl("cal", "contact 3", 3);
        conman.addNewContact("ann", "contact 1");conman.addNewContact("bob", "contact 2");conman.addNewContact("cal", "contact 3");
        cset = conman.getContacts(1,2,3);
    }

    @AfterClass
    public static void cleanUp()
    {
        conman = null;
    }

    @Test
    public void testAddFutureMeeting()
    {
        ByteArrayOutputStream barr = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(barr);
        PrintStream old = System.out;
        System.setOut(ps);

        calendar.set(2015, 7, 22);
        conman.addFutureMeeting(cset, calendar);
        String expected = "Success - Meeting Scheduled!\n";
        String actual = (barr.toString());
        assertEquals(expected, actual);
        List<Meeting> list = conman.getFutureMeetingList(calendar);
        assertTrue(list.size() == 1);

        System.out.flush();
        System.setOut(old);
    }

    @Test
    public void testGetPastMeeting()
    {
        boolean exception = false;
        try
        {
            calendar.set(2099, 7, 22);
            conman.addNewPastMeeting(cset, calendar, "Here is a past meeting");
        }
        catch (IllegalArgumentException illex)
        {
            exception = true;
        }
        assertTrue(exception);
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
