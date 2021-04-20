package lu.eminozandac.ondamondclinet.model;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.List;
import lu.eminozandac.ondamondclinet.listener.ExceptionListener;
import lu.eminozandac.ondamondclinet.listener.ObjectListListener;
import lu.eminozandac.ondamondclinet.utils.BaseTask;

public class NotificationModel {
    public String objectId;
    public int type;

    public ParseUser receiver;
    public ParseUser sender;
    public ParseObject request;

    public void parse(ParseObject item) {
        if ( item == null )
            return;
        objectId = item.getObjectId();
        type = item.getInt(ParseConstants.noti_type);
        receiver = item.getParseUser(ParseConstants.noti_receiver);
        sender = item.getParseUser(ParseConstants.noti_sender);
        request = item.getParseObject(ParseConstants.noti_request);
    }
    // get review list
    public static void GetList(final ObjectListListener listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.TBL_NOTIFICATION);
        query.whereEqualTo(ParseConstants.noti_receiver, ParseUser.getCurrentUser());
        query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
        query.include(ParseConstants.noti_receiver);
        query.include(ParseConstants.noti_sender);
        query.include(ParseConstants.noti_request);
        query.setLimit(ParseConstants.QUERY_FETCH_MAX_COUNT);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException pe) {
                // TODO Auto-generated method stub
                if (listener != null)
                    listener.done(objects, ParseErrorHandler.handle(pe));
            }
        });
    }
    public static void Register(final NotificationModel model, final ExceptionListener listener) {
        BaseTask.run(new BaseTask.TaskListener() {

            @Override
            public Object onTaskRunning(int taskId, Object data) {
                // TODO Auto-generated method stub
                try {
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onTaskResult(int taskId, Object result) {
                // TODO Auto-generated method stub
                final ParseObject markObj = new ParseObject(ParseConstants.TBL_NOTIFICATION);
                markObj.put(ParseConstants.noti_type, model.type);
                markObj.put(ParseConstants.noti_receiver, model.receiver);
                markObj.put(ParseConstants.noti_sender, ParseUser.getCurrentUser());
                markObj.put(ParseConstants.noti_request, model.request);
                markObj.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        // TODO Auto-generated method stub
                        if (listener != null)
                            listener.done(ParseErrorHandler.handle(e));
                    }
                });
            }
            @Override
            public void onTaskProgress(int taskId, Object... values) {}
            @Override
            public void onTaskPrepare(int taskId, Object data) {}
            @Override
            public void onTaskCancelled(int taskId) {}
        });
    }
}
