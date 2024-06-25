package artcreator.domain.impl;

/* Factory for creating domain objects */

import artcreator.domain.port.Profile;
import artcreator.domain.port.Template;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.State.S;

import java.awt.image.BufferedImage;

public class DomainImpl {

	private BufferedImage image;
	private Template template;
	private Profile profile;

	public DomainImpl() {
		image = null;
		template = null;
		profile = null;
	}

	public State setImage(BufferedImage image){
		this.image = image;
		return S.IMAGE_LOADED;
	}

	public BufferedImage getImage() {
        return image;
    }

	public State setTemplate(Template template){
		this.template = template;
		return S.TEMPLATE_CREATED;
	}

	public Template getTemplate() {
		return template;
	}

	public State updateProfile(Profile profile){
		this.profile = profile;
		return S.PROFILE_UPDATE;
	}

	public Profile updateProfile() {
		return profile;
	}
}
