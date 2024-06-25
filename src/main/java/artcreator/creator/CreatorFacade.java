package artcreator.creator;

import artcreator.creator.impl.CreatorImpl;
import artcreator.creator.model.Profile;
import artcreator.creator.model.Template;
import artcreator.creator.port.Creator;
import artcreator.domain.DomainFactory;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.State;
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
	public synchronized void sysop(String str) {
		if (this.stateMachine.getState().isSubStateOf( S.CREATE_TEMPLATE /* choose right state*/ ))
			this.creator.sysop(str);
	}

	@Override
	public Template setImage(String path) {
		if (this.stateMachine.getState().isSubStateOf(S.CREATE_TEMPLATE)||
		this.stateMachine.getState().isSubStateOf(S.NO_PICTURE_LOADED)||
		this.stateMachine.getState().isSubStateOf(S.CAN_CREATE_TEMPLATE)) {
			State s = creator.setImage(path);
		}
		return null;
	}

	@Override
	public synchronized Template setProfile(Profile profile){
		return creator.setProfile(profile);
	}
	

}
