package lu.eminozandac.ondamondclinet.listener;

import com.parse.ParseObject;

public interface ObjectListener {
	public void done(ParseObject object, String error);
}
