package lu.eminozandac.ondamondclinet.ui.map;

import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import lu.eminozandac.ondamondclinet.R;
import lu.eminozandac.ondamondclinet.ui.BaseActivity;

public class RequestActivity extends BaseActivity {
    public static RequestActivity instance = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_request);

        final NavigationView navigationView = findViewById(R.id.nav_view);
    }
}
