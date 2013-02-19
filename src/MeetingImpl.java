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

    public MeetingImpl()
    {
        // no args constructor for comparator
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

    /** @param shouldBeFuture - flag passed from ContactManager so we know
     * whether getFutureMeeting() or getPastMeeting() has been called */
    public Meeting getMeeting(Set<Meeting> meetingSet, int id, boolean shouldBeFuture)
    {
        for (Iterator<Meeting> itr = meetingSet.iterator(); itr.hasNext();)
        {
            if (itr.next().getId() == id && shouldBeFuture == true)   // i.e. this needs to be a FUTURE meeting
            {
                if (((MeetingImpl)itr.next()).inFuture() == true)     // use boolean getter to confirm this is a FUTURE meeting
                {
                    /** if this condition true we have found id AND confirmed the meeting to be FUTURE
                     * @return itr.next which is type Meeting, cast into type FutureMeeting */
                    return (FutureMeeting) itr.next();
                }
                else if (((MeetingImpl)itr.next()).inPast() == true)       // i.e. if this is a PAST meeting [error]
                {
                    /** if this condition true we have found id BUT the meeting is PAST
                     * @throws an IllegalArgsException */
                    throw new IllegalArgumentException("Meeting with specified ID happened on " + ((MeetingImpl)itr.next()).getFormattedDate());
                }
            }
            else if (itr.next().getId() == id && shouldBeFuture == false)   // i.e. this needs to be a PAST meeting
            {
                if (((MeetingImpl)itr.next()).inPast() == true)   // use boolean getter to confirm this is a PAST meeting
                {
                    /** if this condition true we have found id AND confirmed the meeting to be PAST
                     * @return itr.next which is type Meeting, cast into type FutureMeeting */
                    return (PastMeeting) itr.next();
                }
                else if (((MeetingImpl)itr.next()).inFuture() == true)    // i.e. if this is a FUTURE meeting [error]
                {
                    /** if this condition true we have found id BUT the meeting is FUTURE
                     * @throws an IllegalArgsException */
                    throw new IllegalArgumentException("Meeting with specified ID will not happen until " + ((MeetingImpl)itr.next()).getFormattedDate());
                }
            }
            else // if the id is never found at all
            {
                System.err.println("No meeting found with id " + id);
                return null;
            }
        }
        System.err.println("Unable to read list of meetings. Please ensure it has readable permissions and/or has been created");
        return null;
    }

}
