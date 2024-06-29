package artcreator.creator;

import artcreator.creator.impl.CreatorImpl;
import artcreator.domain.port.Profile;
import artcreator.creator.port.Creator;
import artcreator.domain.DomainFactory;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.StateMachine;
import artcreator.statemachine.port.State.S;

import java.io.File;
import java.time.LocalDateTime;

public class CreatorFacade implements CreatorFactory, Creator {

	private CreatorImpl creator;
	private StateMachine stateMachine;
	
	@Override
	public Creator creator() {
		if (this.creator == null) {
			this.stateMachine = StateMachineFactory.FACTORY.stateMachine();
			this.creator = new CreatorImpl(DomainFactory.FACTORY.domain());
		}
		return this;
	}

	@Override
	public synchronized void setImage(File imageFile) {
		if (this.stateMachine.getState().isSubStateOf(S.NO_IMAGE_LOADED) ||
				this.stateMachine.getState().isSubStateOf(S.IMAGE_LOADED) ||
				this.stateMachine.getState().isSubStateOf(S.TEMPLATE_CREATED)) {
			creator.setImage(imageFile);
		} else {
			System.err.println(LocalDateTime.now() + " - SetImage is not allowed");
		}
	}

	@Override
	public synchronized void updateProfile(Profile profile){
		if (this.stateMachine.getState().isSubStateOf(S.TEMPLATE_CREATED) ||
				this.stateMachine.getState().isSubStateOf(S.PROFILE_UPDATED)) {
			creator.updateProfile(profile);
		} else {
			System.err.println(LocalDateTime.now() + " - UpdateProfile is not allowed");
		}
	}

	public synchronized void createTemplate(){
		if (this.stateMachine.getState().isSubStateOf(S.IMAGE_LOADED) ||
				this.stateMachine.getState().isSubStateOf(S.TEMPLATE_CREATED) ||
				this.stateMachine.getState().isSubStateOf(S.PROFILE_UPDATED)){
			creator.createTemplate();
		}
	}

}
