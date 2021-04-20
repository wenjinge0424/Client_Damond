package lu.eminozandac.ondamondclinet.ui.needeuipment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.parse.ParseObject;

import java.util.List;

import lu.eminozandac.ondamondclinet.MainActivity;
import lu.eminozandac.ondamondclinet.R;
import lu.eminozandac.ondamondclinet.listener.ObjectListListener;
import lu.eminozandac.ondamondclinet.model.EquipRequestModel;
import lu.eminozandac.ondamondclinet.ui.dialog.MyProgressDialog;

public class NeedEquipmentFragment extends Fragment {

    public MyProgressDialog dlg_progress;
    MainActivity mActivity;

    ParseObject myEqupmentObject;
    EquipRequestModel showModel = new EquipRequestModel();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_need_equipment, container, false);
        mActivity = MainActivity.instance;
        dlg_progress = new MyProgressDialog(mActivity);
        getServerData();
        return root;
    }
    public void getServerData() {
        dlg_progress.show();
        EquipRequestModel.GetList(new ObjectListListener() {
            @Override
            public void done(List<ParseObject> objects, String error) {
                dlg_progress.cancel();
                if (error == null && objects.size() > 0){
                    myEqupmentObject = objects.get(0);
                    showModel.parse(myEqupmentObject);
                }
            }
        });
    }
    private void showData() {

    }
}
