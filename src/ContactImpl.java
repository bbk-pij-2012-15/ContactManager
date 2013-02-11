import java.io.Serializable;

public class ContactImpl implements Contact, Serializable
{
    private String name;
    private String notes = "";
    /** @param IdHelper in order to ensure uniqueness,
    the user cannot be allowed to assign id's */
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
        /** @return prints a newline at the end of each added note
        so the list of notes remains clear to read */
        notes += (note + "\n");
    }
}
