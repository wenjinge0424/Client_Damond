package lu.eminozandac.ondamondclinet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.parse.ParseUser;
import lu.eminozandac.ondamondclinet.MainActivity;
import lu.eminozandac.ondamondclinet.R;
import lu.eminozandac.ondamondclinet.listener.UserListener;
import lu.eminozandac.ondamondclinet.model.UserModel;
import lu.eminozandac.ondamondclinet.utils.CommonUtil;
import lu.eminozandac.ondamondclinet.utils.MessageUtil;

public class LoginEmailActivity extends BaseActivity implements View.OnClickListener {
    public static LoginEmailActivity instance = null;

    EditText edt_email;
    EditText edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_login_email);

        findViewById(R.id.btn_login).setOnClickListener(this);
        edt_email = findViewById(R.id.edt_user_name);
        edt_password = findViewById(R.id.edt_user_password);
        edt_email.setText("client@test.com");
        edt_password.setText("aaaaaa");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                onLogin();
                break;
        }
    }

    private void onLogin() {
        if(isValid() == true) {
            final String email = edt_email.getText().toString().trim();
            String password = edt_password.getText().toString().trim();
            dlg_progress.show();
            UserModel.Login(email, password, new UserListener() {
                @Override
                public void done(ParseUser user, String error) {
                    if (error == null) {
                        dlg_progress.cancel();
                        startActivity(new Intent(instance, MainActivity.class));
                        finish();
                    } else {
                        dlg_progress.cancel();
                        MessageUtil.showError(instance, R.string.invalid_incorrect_password);
                    }
                }
            });
        }
    }

    private boolean isValid() {
        String email = edt_email.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        if ( TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            MessageUtil.showError(instance, R.string.valid_No_email_password);
            edt_email.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            MessageUtil.showError(instance, R.string.valid_No_email);
            edt_email.requestFocus();
            return false;
        }
        if (!CommonUtil.isValidEmail(email)) {
            MessageUtil.showError(instance, R.string.valid_Invalid_email);
            edt_email.requestFocus();
            edt_email.setSelection(edt_email.getText().length());
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            MessageUtil.showError(instance, R.string.valid_No_password);
            edt_password.requestFocus();
            return false;
        }
        return true;
    }
}
