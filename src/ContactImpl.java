import java.io.Serializable;

public class ContactImpl implements Contact, Serializable
{
    private String name;
    private String notes = "";
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
        return this.Id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getNotes()
    {
        return this.notes;
    }

    public void addNotes(String note)
    {
        /** @return prints a newline at the end of each added note and a dash
        at the start so the list of notes remains clear to read */
        notes += ("-" + note + "\n");
    }

    public String getInfo()
    {
        /** @return returns a useful String containing a Contact's name, id and any notes
        Will be used for test purposes and to display information about a specific Contact */
        String tmp = "Name: " + this.name + ", Id: " + this.Id + ", Notes: " + this.notes;
        return tmp;
    }
}
