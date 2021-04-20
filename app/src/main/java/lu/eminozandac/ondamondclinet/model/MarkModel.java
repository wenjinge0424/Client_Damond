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

public class MarkModel {
    public String objectId;
    public long mark;

    public ParseUser recevier;
    public ParseUser sender;
    public String request;

    public void parse(ParseObject item) {
        if ( item == null )
            return;
        objectId = item.getObjectId();
        mark = item.getLong(ParseConstants.mark_mark);
        recevier = item.getParseUser(ParseConstants.mark_recevier);
        sender = item.getParseUser(ParseConstants.mark_sender);
        request = item.getString(ParseConstants.mark_request);
    }
    // get review list
    public static void GetList(final ObjectListListener listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.TBL_MARK);
        query.whereEqualTo(ParseConstants.mark_recevier, ParseUser.getCurrentUser());
        query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
        query.include(ParseConstants.mark_recevier);
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
    public static void Register(final MarkModel model, final ExceptionListener listener) {
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
                final ParseObject markObj = new ParseObject(ParseConstants.TBL_MARK);
                markObj.put(ParseConstants.mark_mark, model.mark);
                markObj.put(ParseConstants.mark_recevier, model.recevier);
                markObj.put(ParseConstants.mark_sender, model.sender);
                markObj.put(ParseConstants.mark_request, model.request);
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
