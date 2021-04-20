package lu.eminozandac.ondamondclinet.ui.request;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.Timer;
import java.util.TimerTask;

import lu.eminozandac.ondamondclinet.R;

public class CreateTrackMeFragment extends Fragment implements View.OnClickListener {

    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_create_track_me, container, false);
        root.findViewById(R.id.img_close).setOnClickListener(this);
        createTrackMeRequest();
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_close:
                Navigation.findNavController(v).popBackStack();
                break;
        }
    }
    private void createTrackMeRequest(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // this code will be executed after 2 seconds
                Navigation.findNavController(root).navigate(R.id.action_CREATTRACK_to_MAPTRACK);
            }
        }, 2000);
    }
}
