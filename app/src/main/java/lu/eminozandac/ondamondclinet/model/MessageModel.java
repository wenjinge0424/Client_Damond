package lu.eminozandac.ondamondclinet.model;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.ArrayList;
import java.util.List;
import lu.eminozandac.ondamondclinet.listener.ExceptionListener;
import lu.eminozandac.ondamondclinet.listener.ObjectListListener;
import lu.eminozandac.ondamondclinet.utils.BaseTask;

public class MessageModel {
    public String objectId;
    public String message;
    public String type;

    public ParseUser receiver;
    public ParseUser sender;
    public ParseObject request;

    public void parse(ParseObject item) {
        if ( item == null )
            return;
        objectId = item.getObjectId();
        message = item.getString(ParseConstants.msg_message);
        receiver = item.getParseUser(ParseConstants.msg_receiver);
        sender = item.getParseUser(ParseConstants.msg_sender);
        request = item.getParseObject(ParseConstants.msg_request);
    }
    // get review list
    public static void GetList(final ObjectListListener listener) {
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery(ParseConstants.TBL_MESSAGE);
        query1.whereEqualTo(ParseConstants.msg_receiver, ParseUser.getCurrentUser());
        query1.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
        query1.include(ParseConstants.msg_receiver);
        query1.setLimit(ParseConstants.QUERY_FETCH_MAX_COUNT);

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery(ParseConstants.TBL_MESSAGE);
        query2.whereEqualTo(ParseConstants.msg_sender, ParseUser.getCurrentUser());
        query2.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
        query2.include(ParseConstants.msg_sender);
        query2.setLimit(ParseConstants.QUERY_FETCH_MAX_COUNT);

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(query1);
        queries.add(query2);
        ParseQuery<ParseObject> query = ParseQuery.or(queries);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException pe) {
                // TODO Auto-generated method stub
                if (listener != null)
                    listener.done(objects, ParseErrorHandler.handle(pe));
            }
        });
    }
    public static void Register(final MessageModel model, final ExceptionListener listener) {
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
                final ParseObject markObj = new ParseObject(ParseConstants.TBL_MESSAGE);
                markObj.put(ParseConstants.msg_message, model.message);
                markObj.put(ParseConstants.mark_sender, ParseUser.getCurrentUser());
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
