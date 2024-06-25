package artcreator.domain.port;


/* Factory for creating domain objects */

import artcreator.statemachine.port.State;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface Domain {

	void setImage(BufferedImage Image);

	BufferedImage getImage();

	void setTemplate(Template template);

	Template getTemplate();

	void setProfile(Profile profile);

	Profile getProfile();
}
