package lu.eminozandac.ondamondclinet.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.app.ActivityCompat;

import java.io.File;

public class CommonUtil {
	/*
	 * Hide keyboard
	 */
	public static void hideKeyboard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public static void showKeyboard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null)
			imm.showSoftInputFromInputMethod(view.getWindowToken(), 0);
	}


	/*
	 * Mail
	 */
	// check validation
	public static boolean isValidEmail(String emailAddr) {
		return !TextUtils.isEmpty(emailAddr) && android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddr).matches();
	}

	// check valid character
	public static int TYPE_LETTER_NUMBER = 0;
	public static int TYPE_LETTER = 1;
	public static boolean isValidCharacter(int type, String value) {
		if (type == TYPE_LETTER_NUMBER)
			return value.matches("[a-zA-Z0-9 ]*");
		else if (type == TYPE_LETTER)
			return value.matches("[a-zA-Z ]*");
		return true;
	}

	// check include number
	public static boolean isIncludeNumber(String value) {
		return value.matches(".*\\d+.*");
	}

	// check include letter
	public static boolean isIncludeLetter(String value) {
		return value.matches(".*[a-zA-Z]+.*");
	}

	// send email
	public static void SendEmail(Context context, String toEmail, String subject, String body, String attachment_url) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		Uri data = Uri.parse("mailto:"
				+ toEmail
				+ "?subject=" + subject + "&body=" + body +"");
		intent.setData(data);
		if (!TextUtils.isEmpty(attachment_url)) {
			Uri photoURI = Uri.fromFile(new File(attachment_url));
//					FileProvider.getUriForFile(context, getApplicationContext().getPackageName() + ".provider", new File(attachment_url));
			intent.putExtra(Intent.EXTRA_STREAM, photoURI);
		}
		context.startActivity(intent);
	}

	/*
	 * SMS
	 */
	public static void SendSMS(Context context, String body) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setType("vnd.android-dir/mms-sms");
		if (!TextUtils.isEmpty(body))
			intent.putExtra("sms_body", body);

		context.startActivity(intent);
	}

	public static void sendSmsInBackground(String phoneNumber, String msg) {
		SmsManager sms = SmsManager.getDefault();
		if (!TextUtils.isEmpty(phoneNumber)) {
			sms.sendTextMessage(phoneNumber, null, msg, null, null);
		}
	}

	public static void CallPhone(Context context, String phoneNumber) {
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:"+phoneNumber));
		context.startActivity(intent);
	}


	// parse file url change
	public static String getCDNUrlFromParseUrl(String path) {
		path = path.replace("https://parse.brainyapps.com:20050", "https://d2zvprcpdficqw.cloudfront.net/process/10050");
		return path;
	}

	// Double To Double
	public static Double D2D(double value) {
		Double result = Double.valueOf((int)(value * 100)) / 100;
		return result;
	}

	public static int TYPE_ALL_PERMISSION = 0;
	public static int TYPE_CAMERA_PERMISSION =1;
	public static int TYPE_LOCATION_PERMISSION =2;
	public static int TYPE_STORAGE_PERMISSION =3;
	public static int TYPE_CALL_PHONE =4;
	public static boolean verifyStoragePermissions(final int type, final Activity activity) {
		// Check if we have write permission
		int permission0 = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
		int permission1 = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA);
		int permission2 = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION);
		int permission3 = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.CALL_PHONE);
		if (type == TYPE_ALL_PERMISSION) {
			if (permission0 != PackageManager.PERMISSION_GRANTED
					|| permission1 != PackageManager.PERMISSION_GRANTED
					|| permission2 != PackageManager.PERMISSION_GRANTED
					|| permission3 != PackageManager.PERMISSION_GRANTED) {
				return false;
			}
		} else if (type == TYPE_CAMERA_PERMISSION) {
			if (permission0 != PackageManager.PERMISSION_GRANTED
					|| permission1 != PackageManager.PERMISSION_GRANTED) {
				return false;
			}
		} else if (type == TYPE_LOCATION_PERMISSION) {
			if (permission2 != PackageManager.PERMISSION_GRANTED) {
				return false;
			}
		} else if (type == TYPE_STORAGE_PERMISSION) {
			if (permission0 != PackageManager.PERMISSION_GRANTED) {
				return false;
			}
		} else if (type == TYPE_CALL_PHONE) {
			if (permission3 != PackageManager.PERMISSION_GRANTED) {
				return false;
			}
		}
		return true;
	}
}
