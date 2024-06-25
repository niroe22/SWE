package artcreator.creator.impl;

import artcreator.domain.port.Profile;
import artcreator.domain.port.Domain;
import artcreator.domain.port.Template;
import artcreator.statemachine.port.Observer;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.State.S;
import artcreator.statemachine.port.StateMachine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CreatorImpl implements Observer {

	private final StateMachine stateMachine;
	private final Domain domain;

	public CreatorImpl(StateMachine stateMachine, Domain domain) {
		this.stateMachine = stateMachine;
		this.domain = domain;
	}

	public void setImage(String imagePath) {
		try{
			BufferedImage image = ImageIO.read(new File(imagePath));
			domain.setImage(image);
		} catch (IOException e) {
			System.err.println("Error while reading image " + imagePath);
		}
	}

	public void updateProfile(Profile profile){
		domain.setProfile(profile);
	}

	private void createTemplate(Profile profile, BufferedImage bufferedImage){
//		Template template = new Template();
//
//		domain.setTemplate(template);
	}

	@Override
	public void update(State currentState) {
		if (currentState.isSubStateOf(S.PROFILE_UPDATE) ||
			currentState.isSubStateOf(S.IMAGE_LOADED)){
			this.createTemplate(domain.getProfile(), domain.getImage());
		}
	}
}
