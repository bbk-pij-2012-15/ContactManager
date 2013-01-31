import org.junit.*;
import static org.junit.Assert.*;

public class ContactImplTest
{

    /** create some ContactImpl objects for the entire test to use */
    ContactImpl cont = new ContactImpl("Test Contact");
    ContactImpl cont1 = new ContactImpl("Test Contact");
    ContactImpl cont2 = new ContactImpl("Test Contact");
    ContactImpl cont3 = new ContactImpl("Test Contact");
    ContactImpl cont4 = new ContactImpl("Test Contact");
    ContactImpl cont5 = new ContactImpl("Test Contact");

    @After
    public void cleanUp()
    {
        /**remove objects after test by setting it to null
        just in case they interfere with future tests */
        cont = null;
        cont1 = null;
        cont2 = null;
        cont3 = null;
        cont4 = null;
        cont5 = null;
    }

    @Test
    public void testGetId()
    {
        int output = cont.getId();
        int expected = 1;
        assertEquals(expected, output);
    }

    @Test
    public void testIdHelper()
    {
        /** test several ContactImpl objects and verify
        they all have unique, consecutive Ids */
        String output = "" + cont.getId() + cont1.getId() + cont2.getId() + cont3.getId() + cont4.getId() + cont5.getId();
        String expected = "123456";
        assertEquals(expected, output);
     }

    @Test
    public void testGetName()
    {
        String output = cont.getName();
        String expected = "Test Contact";
        assertEquals(expected, output);
    }

    @Test
    public void testAddNotes()
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
    public void testGetNotes()
    {
        cont.addNotes("This is a note");
        String output = cont.getNotes();
        String expected = "This is a note\n";
        assertEquals(expected, output);
    }
}
