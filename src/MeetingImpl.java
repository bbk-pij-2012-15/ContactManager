import java.io.Serializable;
import java.util.*;

public class MeetingImpl implements Meeting, Serializable
{
    private int meetingId;
    private Set<Contact> contactsAtMeeting = new HashSet<Contact>();
    private Calendar meetingCal;

    /** @param past a marker to test if the meeting date is in the past */
    private boolean past = false;
    /** @param future a marker to test if the meeting date is in the future */
    private boolean future = false;

    private String meetingNotes = "";  // initialized to the empty string so no chance of being returned as null

    /** @param id a unique id calculated in ContactManagerImpl
     *  (by adding 1 to the current size of the set of meetings */
    public MeetingImpl(int id, Set<Contact> set, Calendar date)
    {
        this.meetingId = id;
        this.contactsAtMeeting.addAll(set);
        this.meetingCal = date;

        Calendar currentDate = GregorianCalendar.getInstance();
        if (currentDate.after(date))             // i.e if meeting date is in the past
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
        /** @return the unique id of the meeting */
        return this.meetingId;
    }

    public Calendar getDate()
    {
        /** @return the date of the meeting */
        return this.meetingCal;
    }

    public String getNotes()
    {
        /** @return any and all notes associated with the meeting.
         *  If there are no notes, the empty string is returned */
        return this.meetingNotes;
    }

    public Set<Contact> getContacts()
    {
        /** @return any and all contacts associated with the meeting.
         *  If there are no contacts, an empty set is returned */
        return this.contactsAtMeeting;
    }

    /** @return a formatted string with the details of contacts who attended a given meeting
     * @see #getMeetingInfo() - this method only used by that method to simplify its body */
    private String getSetInfo()
    {
        String setInfo = "Contacts at Meeting: ";
        for (Iterator<Contact> itr = this.contactsAtMeeting.iterator(); itr.hasNext();)
        {
            ContactImpl tmp = (ContactImpl) itr.next();
            setInfo += tmp.getInfo() + "\n";
        }
       /** @return removes final newline character, which is
        * unnecessary as there are no more contacts to follow) */
        return setInfo.substring(0, (setInfo.length() - 1));
    }

    /** @return a formatted string with the details of contacts who attended a given meeting
     * @see #getMeetingInfo() - this method only used by that method to simplify its body */
    private String getFormattedDate()
    {
        /** adds 1 to month int of calendar as month int values starts from 0 */
        return "Date of Meeting: " + this.meetingCal.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
                (this.meetingCal.get(GregorianCalendar.MONTH) + 1) + "/" + this.meetingCal.get(GregorianCalendar.YEAR);
    }

    /** @return a useful String containing a Meeting's id, contacts in attendance, date and any notes
     *  Will be used for test purposes and by user to display information about a specific Meeting */
    protected String getMeetingInfo()
    {
        String id = "Meeting Id: " + this.meetingId;
        String contacts = this.getSetInfo();
        String date = this.getFormattedDate();
        String notes = "Meeting Notes: " + this.getNotes();
        return (id + "\n" + contacts + "\n" + date + "\n" + notes);
    }

    public boolean inPast()
    {
        /** @return truth value about meeting being in the past */
        return past;
    }

    public boolean inFuture()
    {
        /** @return truth value about meeting being in the future */
        return future;
    }

    public void addNotes(String note)
    {
        /** @return prints a newline at the end of each added note and a dash-bullet
         *  at the start so the list of notes remains clear to read */
        meetingNotes += ("-" + note + "\n");
    }


    /** @param whatKindOfMeeting - flag passed from ContactManagerImpl so we know which of 3 methods have been called
     *  @see ContactManagerImpl#getFutureMeeting(int),
     *  @see ContactManagerImpl#getMeeting(int),
     *  @see ContactManagerImpl#getPastMeeting(int) */
    protected static Meeting returnMeeting(Set<Meeting> meetingSet, int id, char whatKindOfMeeting)
    {
        for (Meeting meeting : meetingSet)
        {
            if (meeting.getId() == id && whatKindOfMeeting == 'f')   // i.e. called from getFutureMeeting
            {
                if (((MeetingImpl)meeting).inFuture() == true)     // use boolean getter to confirm this is a FUTURE meeting
                {
                    /** if this condition true we have found id AND confirmed the meeting to be FUTURE; @return itr.next */
                    return meeting;
                }
                else if (((MeetingImpl)meeting).inPast() == true)       // i.e. if this is a PAST meeting [error]
                {
                    /** if this condition true we have found id BUT the meeting is PAST; @throws IllegalArgsException */
                    throw new IllegalArgumentException("Meeting with specified ID happened on " + ((MeetingImpl)meeting).getFormattedDate());
                }
            }
            else if (meeting.getId() == id && whatKindOfMeeting == 'p')   // i.e. called from getPastMeeting
            {
                if (((MeetingImpl)meeting).inPast() == true)   // use boolean getter to confirm this is a PAST meeting
                {
                    /** if this condition true we have found id AND confirmed the meeting to be PAST; @return itr.next */
                    return meeting;
                }
                else if (((MeetingImpl)meeting).inFuture() == true)    // i.e. if this is a FUTURE meeting [error]
                {
                    /** if this condition true we have found id BUT the meeting is FUTURE; @throws IllegalArgsException */
                    throw new IllegalArgumentException("Meeting with specified ID will not happen until " + ((MeetingImpl)meeting).getFormattedDate());
                }
            }
            else if (meeting.getId() == id && whatKindOfMeeting == 'm')   // i.e. called from getMeeting
            {
                /** can just return; no need to check if meeting past or future as it can be both to satisfy getMeeting */
                return meeting;
            }
        }
        /** if no meeting has been found at all... */
        System.err.println("No meeting found with id " + id);
        return null;
    }

    /** custom comparator for sorting meetings chronologically for the 3 list getter methods in ContactManagerImpl
     *  @see ContactManagerImpl#getPastMeetingList(Contact),
     *  @see ContactManagerImpl#getFutureMeetingList(java.util.Calendar),
     *  @see ContactManagerImpl#getFutureMeetingList(Contact)  */
    protected static Comparator<Meeting> MeetingComparator = new Comparator<Meeting>()
    {
        @Override
        public int compare(Meeting m1, Meeting m2)
        {
            Calendar cal1 = m1.getDate();      // the calendar for the first meeting
            Calendar cal2 = m2.getDate();   // the calendar for the second meeting
            long cal1Time = cal1.getTimeInMillis() ;
            long cal2Time = cal2.getTimeInMillis();
            /** @return a number which will unambiguously place each calendar in order (using milliseconds)
             *  1 if cal1Time is greater than cal2Time, -1 for vice-versa and 0 for equality*/
            return (cal1Time > cal2Time) ? 1 : (cal1Time < cal2Time) ? -1 : 0;        // used ternary operator to save space
        }
    };
}
