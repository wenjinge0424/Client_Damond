package lu.eminozandac.ondamondclinet.ui.myintervention;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import lu.eminozandac.ondamondclinet.MainActivity;
import lu.eminozandac.ondamondclinet.R;
import lu.eminozandac.ondamondclinet.listener.ObjectListListener;
import lu.eminozandac.ondamondclinet.model.ParseConstants;
import lu.eminozandac.ondamondclinet.model.TrackRequestModel;
import lu.eminozandac.ondamondclinet.ui.dialog.MyProgressDialog;
import lu.eminozandac.ondamondclinet.utils.DateTimeUtils;

public class MyInterventionFragment extends Fragment {

    MainActivity mActivity;
    LinearLayout layout_content;

    public MyProgressDialog dlg_progress;

    List<ShowModel> mInterventionArray = new ArrayList<>();
    class ShowModel {
        TrackRequestModel trackRequestModel;
        Boolean isShow = false;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_intervention, container, false);
        mActivity = MainActivity.instance;
        layout_content = root.findViewById(R.id.layout_content);
        dlg_progress = new MyProgressDialog(mActivity);
        getServerData();
        return root;
    }

    public void getServerData() {
        mInterventionArray.clear();
        dlg_progress.show();
        TrackRequestModel.GetList(new ObjectListListener(){
            @Override
            public void done(List<ParseObject> objects, String error) {
                dlg_progress.cancel();
                if (error == null && objects.size() > 0){
                    for (int i = 0; i< objects.size(); i++){
                        ShowModel showItem = new ShowModel();
                        TrackRequestModel model = new TrackRequestModel();
                        model.parse(objects.get(i));
                        showItem.trackRequestModel = model;
                        mInterventionArray.add(showItem);
                    }
                }
                showData();
            }
        });

    }

    private void showData() {
        layout_content.removeAllViews();
        for (int i = 0; i < mInterventionArray.size(); i ++) {
            final int position = i;
            LinearLayout layer = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.item_intervention, null);
            LinearLayout layout_location = layer.findViewById(R.id.layout_location);
            LinearLayout layout_profile = layer.findViewById(R.id.layout_profile);
            TextView txt_time = layer.findViewById(R.id.txt_time);
            TextView txt_title = layer.findViewById(R.id.txt_title);
            TextView txt_start_position = layer.findViewById(R.id.txt_start_position);
            TextView txt_end_position = layer.findViewById(R.id.txt_end_position);
            TextView txt_action_detail = layer.findViewById(R.id.txt_action_detail);
            txt_time.setText(DateTimeUtils.dateToString(mInterventionArray.get(i).trackRequestModel.created_at, "yyyy-MM-dd hh:mm a"));
            String request_type = mInterventionArray.get(i).trackRequestModel.type;
            txt_title.setText(request_type);
            if ( mInterventionArray.get(i).trackRequestModel.isTrackme() ){
                txt_start_position.setText(mInterventionArray.get(i).trackRequestModel.track_address);
                txt_end_position.setText("My location");
            }else{
                ParseObject homeAddress = mInterventionArray.get(i).trackRequestModel.address;
                txt_start_position.setText(homeAddress.getString(ParseConstants.homeadd_address));
                txt_end_position.setText("My location");
            }
            ParseUser receiver = mInterventionArray.get(i).trackRequestModel.receiver;
            txt_action_detail.setText("Mission accomplished by " + receiver.getUsername());
            layout_location.setVisibility(View.GONE);
            layout_profile.setVisibility(View.GONE);
            if (mInterventionArray.get(position).isShow) {
                layout_location.setVisibility(View.VISIBLE);
                layout_profile.setVisibility(View.VISIBLE);
            }

            layer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mInterventionArray.get(position).isShow = !mInterventionArray.get(position).isShow;
                    showData();
                }
            });
            layout_content.addView(layer);
        }
    }
}
