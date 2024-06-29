package artcreator.creator.port;

import artcreator.domain.port.Profile;

import java.io.File;

public interface Creator {

	void setImage(File imageFile);

	void updateProfile(Profile profile);

	void createTemplate();
}

