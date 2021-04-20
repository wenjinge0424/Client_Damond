package lu.eminozandac.ondamondclinet.ui.requestpatrol;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import lu.eminozandac.ondamondclinet.MainActivity;
import lu.eminozandac.ondamondclinet.R;
import lu.eminozandac.ondamondclinet.model.HomeAddressModel;
import lu.eminozandac.ondamondclinet.model.HomeSettingModel;
import lu.eminozandac.ondamondclinet.model.TrackRequestModel;

public class CreateRPLocationFragment extends Fragment implements View.OnClickListener {

    List<HomeAddressModel> mHomeList = new ArrayList<>();

    LinearLayout layout_home_list;

    MainActivity mActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_rpposition, container, false);
        layout_home_list = root.findViewById(R.id.layout_home_list);
        mActivity = MainActivity.instance;
        root.findViewById(R.id.btn_confirm).setOnClickListener(this);
        getServerData();
        return root;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                Navigation.findNavController(v).navigate(R.id.action_RPPS_to_RPFN);
                break;
        }
    }
    public void getServerData() {
        mHomeList.clear();
        for (int i = 0; i < 5; i ++) {
            HomeAddressModel tmpModel = new HomeAddressModel();
            tmpModel.home_type = "My Home";
            tmpModel.address = "Los angels, USA";
            mHomeList.add(tmpModel);
        }
        showData();
    }
    private void showData() {
        layout_home_list.removeAllViews();
        for (int i = 0; i < mHomeList.size(); i ++) {
            LinearLayout layer = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.item_select_home, null);
            TextView txt_title = layer.findViewById(R.id.txt_title);
            TextView txt_detail = layer.findViewById(R.id.txt_detail);
            ImageView img_selected = layer.findViewById(R.id.ico_selected);
            txt_title.setText(mHomeList.get(i).home_type);
            txt_detail.setText(mHomeList.get(i).address);
            layout_home_list.addView(layer);
        }
    }
}
