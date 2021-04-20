package lu.eminozandac.ondamondclinet.model;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import lu.eminozandac.ondamondclinet.listener.ExceptionListener;
import lu.eminozandac.ondamondclinet.listener.ObjectListListener;
import lu.eminozandac.ondamondclinet.utils.BaseTask;

public class HomeAddressModel {
    public String objectId;
    public String home_type;
    public String address;
    public ParseGeoPoint location;
    ParseUser owner;
    public long currentLocation_lat;
    public long currentLocation_long;

    public void parse(ParseObject item) {
        if ( item == null )
            return;
        objectId = item.getObjectId();
        home_type = item.getString(ParseConstants.homeadd_home_type);
        address = item.getString(ParseConstants.homeadd_address);
        location = item.getParseGeoPoint(ParseConstants.homeadd_location);
        owner = item.getParseUser(ParseConstants.homeadd_owner);
        if(location != null) {
            currentLocation_lat = (long) location.getLatitude();
            currentLocation_long = (long) location.getLatitude();
        }
    }
    // get review list
    public static void GetList(final ObjectListListener listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.TBL_HOMEADDRESS);
        query.whereEqualTo(ParseConstants.homeadd_owner, ParseUser.getCurrentUser());
        query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
        query.include(ParseConstants.homeadd_owner);
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
    public static void Register(final HomeAddressModel model, final ExceptionListener listener) {
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
                final ParseObject equipRequestObj = new ParseObject(ParseConstants.TBL_HOMEADDRESS);
                equipRequestObj.put(ParseConstants.homeadd_home_type, model.home_type);
                equipRequestObj.put(ParseConstants.homeadd_address, model.address);
                equipRequestObj.put(ParseConstants.homeadd_location, model.location);
                equipRequestObj.put(ParseConstants.homeadd_owner, ParseUser.getCurrentUser());
                equipRequestObj.saveInBackground(new SaveCallback() {
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
    public static void Update(ParseObject projectObj, final ExceptionListener listener) {
        projectObj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (listener != null)
                    listener.done(ParseErrorHandler.handle(e));
            }
        });
    }
}
