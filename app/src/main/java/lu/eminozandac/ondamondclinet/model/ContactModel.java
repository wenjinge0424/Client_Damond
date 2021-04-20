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

public class ContactModel {
    public String objectId;
    public String email;
    public String phone;
    public String name;

    public ParseUser owner;

    public void parse(ParseObject item) {
        if ( item == null )
            return;
        objectId = item.getObjectId();
        email = item.getString(ParseConstants.contacts_email);
        phone = item.getString(ParseConstants.contacts_phone);
        name = item.getString(ParseConstants.contacts_name);
        owner = item.getParseUser(ParseConstants.contacts_owner);
    }

    // get review list
    public static void GetList(final ObjectListListener listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.TBL_CONTACT);
        query.whereEqualTo(ParseConstants.contacts_owner, ParseUser.getCurrentUser());
        query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
        query.include(ParseConstants.contacts_owner);
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
    public static void Register(final ContactModel model, final ExceptionListener listener) {
        ParseObject reportObj = new ParseObject(ParseConstants.TBL_CONTACT);
        reportObj.put(ParseConstants.contacts_email, model.email);
        reportObj.put(ParseConstants.contacts_phone, model.phone);
        reportObj.put(ParseConstants.contacts_name, model.name);
        reportObj.put(ParseConstants.contacts_owner, ParseUser.getCurrentUser());

        reportObj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException pe) {
                // TODO Auto-generated method stub
                if (listener != null)
                    listener.done(ParseErrorHandler.handle(pe));
            }
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
