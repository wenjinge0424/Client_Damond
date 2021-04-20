package lu.eminozandac.ondamondclinet.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import lu.eminozandac.ondamondclinet.R;

public class MapDetailFragment extends Fragment implements View.OnClickListener {
    ImageView img_search;
    ImageView img_police;
    Fragment mCurrentFragment;
    int mCurrentFragmentIndex = -1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map_detail, container, false);
        img_search = root.findViewById(R.id.img_search);
        img_police = root.findViewById(R.id.img_police);
        root.findViewById(R.id.layout_search).setOnClickListener(this);
        root.findViewById(R.id.layout_police).setOnClickListener(this);
        root.findViewById(R.id.btn_home).setOnClickListener(this);
        root.findViewById(R.id.btn_create_track_me).setOnClickListener(this);
        SwitchContent(0, null);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_search:
                SwitchContent(0, null);
                break;
            case R.id.layout_police:
                SwitchContent(1, null);
                break;
            case R.id.btn_home:
                RequestActivity.instance.myBack();
                break;
            case R.id.btn_create_track_me:
                Navigation.findNavController(v).navigate(R.id.action_MAPREQUEST_to_CREATETRACK);
                break;
        }
    }

    public void SwitchContent(int fragment_index, Bundle bundle) {
        img_search.setBackgroundResource(R.mipmap.ico_magnofying_deactive);
        img_police.setBackgroundResource(R.mipmap.tab_police_deactive);

        mCurrentFragmentIndex = fragment_index;
        if (mCurrentFragmentIndex == 0) {
            mCurrentFragment = MapSearchFragment.newInstance();
            img_search.setBackgroundResource(R.mipmap.ico_magnofying_active);
        } else if (mCurrentFragmentIndex == 1) {
            mCurrentFragment = MapPoliceFragment.newInstance();
            img_police.setBackgroundResource(R.mipmap.tab_police_active);
        }

        if (mCurrentFragment != null) {
            try {
                if (bundle != null)
                    mCurrentFragment.setArguments(bundle);
                FragmentManager fragmentManager = getChildFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_content, mCurrentFragment).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
