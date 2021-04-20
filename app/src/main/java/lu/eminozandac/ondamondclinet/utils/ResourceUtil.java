package lu.eminozandac.ondamondclinet.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResourceUtil {
	public static String RES_DIRECTORY = Environment.getExternalStorageDirectory() + "/ODC/";
	/*
	 * File
	 */
	public static String getImageFilePath(String fileName) {
		String tempDirPath = RES_DIRECTORY;
		String tempFileName = fileName;

		File tempDir = new File(tempDirPath);
		if (!tempDir.exists())
			tempDir.mkdirs();
		File tempFile = new File(tempDirPath + tempFileName);
		if (!tempFile.exists())
			try {
				tempFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		return tempDirPath + tempFileName;
	}
	public static String getAvatarFilePath() {
		return getImageFilePath("avatar.png");
	}
	public static String getPhotoFilePath() {
		return getImageFilePath("photo.png");
	}

	public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		BitmapFactory.decodeFile(path, options);
		int reqHeight = reqWidth * options.outHeight / options.outWidth;
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (width <= reqWidth && height <= reqHeight)
			return 1;

		float widthRatio = (float)width / reqWidth;
		float heightRatio = (float)height / reqHeight;
		float maxRatio = Math.max(widthRatio, heightRatio);
		inSampleSize = (int)(maxRatio + 0.5);
		return inSampleSize;
	}

	public static Bitmap decodeUri(Context context, Uri selectedImage, int reqSize) throws FileNotFoundException {
		// Decode image size
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(context.getContentResolver().openInputStream(selectedImage), null, options);

		// Find the correct scale value. It should be the power of 2.
		int width_tmp = options.outWidth, height_tmp = options.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp / 2 < reqSize
					|| height_tmp / 2 < reqSize) {
				break;
			}
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}
		// Decode with inSampleSize
		options = new BitmapFactory.Options();
		options.inSampleSize = scale;
		return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(selectedImage), null, options);
	}
	/*
    * Save bitmap on given file path on sdcard
    */
	public static void saveBitmapToSdcard(Bitmap bitmap, String dirPath) {
		File tempFile = new File(dirPath);
		if (tempFile.exists())
			tempFile.delete();

		try {
			FileOutputStream fOut = new FileOutputStream(tempFile);

			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Uri getImageContentUri(Context context, File imageFile) {
		String filePath = imageFile.getAbsolutePath();
		Cursor cursor = context.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Images.Media._ID },
				MediaStore.Images.Media.DATA + "=? ",
				new String[] { filePath }, null);
		if (cursor != null && cursor.moveToFirst()) {
			int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
			cursor.close();
			return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
		} else {
			if (imageFile.exists()) {
				ContentValues values = new ContentValues();
				values.put(MediaStore.Images.Media.DATA, filePath);
				return context.getContentResolver().insert(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			} else {
				return null;
			}
		}
	}
}
