import org.junit.*;
import static org.junit.Assert.*;

public class ContactImplTest
{

    // create a ContactImpl for the entire test to use
    // n.b. test WILL fail at this point as ContactImpl not yet implemented
    public ContactImpl cont = new ContactImpl("Test Contact");

    @Before
    public void buildUp() throws Exception
    {
       fail("not yet written");
    }

    @After
    public void cleanUp() throws Exception
    {
        // remove object after test by setting it to null
        // just in case it interferes with future tests
        cont = null;
    }

    @Test
    public void testGetId() throws Exception
    {
        int output = cont.getId();
        int expected = 001;
        assertEquals(expected, output);
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
        // test AddNotes by making sure a note has been added and so
        // it is not empty (""). we test the actual String in next method
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
        String output = cont.getNotes();
        String expected = "This is a note";
        assertEquals(expected, output);
    }
}
