package artcreator.creator.impl;

import artcreator.domain.port.Profile;
import artcreator.domain.port.Domain;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.StateMachine;

public class CreatorImpl {

	private StateMachine stateMachine;
	private Domain domain;

	public CreatorImpl(StateMachine stateMachine, Domain domain) {
		this.stateMachine = stateMachine;
		this.domain = domain;
	}

	public State setImage(String path) {
		domain.setImage();
	}

	public State updateProfile(Profile profile){
		return null;
	}

}
