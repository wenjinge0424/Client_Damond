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

public class EquipRequestModel {
    public String objectId;
    public Boolean main_residence;
    public int burglary;
    public int access;
    public String postal;
    public int hours_a_day;
    public int second_access;
    public int number_occupant;
    public int location;
    public int home_type;

    public ParseUser protection_for;
    public ParseUser owner;

    public void parse(ParseObject item) {
        if ( item == null )
            return;
        objectId = item.getObjectId();
        main_residence = item.getBoolean(ParseConstants.equip_main_residence);
        burglary = item.getInt(ParseConstants.equip_burglary);
        access = item.getInt(ParseConstants.equip_access);
        postal = item.getString(ParseConstants.equip_postal);
        hours_a_day = item.getInt(ParseConstants.equip_hours_a_day);
        second_access = item.getInt(ParseConstants.equip_second_access);
        number_occupant = item.getInt(ParseConstants.equip_number_occupant);
        location = item.getInt(ParseConstants.equip_location);
        home_type = item.getInt(ParseConstants.equip_home_type);
        protection_for = item.getParseUser(ParseConstants.equip_protection_for);
        owner = item.getParseUser(ParseConstants.equip_owner);
    }

    // get review list
    public static void GetList(final ObjectListListener listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.TBL_EQUIPREQUEST);
        query.whereEqualTo(ParseConstants.equip_owner, ParseUser.getCurrentUser());
        query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
        query.include(ParseConstants.equip_owner);
        query.include(ParseConstants.equip_protection_for);
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

    // create photo
    public static void Register(final EquipRequestModel model, final ExceptionListener listener) {
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
                final ParseObject equipRequestObj = new ParseObject(ParseConstants.TBL_EQUIPREQUEST);
                equipRequestObj.put(ParseConstants.equip_owner, ParseUser.getCurrentUser());
                equipRequestObj.put(ParseConstants.equip_main_residence, model.main_residence);
                equipRequestObj.put(ParseConstants.equip_burglary, model.burglary);
                equipRequestObj.put(ParseConstants.equip_access, model.access);
                equipRequestObj.put(ParseConstants.equip_postal, model.postal);
                equipRequestObj.put(ParseConstants.equip_hours_a_day, model.hours_a_day);
                equipRequestObj.put(ParseConstants.equip_second_access, model.second_access);
                equipRequestObj.put(ParseConstants.equip_number_occupant, model.number_occupant);
                equipRequestObj.put(ParseConstants.equip_location, model.location);
                equipRequestObj.put(ParseConstants.equip_home_type, model.home_type);
                equipRequestObj.put(ParseConstants.equip_protection_for, model.protection_for);
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

    public static void Update(ParseObject obj, final ExceptionListener listener) {
        obj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (listener != null)
                    listener.done(ParseErrorHandler.handle(e));
            }
        });
    }
}
