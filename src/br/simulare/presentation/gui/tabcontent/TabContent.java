package br.simulare.presentation.gui.tabcontent;

import java.awt.Component;

/**
 * Graphic content of a specific tab.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public abstract class TabContent {
	
	protected Component component;
		
	public Component getTabContentComponent() {
		return component;
	}

	protected abstract Component createTabContentComponent();
	
}
