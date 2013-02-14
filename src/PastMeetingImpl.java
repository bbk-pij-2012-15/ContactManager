import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl implements PastMeeting extends Meeting
{
    private String meetingNotes;

    public PastMeetingImpl(Set<Contact> set, Calendar date)
    {
          this.
    }

    public void addNotes(String note)
    {
        /** @return prints a newline at the end of each added note and a dash
        at the start so the list of notes remains clear to read */
        meetingNotes += ("-" + note + "\n");
    }

    public String getNotes()
    {
        return this.meetingNotes;
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
