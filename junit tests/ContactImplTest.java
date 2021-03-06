import org.junit.*;
import static org.junit.Assert.*;

public class ContactImplTest
{

    /** create some ContactImpl objects for the entire test to use */
    Contact cont1 = new ContactImpl("Test Contact", "", 1);       // this note is an empty string for the purposes of testAddNotes
    ContactImpl cont2 = new ContactImpl("Test Contact", "note", 2);     // created this contact as object of ContactImpl for the purposes of testGetInfo [as method not in interface]
    Contact cont3 = new ContactImpl("Test Contact", "note", 3);
    Contact cont4 = new ContactImpl("Test Contact", "note", 4);
    Contact cont5 = new ContactImpl("Test Contact", "note", 5);
    Contact cont6 = new ContactImpl("Test Contact", "note", 6);

    @After
    public void cleanUp()
    {
        /**remove objects after test by setting it to null
        just in case they interfere with future tests */
        cont1 = null;
        cont2 = null;
        cont3 = null;
        cont4 = null;
        cont5 = null;
        cont6 = null;
    }

    @Test
    public void testGetId()
    {
        int output = cont1.getId();
        int expected = 001;
        assertEquals(expected, output);
    }

    @Test
    public void testIdHelper()
    {
        /** test several ContactImpl objects and verify
        they all have unique, consecutive Ids */
        String output = "" + cont1.getId() + cont2.getId() + cont3.getId() + cont4.getId() + cont5.getId() + cont6.getId();
        String expected = "123456";    // concatenate the Id's into a string and test if it matches what it should be
        assertEquals(expected, output);
     }

    @Test
    public void testGetName()
    {
        String output = cont1.getName();
        String expected = "Test Contact";
        assertEquals(expected, output);
    }

    @Test
    public void testAddNotes()
    {
        /** test AddNotes by making sure a note has been added and so
        it is not empty (""). we test the actual String in next method */
        cont1.addNotes("This is a note");
        boolean passedTest = false;
        if (cont1.getNotes() != "")
        {
            passedTest = true;
        }
        assertTrue(passedTest);
    }

    @Test
    public void testGetNotes()
    {
        cont1.addNotes("This is a note");
        String output = cont1.getNotes();
        String expected = "This is a note\n";
        assertEquals(expected, output);
    }

    @Test
    public void testGetInfo()
    {
        String info = cont2.getInfo();
        String expected = "Name: Test Contact, Id: 2, Notes: \nnote";
        assertEquals(expected, info);
    }

}
