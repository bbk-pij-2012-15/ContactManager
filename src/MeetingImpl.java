import java.util.*;

public class MeetingImpl implements Meeting
{
    private int meetingId;
    private Set<Contact> contactSet = new HashSet<Contact>();
    private Calendar meetingCal;

    public MeetingImpl(Set<Contact> set)
    {
        this.meetingId = (set.size() + 1);
        this.contactSet.addAll(set);
        this.meetingCal = new GregorianCalendar().getInstance();
    }

    public int getId()
    {
        return this.meetingId;
    }

    public Calendar getDate()
    {
        return this.meetingCal;
    }

    public Set<Contact> getContacts()
    {
        return this.contactSet;
    }

    public String getSetInfo()
    {
        String setInfo = "";
        for (Iterator<Contact> itr = this.contactSet.iterator(); itr.hasNext(); )
        {
            Contact tmp = itr.next();
            setInfo += tmp.getInfo();   // will implement this method later
        }
        return setInfo;
    }

    public String getMeetingInfo()
    {
        String id = "Meeting Id: " + this.meetingId;
        String contacts = "Contacts at Meeting: " + this.getSetInfo();
        String date = "Date of Meeting: " + this.meetingCal.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
                (this.meetingCal.get(GregorianCalendar.MONTH) + 1) + "/" + this.meetingCal.get(GregorianCalendar.YEAR);
        String info = (id + "\n" + contacts + "\n" + date);
        return info;
    }

}
