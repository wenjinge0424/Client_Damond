package lu.eminozandac.ondamondclinet.model;

public class ParseConstants {
	// parse limit to get object
	public static final int QUERY_FETCH_MAX_COUNT = 1000;

	// table TBL_USER
	public static final String TBL_USER = "User";
	public static final String objectId = "objectId";
	public static final String user_available = "available";
	public static final String user_first_name = "first_name";
	public static final String user_last_name = "last_name";
	public static final String user_phone = "phone";
	public static final String user_username = "username";
	public static final String user_user_name = "user_name";
	public static final String user_email = "email";
	public static final String user_password = "password";
	public static final String user_family = "family";
	public static final String user_height = "height";
	public static final String user_description = "description";
	public static final String user_staff_comment = "staff_comment";
	public static final String user_staff_postal = "staff_postal";
	public static final String user_staff_city = "staff_city";
	public static final String user_staff_birthday = "staff_birthday";
	public static final String user_staff_nationality = "staff_nationality";
	public static final String user_currentLocation = "currentLocation";
	public static final String user_currentLocation_lat = "currentLocation_lat";
	public static final String user_currentLocation_long = "currentLocation_long";

	public static final String TBL_CONTACT = "contacts";
	public static final String contacts_email = "email";
	public static final String contacts_phone = "phone";
	public static final String contacts_name = "name";
	public static final String contacts_owner = "owner";

	public static final String TBL_EQUIPREQUEST = "equip_requestes";
	public static final String equip_main_residence = "main_residence";
	public static final String equip_burglary = "burglary";
	public static final String equip_access = "access";
	public static final String equip_postal = "postal";
	public static final String equip_hours_a_day = "hours_a_day";
	public static final String equip_second_access = "second_access";
	public static final String equip_number_occupant = "number_occupant";
	public static final String equip_location = "location";
	public static final String equip_home_type = "home_type";
	public static final String equip_protection_for = "protection_for";
	public static final String equip_owner = "owner";

	public static final String TBL_HOMEADDRESS = "home_addresses";
	public static final String homeadd_home_type = "home_type";
	public static final String homeadd_address = "address";
	public static final String homeadd_location = "location";
	public static final String homeadd_owner = "owner";
	public static final String homeadd_currentLocation_lat = "currentLocation_lat";
	public static final String homeadd_currentLocation_long = "currentLocation_long";

	public static final String TBL_HOMESETTING = "home_settings";
	public static final String homeset_bar_on_window = "bar_on_window";
	public static final String homeset_get_to_house = "get_to_house";
	public static final String homeset_brand_of_alam = "brand_of_alam";
	public static final String homeset_number_of_contact = "number_of_contact";
	public static final String homeset_instruction = "instruction";
	public static final String homeset_hours_a_day = "hours_a_day";
	public static final String homeset_second_access = "second_access";
	public static final String homeset_video_protection = "video_protection";
	public static final String homeset_owner = "owner";

	public static final String TBL_MARK = "marks";
	public static final String mark_mark = "mark";
	public static final String mark_recevier = "recevier";
	public static final String mark_sender = "sender";
	public static final String mark_request = "request";

	public static final String TBL_MESSAGE = "messages";
	public static final String msg_message = "messages";
	public static final String msg_type = "type";
	public static final String msg_receiver = "receiver";
	public static final String msg_sender = "sender";
	public static final String msg_request = "request";

	public static final String TBL_NOTIFICATION = "notificaitons";
	public static final String noti_type = "type";
	public static final String noti_receiver = "receiver";
	public static final String noti_sender = "sender";
	public static final String noti_request = "request";

	public static final String TBL_TEMP_REQUEST = "temp_request";
	public static final String temp_state = "state";
	public static final String temp_type = "type";
	public static final String temp_location = "location";
	public static final String temp_owner = "owner";
	public static final String temp_accepter = "accepter";
	public static final String temp_officers = "officers";
	public static final String temp_decliner = "decliner";
	public static final String temp_request = "request";

	public static final String TBL_TRACK_REQUEST = "track_requestes";
	public static final String track_track_address = "track_address";
	public static final String track_track_from_address = "track_from_address";
	public static final String track_isComplete = "isComplete";
	public static final String track_start_date = "start_date";
	public static final String track_time = "time";
	public static final String track_type = "type";
	public static final String track_repeat = "repeat";
	public static final String track_tmp_reques_id = "tmp_reques_id";
	public static final String track_track_location = "track_location";
	public static final String track_sender_home = "sender_home";
	public static final String track_receiver_home = "receiver_home";
	public static final String track_address = "address";
	public static final String track_sender = "sender";
	public static final String track_receiver = "receiver";


	public static final String KEY_IS_ONLINE = "isOnline";
	public static final String KEY_OWNER = "owner";
	public static final String KEY_PREVIEW_PASSWORD = "previewPassword";

	public static final String KEY_CREATED_AT = "createdAt";
	public static final String KEY_AVATAR = "avatar";
}
