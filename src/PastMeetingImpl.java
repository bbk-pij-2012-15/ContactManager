import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting
{
    private String meetingNotes = "";   // initialized to the empty string, so will never be returned null

    public PastMeetingImpl(int id, Set<Contact> set, Calendar date)
    {
        /** calls the constructor in MeetingImpl */
        super(id, set, date);
    }

    public void addNotes(String note)
    {
        /** method prints a newline at the end of each added note and a dash
        *   at the start so the list of notes remains clear to read */
        this.meetingNotes += ("-" + note + "\n");
    }

    public String getNotes()
    {
        /** @return any and all notes associated with the meeting.
         *  If there are no notes, the empty string is returned */
        return this.meetingNotes;
    }

    public int getId()
    {
        /** uses MeetingImpl's getId() method */
        return super.getId();
    }

    public Calendar getDate()
    {
        /** uses MeetingImpl's getDate() method */
        return super.getDate();
    }

    public Set<Contact> getContacts()
    {
        /** uses MeetingImpl's getContacts() method */
        return super.getContacts();
    }

    protected String getMeetingInfo()
    {
        /** uses MeetingImpl's getMeetingInfo() method */
        return super.getMeetingInfo();
    }
}
