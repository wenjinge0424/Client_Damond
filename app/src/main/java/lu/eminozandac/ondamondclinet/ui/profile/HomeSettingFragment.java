package lu.eminozandac.ondamondclinet.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import lu.eminozandac.ondamondclinet.MainActivity;
import lu.eminozandac.ondamondclinet.R;
import lu.eminozandac.ondamondclinet.model.ContactModel;
import lu.eminozandac.ondamondclinet.model.HomeAddressModel;
import lu.eminozandac.ondamondclinet.model.TrackRequestModel;
import lu.eminozandac.ondamondclinet.model.UserModel;

public class HomeSettingFragment extends Fragment implements View.OnClickListener {

    MainActivity mActivity;

    LinearLayout layout_user_profile;
    LinearLayout layout_my_addresses;
    LinearLayout layout_security_info;
    LinearLayout layout_contacts;

    UserModel currentUser = new UserModel();
    List<HomeAddressModel> mHomeList = new ArrayList<>();
    List<ContactModel> mContactList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_setting, container, false);
        mActivity = MainActivity.instance;

        layout_user_profile = root.findViewById(R.id.layout_user_profile);
        layout_my_addresses = root.findViewById(R.id.layout_my_addresses);
        layout_security_info = root.findViewById(R.id.layout_security_info);
        layout_contacts = root.findViewById(R.id.layout_contacts);
        getServerData();
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                break;
        }
    }
    public void getServerData() {
        currentUser = new UserModel();
        currentUser.first_name = "Emin";
        currentUser.last_name = "ozandac";
        currentUser.phone = "1234567890";
        currentUser.email = "client@test.com";

        mHomeList.clear();
        for (int i = 0; i < 5; i ++) {
            HomeAddressModel tmpModel = new HomeAddressModel();
            tmpModel.home_type = "My Home";
            tmpModel.address = "Los angels, USA";
            mHomeList.add(tmpModel);
        }
        mContactList.clear();
        for (int i = 0; i < 2; i ++) {
            ContactModel tmpModel = new ContactModel();
            tmpModel.name = "Ales gabrella";
            tmpModel.phone = "2021/12/23 16:28";
            tmpModel.email = "brow@gmail.com";
            mContactList.add(tmpModel);
        }
        showData();
    }

    private void showData() {
        layout_user_profile.removeAllViews();
        layout_my_addresses.removeAllViews();
        layout_security_info.removeAllViews();
        layout_contacts.removeAllViews();

        LinearLayout user_layer = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.item_user_contact, null);
        TextView user_txt_title = user_layer.findViewById(R.id.txt_title);
        TextView user_txt_phone = user_layer.findViewById(R.id.txt_phone);
        TextView user_txt_email = user_layer.findViewById(R.id.txt_email);
        user_txt_title.setText(currentUser.first_name + currentUser.last_name);
        user_txt_phone.setText(currentUser.phone);
        user_txt_email.setText(currentUser.email);
        layout_user_profile.addView(user_layer);

        for (int i = 0; i < mHomeList.size(); i ++) {
            LinearLayout layer = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.item_home_address, null);
            TextView txt_title = layer.findViewById(R.id.txt_title);
            TextView txt_detail = layer.findViewById(R.id.txt_detail);
            txt_title.setText(mHomeList.get(i).home_type);
            txt_detail.setText(mHomeList.get(i).address);
            layout_my_addresses.addView(layer);
        }
        LinearLayout add_home_layer = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.item_add_detail, null);
        TextView add_home_title = add_home_layer.findViewById(R.id.txt_title);
        add_home_title.setText("Add address");
        layout_my_addresses.addView(add_home_layer);

        add_home_layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_HOMESETTING_to_ADDHOMEADD);
            }
        });

        settinSecurityInformation();

        for (int i = 0; i < mContactList.size(); i ++) {
            LinearLayout layer = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.item_other_contact, null);
            TextView txt_title = layer.findViewById(R.id.txt_title);
            TextView txt_detail = layer.findViewById(R.id.txt_phone);
            txt_title.setText(mContactList.get(i).name);
            txt_detail.setText(mContactList.get(i).phone);
            layout_contacts.addView(layer);
        }
        LinearLayout add_contact_layer = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.item_add_detail, null);
        TextView add_contact_title = add_contact_layer.findViewById(R.id.txt_title);
        add_contact_title.setText("Add contact");
        layout_contacts.addView(add_contact_layer);

        add_contact_layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_HOMESETTING_to_ADDCONTACT);
            }
        });
    }
    private void settinSecurityInformation(){
        LinearLayout layer_brand = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.item_select_detail, null);
        TextView add_brand_title = layer_brand.findViewById(R.id.txt_title);
        TextView add_brand_detail = layer_brand.findViewById(R.id.txt_detail);
        add_brand_title.setText("Brand of my alarm system");
        add_brand_detail.setText("Protect America");
        layout_security_info.addView(layer_brand);

        LinearLayout layer_numberContact = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.item_select_detail, null);
        TextView add_contact_count_title = layer_numberContact.findViewById(R.id.txt_title);
        TextView add_contact_count_detail = layer_numberContact.findViewById(R.id.txt_detail);
        add_contact_count_title.setText("Number of contact points");
        add_contact_count_detail.setText("1");
        layout_security_info.addView(layer_numberContact);

        LinearLayout layer_video_protect = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.item_select_detail, null);
        TextView add_video_title = layer_video_protect.findViewById(R.id.txt_title);
        Switch add_video_selector = layer_video_protect.findViewById(R.id.switch_selector);
        add_video_title.setText("Video protection system");
        layout_security_info.addView(layer_video_protect);

        LinearLayout layer_gethome = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.item_select_detail, null);
        TextView add_gethome_title = layer_gethome.findViewById(R.id.txt_title);
        TextView add_gethome_detail = layer_gethome.findViewById(R.id.txt_detail);
        add_gethome_title.setText("Get to your house");
        add_gethome_detail.setText("Gate on street");
        layout_security_info.addView(layer_gethome);

        LinearLayout layer_sec_access = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.item_select_detail, null);
        TextView add_sec_access_title = layer_sec_access.findViewById(R.id.txt_title);
        TextView add_sec_access_detail = layer_sec_access.findViewById(R.id.txt_detail);
        add_sec_access_title.setText("Secondary access");
        add_sec_access_detail.setText("A garden");
        layout_security_info.addView(layer_sec_access);

        LinearLayout layer_bar_window = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.item_select_detail, null);
        TextView add_var_window_title = layer_bar_window.findViewById(R.id.txt_title);
        Switch add_var_window_selector = layer_bar_window.findViewById(R.id.switch_selector);
        add_var_window_title.setText("Bars on windows");
        layout_security_info.addView(layer_bar_window);

        LinearLayout layer_hours_day = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.item_select_detail, null);
        TextView add_hours_day_title = layer_hours_day.findViewById(R.id.txt_title);
        TextView add_hours_day_detail = layer_hours_day.findViewById(R.id.txt_detail);
        add_hours_day_title.setText("Hours a day in your house");
        add_hours_day_detail.setText("A garden");
        layout_security_info.addView(layer_hours_day);
    }
}
