import java.util.Set;

/** A meeting to be held in the future -
no methods here, just a naming class */

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting
{
       public FutureMeetingImpl(Set<Contact> set)
       {
           super(set);
       }
}
