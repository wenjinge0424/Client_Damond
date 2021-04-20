package lu.eminozandac.ondamondclinet.model;

import com.parse.DeleteCallback;
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

public class TempRequestModel {
    public String objectId;
    public int state;
    public int type;
    public String location;

    public ParseUser owner;
    public ParseUser accepter;
    public List<ParseUser> officers;
    public List<ParseUser> decliner;
    public String request;

    public void parse(ParseObject item) {
        if ( item == null )
            return;
        objectId = item.getObjectId();
        state = item.getInt(ParseConstants.temp_state);
        type = item.getInt(ParseConstants.temp_type);
        location = item.getString(ParseConstants.temp_location);
        owner = item.getParseUser(ParseConstants.temp_owner);
        accepter = item.getParseUser(ParseConstants.temp_accepter);
//        officers = item.getJSONArray(ParseConstants.temp_officers);
//        decliner = item.getJSONArray(ParseConstants.temp_decliner);
        request = item.getString(ParseConstants.temp_request);
    }
    // get review list
    public static void GetList(final ObjectListListener listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.TBL_TEMP_REQUEST);
        List<ParseUser> containUsers = new ArrayList<ParseUser>();
        containUsers.add(ParseUser.getCurrentUser());
        query.whereContainedIn(ParseConstants.temp_officers, containUsers);
        query.whereEqualTo(ParseConstants.temp_state, 0);
        query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
        query.include(ParseConstants.temp_owner);
        query.include(ParseConstants.temp_officers);
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
    public static void Register(final TempRequestModel model, final ExceptionListener listener) {
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
                final ParseObject markObj = new ParseObject(ParseConstants.TBL_TEMP_REQUEST);
                markObj.put(ParseConstants.temp_state, model.state);
                markObj.put(ParseConstants.temp_type, model.type);
                markObj.put(ParseConstants.temp_location, model.location);
                markObj.put(ParseConstants.temp_owner, ParseUser.getCurrentUser());
                markObj.put(ParseConstants.temp_officers, model.officers);
                markObj.put(ParseConstants.temp_request, model.request);

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
    // update
    public static void Update(ParseObject obj, final ExceptionListener listener) {
        obj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (listener != null)
                    listener.done(ParseErrorHandler.handle(e));
            }
        });
    }
    public static void Delete(final ParseObject obj, final ExceptionListener listener) {
        obj.deleteInBackground(new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                if (listener != null)
                    listener.done(ParseErrorHandler.handle(e));
            }
        });
    }

}
