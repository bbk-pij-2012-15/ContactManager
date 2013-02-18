import java.util.*;

public class MeetingImpl implements Meeting, Comparator<Meeting>
{
    private int meetingId;
    private Set<Contact> contactsAtMeeting = new HashSet<Contact>();
    private Calendar meetingCal;
    private boolean past = false;
    private boolean future = false;

    public MeetingImpl(Set<Contact> set, Calendar date)
    {
        this.meetingId = (set.size() + 1);
        this.contactsAtMeeting.addAll(set);
        this.meetingCal = date;

        Calendar currentDate = GregorianCalendar.getInstance();
        if (currentDate.after(date))       // i.e if meeting date is in the past
        {
            this.past = true;
        }
        else if (currentDate.before(date))       // i.e. if meeting date is in the future
        {
            this.future = true;
        }
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

    public String getFormattedDate()
    {
        String datestr = "Date of Meeting: " + this.meetingCal.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
                (this.meetingCal.get(GregorianCalendar.MONTH) + 1) + "/" + this.meetingCal.get(GregorianCalendar.YEAR);
        return datestr;
    }

    public boolean inPast()
    {
        return past;
    }

    public boolean inFuture()
    {
        return future;
    }

    @Override
    public int compare(Meeting m1, Meeting m2)
    {
        Calendar cal1 = m1.getDate();      // the calendar for the first meeting
        Calendar cal2 = m2.getDate();   // the calendar for the second meeting
        int cal1Time = (int) cal1.getTimeInMillis() ;     // cast the long return type of method getTimeInMillis to an int for the comparator
        int cal2Time = (int) cal2.getTimeInMillis();
        /** @return a number which will unambiguously place each calendar in order (using milliseconds) */
        return (cal1Time - cal2Time);
    }

}
