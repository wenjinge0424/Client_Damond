package lu.eminozandac.ondamondclinet.ui.requestpatrol;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import lu.eminozandac.ondamondclinet.MainActivity;
import lu.eminozandac.ondamondclinet.R;
import lu.eminozandac.ondamondclinet.model.TrackRequestModel;

public class CreateRPFinalFragment extends Fragment implements View.OnClickListener {

    List<TrackRequestModel> mTrackRequestList = new ArrayList<>();

    MainActivity mActivity;
    LinearLayout layout_running;
    LinearLayout layout_complete;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_rpfinal, container, false);

        mActivity = MainActivity.instance;

        layout_running = root.findViewById(R.id.layout_running);
        layout_complete = root.findViewById(R.id.layout_complete);

        root.findViewById(R.id.btn_create_request).setOnClickListener(this);

        getServerData();
        return root;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_request:
                Navigation.findNavController(v).navigate(R.id.action_RPFN_to_RPOR);
                break;
        }
    }
    public void getServerData() {
        mTrackRequestList.clear();
        for (int i = 0; i < 2; i ++) {
            TrackRequestModel tmpModel = new TrackRequestModel();
            tmpModel.isComplete = false;
            tmpModel.time = "2021/12/23 16:28";
            tmpModel.type = "Physical Response";
            mTrackRequestList.add(tmpModel);
        }
        for (int i = 0; i < 5; i ++) {
            TrackRequestModel tmpModel = new TrackRequestModel();
            tmpModel.isComplete = true;
            tmpModel.time = "2021/12/23 16:28";
            tmpModel.type = "Math Response";
            mTrackRequestList.add(tmpModel);
        }
        showData();
    }

    private void showData() {
        layout_running.removeAllViews();
        layout_complete.removeAllViews();
        for (int i = 0; i < mTrackRequestList.size(); i ++) {
            if (mTrackRequestList.get(i).isComplete) {
                LinearLayout layer = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.item_completed_request_patrol, null);
                TextView txt_date = layer.findViewById(R.id.txt_date);
                TextView txt_detail = layer.findViewById(R.id.txt_detail);
                txt_date.setText(mTrackRequestList.get(i).time);
                txt_detail.setText(mTrackRequestList.get(i).type);
                layout_complete.addView(layer);
            } else {
                LinearLayout layer = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.item_running_request_patrol, null);
                TextView txt_date = layer.findViewById(R.id.txt_date);
                TextView txt_detail = layer.findViewById(R.id.txt_detail);
                TextView txt_create_date = layer.findViewById(R.id.txt_create_date);
                TextView txt_request_home_name = layer.findViewById(R.id.txt_request_home_name);
                TextView txt_request_home_location = layer.findViewById(R.id.txt_request_home_location);
                txt_date.setText(mTrackRequestList.get(i).time);
                txt_detail.setText(mTrackRequestList.get(i).type);
                txt_request_home_location.setText("New York, US");
                txt_create_date.setText(mTrackRequestList.get(i).time);
                txt_request_home_name.setText("My Company");
                layout_running.addView(layer);
            }
        }
    }


}
