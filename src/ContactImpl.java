public class ContactImpl implements Contact
{
    private int Id;
    private String name;
    protected String notes;

    // in order to ensure uniqueness, the user cannot be allowed to assign id's
    private int IdHelper = 0;

    public ContactImpl(String name)
    {
        // Id is assigned from IdHelper (initially at 0)
        // IdHelper is then incremented for the next Id it will assign
        this.name = name;
        this.Id = IdHelper;
        IdHelper++;
    }

    public int getId()
    {
        return 0;
    }

    public String getName()
    {
        return "0";
    }

    public String getNotes()
    {
        return "0";
    }

    public void addNotes(String note)
    {

    }
}
