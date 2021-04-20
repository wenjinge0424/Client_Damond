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

public class HomeSettingModel {
    public String objectId;
    public Boolean bar_on_window;
    public int get_to_house;
    public int brand_of_alam;
    public int number_of_contact;
    public String instruction;
    public int hours_a_day;
    public int second_access;
    public Boolean video_protection;

    public ParseUser owner;

    public void parse(ParseObject item) {
        if ( item == null )
            return;
        objectId = item.getObjectId();
        bar_on_window = item.getBoolean(ParseConstants.homeset_bar_on_window);
        get_to_house = item.getInt(ParseConstants.homeset_get_to_house);
        brand_of_alam = item.getInt(ParseConstants.homeset_brand_of_alam);
        number_of_contact = item.getInt(ParseConstants.homeset_number_of_contact);
        instruction = item.getString(ParseConstants.homeset_instruction);
        hours_a_day = item.getInt(ParseConstants.homeset_hours_a_day);
        second_access = item.getInt(ParseConstants.homeset_second_access);
        video_protection = item.getBoolean(ParseConstants.homeset_video_protection);
        owner = item.getParseUser(ParseConstants.homeset_owner);
    }
    // get review list
    public static void GetList(final ObjectListListener listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.TBL_HOMESETTING);
        query.whereEqualTo(ParseConstants.homeset_owner, ParseUser.getCurrentUser());
        query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
        query.include(ParseConstants.homeset_owner);
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
    public static void Register(final HomeSettingModel model, final ExceptionListener listener) {
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
                final ParseObject equipRequestObj = new ParseObject(ParseConstants.TBL_HOMESETTING);
                equipRequestObj.put(ParseConstants.homeset_bar_on_window, model.bar_on_window);
                equipRequestObj.put(ParseConstants.homeset_get_to_house, model.get_to_house);
                equipRequestObj.put(ParseConstants.homeset_brand_of_alam, model.brand_of_alam);
                equipRequestObj.put(ParseConstants.homeset_number_of_contact, model.number_of_contact);
                equipRequestObj.put(ParseConstants.homeset_instruction, model.instruction);
                equipRequestObj.put(ParseConstants.homeset_hours_a_day, model.hours_a_day);
                equipRequestObj.put(ParseConstants.homeset_second_access, model.second_access);
                equipRequestObj.put(ParseConstants.homeset_video_protection, model.video_protection);
                equipRequestObj.put(ParseConstants.homeset_owner, ParseUser.getCurrentUser());
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
