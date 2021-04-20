package lu.eminozandac.ondamondclinet.ui.requestpatrol;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import lu.eminozandac.ondamondclinet.R;

public class CreateRPTimeFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_rptime, container, false);
        root.findViewById(R.id.btn_confirm).setOnClickListener(this);
        return root;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                Navigation.findNavController(v).navigate(R.id.action_RPCT_to_RPPS);
                break;
        }
    }
}
