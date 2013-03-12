import java.io.Serializable;

public class ContactImpl implements Contact, Serializable
{
    private String name;
    private String notes = "";   // initialized to empty string, so will never be returned null

    /** @param Id a unique id calculated by ContactManagerImpl
     (by adding 1 to the current size of the set of contacts) */
    private int Id;

    public ContactImpl(String name, String notes, int Id)
    {
        /** @param Id is safe to have in the constructor as the user never directly constructs Contacts,
         *  (this is done by the ContactManager) and so cannot set an arbitrary or non-unique Id */
        this.name = name;
        this.Id = Id;
        this.notes = notes;
    }

    public int getId()
    {
        /** @return the unique id of the contact */
        return this.Id;
    }

    public String getName()
    {
        /** @return the full name of the contact */
        return this.name;
    }

    public String getNotes()
    {
        /** @return any and all notes associated with the contact.
         *  If there are no notes, the empty string is returned */
        return this.notes;
    }

    public void addNotes(String note)
    {
        /** method prints a newline at the end of each added note and a dash-bullet
        *   at the start so the list of notes remains clear to read */
        this.notes += ("-" + note + "\n");
    }

    protected String getInfo()
    {
        /** @return returns a useful String containing a Contact's name, id and any notes
        Will be used for test purposes and to display information about a specific Contact */
        String tmp = "Name: " + this.name + ", Id: " + this.Id + ", Notes: " + this.notes;
        return tmp;
    }
}
