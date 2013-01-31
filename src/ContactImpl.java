public class ContactImpl implements Contact
{
    private int Id;
    private String name;
    private String notes = "";

    /** @param IdHelper in order to ensure uniqueness,
    the user cannot be allowed to assign id's */
    private static int IdHelper = 1;

    public ContactImpl(String name)
    {
        /** @param Id is assigned from IdHelper (initially at 1)
        IdHelper is then incremented for the next Id it will assign */
        this.name = name;
        this.Id = IdHelper;
        IdHelper++;
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
