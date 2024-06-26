package artcreator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import artcreator.creator.port.Creator;
import artcreator.statemachine.port.Observer;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.Subject;

public class Controller implements ActionListener, Observer {

	  private CreatorFrameOLD myView;
	  private Creator myModel;
	  private Subject subject;


	  public Controller(CreatorFrameOLD view, Subject subject, Creator model) {
	    this.myView = view;
	    this.myModel = model;
	    this.subject = subject;
	    this.subject.attach(this); 
	  }

	  public synchronized void actionPerformed(ActionEvent e) {

	   /* read input */
		String str = (((JButton)  e.getSource()).getText());
		  
//	    CompletableFuture.runAsync(() -> this.myModel.sysop(str));
	  }
	  
	  public void update(State newState) {/* modify controller or view if necessary */}
	}
	
