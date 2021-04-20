package lu.eminozandac.ondamondclinet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import lu.eminozandac.ondamondclinet.model.UserModel;
import lu.eminozandac.ondamondclinet.ui.dialog.MyProgressDialog;
import lu.eminozandac.ondamondclinet.ui.map.RequestActivity;
import lu.eminozandac.ondamondclinet.ui.profile.UserProfileFragment;
import lu.eminozandac.ondamondclinet.ui.view.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static MainActivity instance = null;
    private AppBarConfiguration mAppBarConfiguration;

    CircleImageView profileView;
    TextView user_full_name;
    NavigationView navigationView;
    NavController navController;
    DrawerLayout drawer;
    public MyProgressDialog dlg_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        dlg_progress = new MyProgressDialog(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_request_patrol, R.id.nav_my_intervention, R.id.nav_need_equipment, R.id.nav_help, R.id.nav_legal_notice)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView = navigationView.getHeaderView(0);
        profileView = headerView.findViewById(R.id.img_profile_photo);
        user_full_name = headerView.findViewById(R.id.txt_user_full_name);
        headerView.findViewById(R.id.img_profile_photo).setOnClickListener(this);
        headerView.findViewById(R.id.img_close).setOnClickListener(this);
        initialize();
        showMapRequest();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_profile_photo:
                navController.navigate(R.id.nav_user_profile);
                drawer.closeDrawers();
                break;
            case R.id.img_close:
                showMapRequest();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (UserProfileFragment.instance != null)
            UserProfileFragment.instance.onActivityResult(requestCode, resultCode, data);
    }

    public void initialize() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        UserModel userModel  = new UserModel();
        userModel.parse(currentUser);
        if (!TextUtils.isEmpty(userModel.avatar.getUrl()))
            Picasso.get().load(userModel.avatar.getUrl()).into(profileView);
        user_full_name.setText(UserModel.GetFullName(currentUser));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    public void showMapRequest(){
        startActivity(new Intent(instance, RequestActivity.class));
    }
}
