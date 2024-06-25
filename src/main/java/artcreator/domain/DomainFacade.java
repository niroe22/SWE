package artcreator.domain;

import artcreator.domain.impl.DomainImpl;
import artcreator.domain.port.Domain;
import artcreator.domain.port.Profile;
import artcreator.domain.port.Template;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.StateMachine;

import java.awt.image.BufferedImage;

public class DomainFacade implements DomainFactory, Domain {

    private DomainImpl domain = new DomainImpl();
    private StateMachine stateMachine;

    @Override
    public synchronized Domain domain() {
        if (this.domain == null) {
            this.domain = new DomainImpl();
            this.stateMachine = StateMachineFactory.FACTORY.stateMachine();
        }
        return this;
    }

    @Override
    public synchronized void setImage(BufferedImage image){
        stateMachine.setState(domain.setImage(image));
    }

    @Override
    public synchronized BufferedImage getImage() {
        return domain.getImage();
    }

    @Override
    public synchronized void setTemplate(Template template){
        stateMachine.setState(domain.setTemplate(template));
    }

    @Override
    public synchronized Template getTemplate() {
        return domain.getTemplate();
    }

    @Override
    public synchronized void setProfile(Profile profile){
        stateMachine.setState(domain.updateProfile(profile));
    }

    @Override
    public synchronized Profile getProfile() {
        return domain.updateProfile();
    }
}
