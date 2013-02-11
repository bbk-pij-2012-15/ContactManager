import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class MeetingImpl implements Meeting
{
    private int meetingId;
    private Set<Contact> contactSet = new HashSet<Contact>();
    private Calendar meetingCal = new GregorianCalendar().getInstance();

    public MeetingImpl()
    {

    }

    public int getId()
    {
    }

    public Calendar getDate()
    {
    }

    public Set<Contact> getContacts()
    {
    }

}
