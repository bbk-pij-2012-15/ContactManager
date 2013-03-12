import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/** A meeting to be held in the future -
no methods here, just a naming class */

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting, Serializable
{
       public FutureMeetingImpl(int id, Set<Contact> set, Calendar date)
       {
           /** calls constructor in MeetingImpl */
           super(id, set, date);
       }
}
