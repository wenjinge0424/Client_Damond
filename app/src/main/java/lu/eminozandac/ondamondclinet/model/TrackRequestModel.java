package lu.eminozandac.ondamondclinet.model;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.text.TextUtils;
import lu.eminozandac.ondamondclinet.listener.ExceptionListener;
import lu.eminozandac.ondamondclinet.listener.ObjectListListener;
import lu.eminozandac.ondamondclinet.utils.BaseTask;

public class TrackRequestModel {
    public String objectId;
    public String track_address;
    public String track_from_address;
    public Boolean isComplete;
    public Date start_date;
    public String time;
    public String type;
    public String repeat;
    public String tmp_reques_id;

    public ParseGeoPoint track_location;
    public ParseObject sender_home;
    public ParseObject receiver_home;
    public ParseObject address;

    public ParseUser sender;
    public ParseUser receiver;

    public Date created_at;

    public boolean isTrackme(){
        if ( TextUtils.equals(type, "track me") ){
            return true;
        }
        return false;
    }

    public void parse(ParseObject item) {
        if ( item == null )
            return;
        objectId = item.getObjectId();
        track_address = item.getString(ParseConstants.track_track_address);
        track_from_address = item.getString(ParseConstants.track_track_from_address);
        isComplete = item.getBoolean(ParseConstants.track_isComplete);
        start_date = item.getDate(ParseConstants.track_start_date);
        time = item.getString(ParseConstants.track_time);
        type = item.getString(ParseConstants.track_type);
        repeat = item.getString(ParseConstants.track_repeat);
        tmp_reques_id = item.getString(ParseConstants.track_tmp_reques_id);
        track_location = item.getParseGeoPoint(ParseConstants.track_track_location);
        sender_home = item.getParseObject(ParseConstants.track_sender_home);
        receiver_home = item.getParseObject(ParseConstants.track_receiver_home);
        address = item.getParseObject(ParseConstants.track_address);
        sender = item.getParseUser(ParseConstants.track_sender);
        receiver = item.getParseUser(ParseConstants.track_receiver);
        created_at = item.getCreatedAt();
    }
    public static void GetList(final ObjectListListener listener) {
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery(ParseConstants.TBL_TRACK_REQUEST);
        query1.whereEqualTo(ParseConstants.track_sender, ParseUser.getCurrentUser());

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery(ParseConstants.TBL_TRACK_REQUEST);
        query2.whereEqualTo(ParseConstants.track_receiver, ParseUser.getCurrentUser());

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(query1);
        queries.add(query2);
        ParseQuery<ParseObject> query = ParseQuery.or(queries);
        query.include(ParseConstants.track_sender);
        query.include(ParseConstants.track_receiver);
        query.include(ParseConstants.track_address);
        query.include(ParseConstants.track_sender_home);
        query.include(ParseConstants.track_receiver_home);
        query.setLimit(ParseConstants.QUERY_FETCH_MAX_COUNT);
        query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException pe) {
                // TODO Auto-generated method stub
                if (listener != null)
                    listener.done(objects, ParseErrorHandler.handle(pe));
            }
        });
    }
    public static void GetActiveList(final ObjectListListener listener) {
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery(ParseConstants.TBL_TRACK_REQUEST);
        query1.whereEqualTo(ParseConstants.track_sender, ParseUser.getCurrentUser());

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery(ParseConstants.TBL_TRACK_REQUEST);
        query2.whereEqualTo(ParseConstants.track_receiver, ParseUser.getCurrentUser());

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(query1);
        queries.add(query2);
        ParseQuery<ParseObject> query = ParseQuery.or(queries);
        query.whereEqualTo(ParseConstants.track_isComplete, false);
        query.include(ParseConstants.track_sender);
        query.include(ParseConstants.track_receiver);
        query.include(ParseConstants.track_address);
        query.include(ParseConstants.track_sender_home);
        query.include(ParseConstants.track_receiver_home);
        query.setLimit(ParseConstants.QUERY_FETCH_MAX_COUNT);
        query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException pe) {
                // TODO Auto-generated method stub
                if (listener != null)
                    listener.done(objects, ParseErrorHandler.handle(pe));
            }
        });
    }
    public static void Register(final TrackRequestModel model, final ExceptionListener listener) {
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
                final ParseObject markObj = new ParseObject(ParseConstants.TBL_TRACK_REQUEST);
                markObj.put(ParseConstants.track_track_address, model.track_address);
                markObj.put(ParseConstants.track_track_from_address, model.track_from_address);
                markObj.put(ParseConstants.track_isComplete, false);
                markObj.put(ParseConstants.track_start_date, model.start_date);
                markObj.put(ParseConstants.track_time, model.time);
                markObj.put(ParseConstants.track_repeat, model.repeat);
                if(TextUtils.isEmpty(model.tmp_reques_id) != true){
                    markObj.put(ParseConstants.track_tmp_reques_id, model.tmp_reques_id);
                }
                if(model.track_location != null){
                    markObj.put(ParseConstants.track_track_location, model.track_location);
                }
                if(model.sender_home != null) {
                    markObj.put(ParseConstants.track_sender_home, model.sender_home);
                }
                if(model.receiver_home != null) {
                    markObj.put(ParseConstants.track_receiver_home, model.receiver_home);
                }
                if(model.address != null) {
                    markObj.put(ParseConstants.track_address, model.address);
                }
                markObj.put(ParseConstants.track_sender, ParseUser.getCurrentUser());
                markObj.put(ParseConstants.track_receiver, model.receiver);

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
