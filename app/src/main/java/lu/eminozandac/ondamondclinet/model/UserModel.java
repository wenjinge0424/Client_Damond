package lu.eminozandac.ondamondclinet.model;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import java.util.ArrayList;
import java.util.List;
import lu.eminozandac.ondamondclinet.listener.ExceptionListener;
import lu.eminozandac.ondamondclinet.listener.UserListListener;
import lu.eminozandac.ondamondclinet.listener.UserListener;
import lu.eminozandac.ondamondclinet.utils.BaseTask;
import lu.eminozandac.ondamondclinet.utils.LocationUtil;

public class UserModel {
    public String objectId;
    public Boolean available;
    public String first_name;
    public String last_name;
    public String phone;
    public String username;
    public String user_name;
    public String email;
    public String password;
    public String family;
    public String height;
    public String description;
    public String staff_comment;
    public String staff_postal;
    public String staff_city;
    public String staff_birthday;
    public String staff_nationality;
    public ParseGeoPoint currentLocation;
    public long currentLocation_lat;
    public long currentLocation_long;

    public ParseFile avatar;

    public void parse(ParseUser user) {
        if (user == null)
            return;
        objectId = user.getObjectId();
        available = user.getBoolean(ParseConstants.user_available);
        first_name = user.getString(ParseConstants.user_first_name);
        last_name = user.getString(ParseConstants.user_last_name);
        phone = user.getString(ParseConstants.user_phone);
        username = user.getUsername();
        email = user.getEmail();
        password = user.getString(ParseConstants.user_password);
        user_name = user.getString(ParseConstants.user_user_name);
        family = user.getString(ParseConstants.user_family);
        height = user.getString(ParseConstants.user_height);
        description = user.getString(ParseConstants.user_description);
        staff_comment = user.getString(ParseConstants.user_staff_comment);
        staff_postal = user.getString(ParseConstants.user_staff_postal);
        staff_city = user.getString(ParseConstants.user_staff_city);
        staff_birthday = user.getString(ParseConstants.user_staff_birthday);
        staff_nationality = user.getString(ParseConstants.user_staff_nationality);
        currentLocation = user.getParseGeoPoint(ParseConstants.user_currentLocation);
        if(currentLocation != null) {
            currentLocation_lat = (long) currentLocation.getLatitude();
            currentLocation_long = (long) currentLocation.getLatitude();
        }
        avatar = user.getParseFile(ParseConstants.KEY_AVATAR);
    }
    public static ParseGeoPoint GetLastGeoPoint() {
        ParseGeoPoint currPoint = new ParseGeoPoint();
        if (ParseUser.getCurrentUser() != null) {
            currPoint = ParseUser.getCurrentUser().getParseGeoPoint(ParseConstants.user_currentLocation);
        }
        try {
            if ( LocationUtil.getInstance().mCurrentLocation != null) {
                currPoint = new ParseGeoPoint(LocationUtil.getInstance().mCurrentLocation.getLatitude(),
                        LocationUtil.getInstance().mCurrentLocation.getLongitude());
            }
        } catch (Exception ex) {
            currPoint = new ParseGeoPoint();
        }

        return currPoint;
    }
    public static void fetch(final UserListListener listener){
        final ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.fetchIfNeededInBackground(new GetCallback<ParseUser>() {

            @Override
            public void done(ParseUser object, ParseException e) {
                if (listener != null) {
                    List<ParseUser> users = new ArrayList<>();
                    listener.done(users, "error");
                }
            }
        });
    }
    // register
    public static void Register(final UserModel model, final UserListener listener) {
        BaseTask.run(new BaseTask.TaskListener() {

            @Override
            public Object onTaskRunning(int taskId, Object data) {
                // TODO Auto-generated method stub
                try {
                    if (model.avatar != null)
                        model.avatar.save();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onTaskResult(int taskId, Object result) {
                // TODO Auto-generated method stub
                final ParseUser userObj = new ParseUser();

                userObj.setUsername(model.username);
                userObj.setEmail(model.email);
                userObj.setPassword(model.password);
                userObj.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        // TODO Auto-generated method stub
                        if (e == null) {
                            Login(model.username, model.password, new UserListener() {
                                @Override
                                public void done(ParseUser userObj, String error) {
                                    // TODO Auto-generated method stub
                                    if (listener != null)
                                        listener.done(userObj, error);
                                }
                            });

                        } else {
                            if (listener != null)
                                listener.done(userObj, ParseErrorHandler.handle(e));
                        }
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

    // login
    public static void Login(final String username, final String password, final UserListener listener) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(final ParseUser userObj, ParseException e) {
                if (e == null) { // login success
                    // for push notification
                    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                    installation.put(ParseConstants.KEY_OWNER, userObj);
                    installation.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            // TODO Auto-generated method stub
                            // update location
                            userObj.put(ParseConstants.KEY_PREVIEW_PASSWORD, password);
                            userObj.put(ParseConstants.KEY_IS_ONLINE, true);
                            userObj.saveInBackground();
                            if (listener != null)
                                listener.done(userObj, ParseErrorHandler.handle(e));
                        }
                    });

                } else {
                    if (listener != null)
                        listener.done(null, ParseErrorHandler.handle(e));
                }
            }
        });
    }
    // log out
    public static void Logout(final ExceptionListener listener) {
        // remove parse installation
        ParseUser userObj = ParseUser.getCurrentUser();
        userObj.put(ParseConstants.KEY_IS_ONLINE, false);
        userObj.saveInBackground();
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.remove(ParseConstants.KEY_OWNER);
        installation.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException arg0) {
                // TODO Auto-generated method stub
                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        // TODO Auto-generated method stub
                        if (e == null) {
                            if (listener != null)
                                listener.done(null);
                        } else {
                            if (listener != null)
                                listener.done(ParseErrorHandler.handle(e));
                        }
                    }
                });
            }
        });
    }
    // get all users
    public static void GetUserList(final UserListListener listener) {
        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.setLimit(ParseConstants.QUERY_FETCH_MAX_COUNT);

        userQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                // TODO Auto-generated method stub
                if (listener != null)
                    listener.done(users, ParseErrorHandler.handle(e));
            }
        });
    }

    public static void Update(ParseUser obj, final ExceptionListener listener) {
        obj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (listener != null)
                    listener.done(ParseErrorHandler.handle(e));
            }
        });
    }

    // get user object from email
    public static void GetUserFromEmail(final String email, final UserListener listener) {
        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo(ParseConstants.user_username, email);

        userQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                // TODO Auto-generated method stub
                if (listener != null)
                    listener.done(user, ParseErrorHandler.handle(e));
            }
        });
    }
    // get full name
    public static String GetFullName(final  ParseUser mUser) {
        return mUser.getString(ParseConstants.user_first_name) + " " + mUser.getString(ParseConstants.user_last_name);
    }
    // reset password
    public static void RequestPasswordReset(String strEmail, final ExceptionListener listener) {
        ParseUser.requestPasswordResetInBackground(strEmail, new RequestPasswordResetCallback() {
            public void done(ParseException e) {
                if (listener != null)
                    listener.done(e == null ? null : e.getMessage());
            }
        });
    }
}
