package lu.eminozandac.ondamondclinet.listener;

import com.parse.ParseObject;

import java.util.List;

public interface ObjectListListener {
	public void done(List<ParseObject> objects, String error);
}
