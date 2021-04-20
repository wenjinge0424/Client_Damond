package lu.eminozandac.ondamondclinet.utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Random;

public class MyMath {
	public static double EPSILON = 0.00000000001;
	
	public static final String UNIT_METER = "meter";
	public static final String UNIT_FEET = "feet";
	
	public static int getRandom(int start, int end) {
		if (start > end) {
			int temp = start;
			start = end;
			end = temp;
		}

		Calendar calendar = Calendar.getInstance();
		int seed = Integer.parseInt(DateTimeUtils.dateToString(calendar.getTime(), "ssmmhh"));
		Random random = new Random(seed);

		return random.nextInt(end - start + 1) + start;
	}
	
	public static double EARTH_RADIUS = 6366198;
	
	/*
	 * Distance between two location
	 */
//	public static double distanceBetween(double lat_a, double lon_a, double lat_b, double lon_b) {
//	    double pk = 180/3.14169;
//
//	    double a1 = lat_a / pk;
//	    double a2 = lon_a / pk;
//	    double b1 = lat_b / pk;
//	    double b2 = lon_b / pk;
//
//	    double t1 = Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2);
//	    double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
//	    double t3 = Math.sin(a1)*Math.sin(b1);
//	    double tt = Math.acos(t1 + t2 + t3);
//
//	    return EARTH_RADIUS * tt;
//	}
	
	public static LatLng move(LatLng startLL, double toNorth, double toEast) {
	    double lonDiff = meterToLongitude(toEast, startLL.latitude);
	    double latDiff = meterToLatitude(toNorth);
	    return new LatLng(startLL.latitude + latDiff, startLL.longitude
	            + lonDiff);
	}
	
	public static double meterToLongitude(double meterToEast, double latitude) {
	    double latArc = Math.toRadians(latitude);
	    double radius = Math.cos(latArc) * EARTH_RADIUS;
	    double rad = meterToEast / radius;
	    return Math.toDegrees(rad);
	}
	
	private static double meterToLatitude(double meterToNorth) {
	    double rad = meterToNorth / EARTH_RADIUS;
	    return Math.toDegrees(rad);
	}
	
	/*
	 * Unit convert
	 */
	public static double MILE = 1609; // 1mile : 1609m
	
	public static double meterToMile(double meter) {
		return (meter/MILE);
	}
	
	public static double kmToMile(double kilo) {
		return (kilo*1000/MILE);
	}
}
