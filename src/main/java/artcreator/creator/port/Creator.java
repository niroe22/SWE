package artcreator.creator.port;

import artcreator.domain.port.Profile;

public interface Creator {

	void setImage(String path);

	void updateProfile(Profile profile);
}

