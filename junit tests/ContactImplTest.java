import org.junit.*;
import static org.junit.Assert.*;

public class ContactImplTest
{

    /** create a ContactImpl for the entire test to use */
    ContactImpl cont = new ContactImpl("Test Contact");

    @After
    public void cleanUp() throws Exception
    {
        /**remove object after test by setting it to null
        just in case it interferes with future tests */
        cont = null;
    }

    @Test
    public void testGetId() throws Exception
    {
        int output = cont.getId();
        int expected = 0;
        assertEquals(expected, output);
    }

    @Test
    public void testIdHelper() throws Exception
    {
        /** create several ContactImpl objects and verify
        they all have unique, consecutive Ids */
        ContactImpl cont1 = new ContactImpl("Test Contact");
        ContactImpl cont2 = new ContactImpl("Test Contact");
        ContactImpl cont3 = new ContactImpl("Test Contact");
        ContactImpl cont4 = new ContactImpl("Test Contact");
        ContactImpl cont5 = new ContactImpl("Test Contact");
        String output = "" + cont.getId() + cont1.getId() + cont2.getId() + cont3.getId() + cont4.getId() + cont5.getId();
        String expected = "012345";
        assertEquals(expected, output);
        /** fails at the moment due to IdHelper being assigned to 0 at the
        top of ContactImpl class - this means each object of the class
        is instantiated with IdHelper equal to 0 */
     }

    @Test
    public void testGetName() throws Exception
    {
        String output = cont.getName();
        String expected = "Test Contact";
        assertEquals(expected, output);
    }

    @Test
    public void testAddNotes() throws Exception
    {
        /** test AddNotes by making sure a note has been added and so
        it is not empty (""). we test the actual String in next method */
        cont.addNotes("This is a note");
        boolean passedTest = false;
        if (cont.getNotes() != "")
        {
            passedTest = true;
        }
        assertTrue(passedTest);
    }

    @Test
    public void testGetNotes() throws Exception
    {
        cont.addNotes("This is a note");
        String output = cont.getNotes();
        String expected = "This is a note\n";
        assertEquals(expected, output);
    }
}
