import java.util.*;

public class MeetingImpl implements Meeting
{
    private int meetingId;
    private Set<Contact> contactsAtMeeting = new HashSet<Contact>();
    private Calendar meetingCal;
    private boolean past = false;
    private boolean future = false;
    private String notes;

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
        return (id + "\n" + contacts + "\n" + date);
    }

    public String getFormattedDate()
    {
        return "Date of Meeting: " + this.meetingCal.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
                (this.meetingCal.get(GregorianCalendar.MONTH) + 1) + "/" + this.meetingCal.get(GregorianCalendar.YEAR);
    }

    public boolean inPast()
    {
        return past;
    }

    public boolean inFuture()
    {
        return future;
    }

    public void addNotes(String note)
    {
        /** @return prints a newline at the end of each added note and a dash
        at the start so the list of notes remains clear to read */
        notes += ("-" + note + "\n");
    }


    /** @param whatKindOfMeeting - flag passed from ContactManager so we know
     * whether getFutureMeeting(), getPastMeeting() or getMeeting() has been called */
    protected Meeting returnMeeting(Set<Meeting> meetingSet, int id, char whatKindOfMeeting)
    {
        for (Iterator<Meeting> itr = meetingSet.iterator(); itr.hasNext();)
        {
            if (itr.next().getId() == id && whatKindOfMeeting == 'f')   // i.e. this needs to be a FUTURE meeting
            {
                if (((MeetingImpl)itr.next()).inFuture() == true)     // use boolean getter to confirm this is a FUTURE meeting
                {
                    /** if this condition true we have found id AND confirmed the meeting to be FUTURE; @return itr.next */
                    return itr.next();
                }
                else if (((MeetingImpl)itr.next()).inPast() == true)       // i.e. if this is a PAST meeting [error]
                {
                    /** if this condition true we have found id BUT the meeting is PAST; @throws IllegalArgsException */
                    throw new IllegalArgumentException("Meeting with specified ID happened on " + ((MeetingImpl)itr.next()).getFormattedDate());
                }
            }
            else if (itr.next().getId() == id && whatKindOfMeeting == 'p')   // i.e. this needs to be a PAST meeting
            {
                if (((MeetingImpl)itr.next()).inPast() == true)   // use boolean getter to confirm this is a PAST meeting
                {
                    /** if this condition true we have found id AND confirmed the meeting to be PAST; @return itr.next */
                    return itr.next();
                }
                else if (((MeetingImpl)itr.next()).inFuture() == true)    // i.e. if this is a FUTURE meeting [error]
                {
                    /** if this condition true we have found id BUT the meeting is FUTURE; @throws IllegalArgsException */
                    throw new IllegalArgumentException("Meeting with specified ID will not happen until " + ((MeetingImpl)itr.next()).getFormattedDate());
                }
            }
            else if (itr.next().getId() == id && whatKindOfMeeting == 'm')   // i.e. this needs to be just a MEETING [getMeeting]
            {
                /** can just return; no need to check if meeting past or future as it can be both to satisfy getMeeting() */
                return itr.next();
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

    /** @param obj - the Calendar or Contact object from the getXMeetingList() methods. useful for knowing which method is calling this one too */
    protected static List<Meeting> returnMeetingList(Set<Meeting> meetingSet, char whatKindOfMeeting, Object obj)
    {
        List<Meeting> futureMeetings = new ArrayList<Meeting>();
        List<PastMeeting> pastMeetings = new ArrayList<PastMeeting>();
        List<Meeting> listToReturn = new ArrayList<Meeting>();

        for (Iterator<Meeting> itr = meetingSet.iterator(); itr.hasNext();)
        {
            if (((MeetingImpl)itr.next()).inFuture() == true)
            {
                /** populate futureMeetings with meetings that satisfy the inFuture() requirement */
                futureMeetings.add(itr.next());
            }
            else if (((MeetingImpl)itr.next()).inPast() == true)
            {
                /** populate pastMeetings with meetings that satisfy the inPast() requirement */
                pastMeetings.add((PastMeeting)itr.next());
            }
        }
        if (obj instanceof Contact && whatKindOfMeeting == 'f')
        {
            for (Iterator<Meeting> itr = futureMeetings.iterator(); itr.hasNext();)
            {
                Meeting m = itr.next();
                if (m.getContacts().contains(obj))
                {
                    /** each time a matching Meeting is found, it is added to the list. */
                    listToReturn.add(m);
                }
            }
        }
        else if (obj instanceof Calendar && whatKindOfMeeting == 'f')
        {
            for (Iterator<Meeting> itr = futureMeetings.iterator(); itr.hasNext();)
            {
                Meeting m = itr.next();
                if (m.getDate().equals(obj))
                {
                    /** each time a matching Meeting is found, it is added to the list. */
                    listToReturn.add(m);
                }
            }
        }
        else if (obj instanceof Contact && whatKindOfMeeting == 'p')
        {
            for (Iterator<PastMeeting> itr = pastMeetings.iterator(); itr.hasNext();)
            {
                PastMeeting pm = itr.next();
                if (pm.getContacts().contains(obj))
                {
                    /** each time a matching Meeting is found, it is added to the list. */
                    listToReturn.add(pm);
                }
            }
        }
        return listToReturn;
    }

    public static Comparator<Meeting> MeetingComparator = new Comparator<Meeting>()
    {
        @Override
        public int compare(Meeting m1, Meeting m2)
        {
            Calendar cal1 = m1.getDate();      // the calendar for the first meeting
            Calendar cal2 = m2.getDate();   // the calendar for the second meeting
            long cal1Time = cal1.getTimeInMillis() ;
            long cal2Time = cal2.getTimeInMillis();
            /** @return a number which will unambiguously place each calendar in order (using milliseconds)
             * 1 if cal1Time is greater than cal2Time, -1 for vice-versa and 0 for equality*/
            return (cal1Time > cal2Time) ? 1 : (cal1Time < cal2Time) ? -1 : 0;        // used ternary operator to save space
        }
    };
}
