package artcreator.creator.port;

import artcreator.creator.model.Profile;
import artcreator.creator.model.Template;

public interface Creator {
	
	void sysop(String str);

	Template setImage(String path);

	Template setProfile(Profile profile);

}

