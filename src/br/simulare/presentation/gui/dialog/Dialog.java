package br.simulare.presentation.gui.dialog;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Dialog (secondary window) for the control of a specific feature.
 * 
 * @author Alâynne Moreira
 * @since Version 1.0
 */

public abstract class Dialog {

	// Dialog title. 
	protected String title;
	// Graphic component. 
	protected JDialog dialogComponent;
	
	// Dialog size.
	public static final Dimension DIALOG_SIZE = new Dimension(1000, 600);
	
	public Dialog(String title, JFrame parentFrame) {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.title = title;
		dialogComponent = new JDialog(parentFrame, false);
		dialogComponent.setTitle(title);
		dialogComponent.setPreferredSize(DIALOG_SIZE);
		dialogComponent.setLocation((screenSize.width - DIALOG_SIZE.width) / 2,
				(screenSize.height - DIALOG_SIZE.height) / 2);
	
	}
	
	public void show() {
		dialogComponent.setVisible(true);
	}
	
	public void hide() {
		dialogComponent.setVisible(false);
	}
	
	public void showMessage(String msg, int msgType) {
		JOptionPane.showMessageDialog(dialogComponent, msg, title, msgType);
	}
	
}
