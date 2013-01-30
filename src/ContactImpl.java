public class ContactImpl implements Contact
{
    private int Id;
    private String name;
    protected String notes;

    public ContactImpl(int Id, String name)
    {
        this.name = name;
        this.Id = Id;
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
