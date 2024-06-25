package artcreator.creator.impl;

import artcreator.creator.model.Profile;
import artcreator.creator.model.Template;
import artcreator.domain.port.Domain;
import artcreator.statemachine.port.StateMachine;

public class CreatorImpl {

	public CreatorImpl(StateMachine stateMachine, Domain domain) {
		// TODO Auto-generated constructor stub
	}

	public void sysop(String str) {
		System.out.println(str);
		
	}

	public Template setImage(String path) {
		return null;
	}

	public Template setProfile(Profile profile){
		return null;
	}
}
