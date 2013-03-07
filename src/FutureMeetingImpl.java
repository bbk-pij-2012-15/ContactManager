import java.util.Calendar;
import java.util.Set;

/** A meeting to be held in the future -
no methods here, just a naming class */

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting
{
       public FutureMeetingImpl(int id, Set<Contact> set, Calendar date)
       {
           super(id, set, date);
       }
}
