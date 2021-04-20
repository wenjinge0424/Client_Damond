package lu.eminozandac.ondamondclinet.ui.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.File;
import lu.eminozandac.ondamondclinet.MainActivity;
import lu.eminozandac.ondamondclinet.R;
import lu.eminozandac.ondamondclinet.model.FileModel;
import lu.eminozandac.ondamondclinet.model.ParseConstants;
import lu.eminozandac.ondamondclinet.model.ParseErrorHandler;
import lu.eminozandac.ondamondclinet.model.UserModel;
import lu.eminozandac.ondamondclinet.ui.view.CircleImageView;
import lu.eminozandac.ondamondclinet.utils.BaseTask;
import lu.eminozandac.ondamondclinet.utils.CommonUtil;
import lu.eminozandac.ondamondclinet.utils.MessageUtil;
import lu.eminozandac.ondamondclinet.utils.ResourceUtil;

public class UserProfileFragment extends Fragment implements View.OnClickListener {
    public static UserProfileFragment instance;
    CircleImageView img_avatar;
    EditText edt_user_name;
    EditText edt_first_name;
    EditText edt_last_name;
    EditText edt_phone_number;
    EditText edt_email;
    EditText edt_password;
    EditText edt_height;
    EditText edt_family;
    EditText edt_description;

    MainActivity mActivity;
    final int PICTURE_PICK = 1000;
    final int CAMERA_CAPTURE = 1001;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        instance = this;
        mActivity = MainActivity.instance;
        View root = inflater.inflate(R.layout.fragment_user_profile, container, false);
        img_avatar = root.findViewById(R.id.img_avatar);
        edt_user_name = root.findViewById(R.id.edt_user_name);
        edt_first_name = root.findViewById(R.id.edt_first_name);
        edt_last_name = root.findViewById(R.id.edt_last_name);
        edt_phone_number = root.findViewById(R.id.edt_phone_number);
        edt_email = root.findViewById(R.id.edt_email);
        edt_password = root.findViewById(R.id.edt_password);
        edt_height = root.findViewById(R.id.edt_height);
        edt_family = root.findViewById(R.id.edt_family);
        edt_description = root.findViewById(R.id.edt_description);
        root.findViewById(R.id.img_avatar).setOnClickListener(this);
        root.findViewById(R.id.btn_confirm).setOnClickListener(this);
        edt_first_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !TextUtils.isEmpty(edt_first_name.getText().toString().trim()))
                    save(0, null);
                return false;
            }
        });
        edt_last_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !TextUtils.isEmpty(edt_last_name.getText().toString().trim()))
                    save(1, null);
                return false;
            }
        });
        edt_phone_number.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !TextUtils.isEmpty(edt_phone_number.getText().toString().trim()))
                    save(2, null);
                return false;
            }
        });
        edt_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !TextUtils.isEmpty(edt_password.getText().toString().trim()))
                    save(3, null);
                return false;
            }
        });
        edt_height.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !TextUtils.isEmpty(edt_height.getText().toString().trim()))
                    save(4, null);
                return false;
            }
        });
        edt_family.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !TextUtils.isEmpty(edt_family.getText().toString().trim()))
                    save(5, null);
                return false;
            }
        });
        edt_description.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !TextUtils.isEmpty(edt_description.getText().toString().trim()))
                    save(6, null);
                return false;
            }
        });
        initialize();
        return root;
    }

    private void initialize() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        UserModel userModel  = new UserModel();
        userModel.parse(currentUser);
        edt_user_name.setText(userModel.user_name);
        edt_first_name.setText(userModel.first_name);
        edt_last_name.setText(userModel.last_name);
        edt_phone_number.setText(userModel.phone);
        edt_email.setText(userModel.user_name);
        edt_password.setText("");
        edt_height.setText(userModel.height);
        edt_family.setText(userModel.family);
        edt_description.setText(userModel.description);
        if (!TextUtils.isEmpty(userModel.avatar.getUrl()))
            Picasso.get().load(userModel.avatar.getUrl()).into(img_avatar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_avatar:
                if (CommonUtil.verifyStoragePermissions(CommonUtil.TYPE_CAMERA_PERMISSION, mActivity))
                    showPhotoDialog();
                else
                    MessageUtil.showError(mActivity, R.string.msg_error_permission);
                break;
            case R.id.btn_confirm:
                Navigation.findNavController(v).navigate(R.id.action_PROFILE_to_HOMESETTING);
                break;
        }
    }

    private void showPhotoDialog() {
        new AlertDialog.Builder(mActivity)
                .setTitle(R.string.upload_photo)
                .setPositiveButton(R.string.select_gallery, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        chooseTakePhoto(false);
                    }
                })
                .setNegativeButton(R.string.take_new_photo, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        chooseTakePhoto(true);
                    }
                })
                .show();
    }

    private void chooseTakePhoto(boolean isTake) {
        if (!isTake) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICTURE_PICK);
        } else {
            try {
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(ResourceUtil.getAvatarFilePath());
                if (file.exists())
                    file.delete();
                Uri photoURI = FileProvider.getUriForFile(mActivity, mActivity.getApplicationContext().getPackageName() + ".provider", file);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(captureIntent, CAMERA_CAPTURE);
            } catch (ActivityNotFoundException anfe) {
                String errorMessage = "Whoops - your device doesn't support capturing images!";
                Toast toast = Toast.makeText(mActivity, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!CommonUtil.verifyStoragePermissions(CommonUtil.TYPE_CAMERA_PERMISSION, mActivity)) {
            MessageUtil.showToast(mActivity, R.string.msg_error_permission);
            return;
        }

        if (requestCode == PICTURE_PICK && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(mActivity, data);
            startCropImageActivity(imageUri);
        }

        if (requestCode == CAMERA_CAPTURE && resultCode == Activity.RESULT_OK) {
            File file = new File(ResourceUtil.getAvatarFilePath());
            Uri photoURI = FileProvider.getUriForFile(mActivity, mActivity.getApplicationContext().getPackageName() + ".provider", file);
            startCropImageActivity(photoURI);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == mActivity.RESULT_OK) {
                String strFileName = ResourceUtil.getAvatarFilePath();
                try {
                    Bitmap bm = ResourceUtil.decodeUri(mActivity, result.getUri(), FileModel.PHOTO_SIZE);
                    if (bm != null) {
                        ResourceUtil.saveBitmapToSdcard(bm, strFileName);
                        img_avatar.setImageDrawable(new BitmapDrawable(bm));
                        saveAvatar();
                    } else {
                        Log.i(getString(R.string.app_name), "Bitmap is null");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                MessageUtil.showError(mActivity, "Cropping failed: " + result.getError());
            }
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .setAspectRatio(1, 1)
                .start(mActivity);
    }

    private void saveAvatar() {
        final UserModel model = new UserModel();
        model.avatar = FileModel.createParseFile("avatar.png", ResourceUtil.getAvatarFilePath());
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
                save(7, model.avatar);
            }

            @Override
            public void onTaskProgress(int taskId, Object... values) {}
            @Override
            public void onTaskPrepare(int taskId, Object data) {}
            @Override
            public void onTaskCancelled(int taskId) {}
        });
    }

    private void save(int type, ParseFile avatar) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (type == 0)
            currentUser.put(ParseConstants.user_first_name, edt_first_name.getText().toString().trim());
        else if (type == 1)
            currentUser.put(ParseConstants.user_last_name, edt_last_name.getText().toString().trim());
        else if (type == 2)
            currentUser.put(ParseConstants.user_phone, edt_phone_number.getText().toString().trim());
        else if (type == 3) {
            currentUser.setPassword(edt_password.getText().toString());
            currentUser.put(ParseConstants.KEY_PREVIEW_PASSWORD, edt_password.getText().toString().trim());
        } else if (type == 4)
            currentUser.put(ParseConstants.user_height, edt_height.getText().toString().trim());
        else if (type == 5)
            currentUser.put(ParseConstants.user_family, edt_family.getText().toString().trim());
        else if (type == 6)
            currentUser.put(ParseConstants.user_description, edt_description.getText().toString().trim());
        else if (type == 7 && avatar != null)
            currentUser.put(ParseConstants.KEY_AVATAR, avatar);
        mActivity.dlg_progress.show();
        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                mActivity.dlg_progress.cancel();
                if (e == null) {
                    MessageUtil.showToast(mActivity, R.string.Success);
                    if (MainActivity.instance != null)
                        MainActivity.instance.initialize();
                } else {
                    MessageUtil.showToast(mActivity, ParseErrorHandler.handle(e));
                }
            }
        });
    }
}
