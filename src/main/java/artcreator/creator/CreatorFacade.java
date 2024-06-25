package artcreator.creator;

import artcreator.creator.impl.CreatorImpl;
import artcreator.domain.port.Profile;
import artcreator.creator.port.Creator;
import artcreator.domain.DomainFactory;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.StateMachine;
import artcreator.statemachine.port.State.S;

public class CreatorFacade implements CreatorFactory, Creator {

	private CreatorImpl creator;
	private StateMachine stateMachine;
	
	@Override
	public Creator creator() {
		if (this.creator == null) {
			this.stateMachine = StateMachineFactory.FACTORY.stateMachine();
			this.creator = new CreatorImpl(stateMachine, DomainFactory.FACTORY.domain());
		}
		return this;
	}

	@Override
	public synchronized void setImage(String path) {
		if (this.stateMachine.getState().isSubStateOf(S.NO_IMAGE_LOADED) ||
				this.stateMachine.getState().isSubStateOf(S.IMAGE_LOADED) ||
				this.stateMachine.getState().isSubStateOf(S.TEMPLATE_CREATED)) {
			creator.setImage(path);
		}
	}

	@Override
	public synchronized void updateProfile(Profile profile){
		if (this.stateMachine.getState().isSubStateOf(S.TEMPLATE_CREATED)) {
			creator.updateProfile(profile);
		}
	}
	

}
