package br.simulare.presentation.gui.action;

import javax.swing.AbstractAction;

/**
 * It represents a behavior executed by the graphic interface.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

@SuppressWarnings("serial")
public abstract class Action extends AbstractAction {

	// Graphic interface element which is responsible for the action. 
	protected Object agent;
	
	public Action(String name, Object agent) {
		
		super(name);
		this.agent = agent;
		
	}
	
}
