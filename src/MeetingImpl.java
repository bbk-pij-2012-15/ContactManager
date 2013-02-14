import java.util.Set;
import java.util.HashSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class MeetingImpl implements Meeting
{
    private int meetingId;
    private Set<Contact> contactsAtMeeting = new HashSet<Contact>();
    private Calendar meetingCal;

    public MeetingImpl(Set<Contact> set, Calendar date)
    {
        this.meetingId = (set.size() + 1);
        this.contactsAtMeeting.addAll(set);
        this.meetingCal = date;
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
        return this.contactsAtMeeting;
    }

    public String getSetInfo()
    {
        String setInfo = "";
        for (Iterator<Contact> itr = this.contactsAtMeeting.iterator(); itr.hasNext();)
        {
            ContactImpl tmp = (ContactImpl) itr.next();
            setInfo += tmp.getInfo();
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
