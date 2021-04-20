package lu.eminozandac.ondamondclinet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import lu.eminozandac.ondamondclinet.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    public static LoginActivity instance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_login);

        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_signup).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                onLogin();
                break;
            case R.id.btn_signup:
                onSignUp();
                break;
        }
    }

    private void onLogin(){
        startActivity(new Intent(instance, LoginEmailActivity.class));
        finish();
    }

    private void onSignUp(){

    }
}
