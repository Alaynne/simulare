package br.simulare.presentation.gui.dialog;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import br.simulare.util.MessageManager;

/**
 * Dialog (secondary window) which has a progress panel for the control of a specific 
 * feature.
 *  
 * @author Alâynne Moreira
 * @author Rodrigo Paes
 * @since Version 1.0
 */

public abstract class DialogWithProgressPanel extends Dialog {

	protected JProgressBar progressBar;

	protected JButton okButton;

	protected JButton cancelButton;
	
	public DialogWithProgressPanel(String title, JFrame parentFrame) {
		super(title, parentFrame);
	}
	
	// It creates the progress panel of the dialog.
	protected JPanel createProgressPanel() {
		
		MessageManager msgManager = MessageManager.getInstance();
		JPanel progressBarPanel = new JPanel(new BorderLayout());
		JPanel internalPanel1 = new JPanel();
		JPanel internalPanel2 = new JPanel();
		
		okButton = new JButton(msgManager.getMessage("ok"));
		cancelButton = new JButton(msgManager.getMessage("cancel"));
				
		internalPanel1.add(okButton);
		internalPanel1.add(cancelButton);
		
		JLabel statusLabel = new JLabel(msgManager.getMessage("status"));
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		
		internalPanel2.add(statusLabel);
		internalPanel2.add(progressBar);
		
		progressBarPanel.add(internalPanel1, BorderLayout.NORTH);
		progressBarPanel.add(internalPanel2, BorderLayout.SOUTH);		

		return progressBarPanel;
		
	}
	
	//It moves the progress bar of the dialog.
	public void startProgressBar() {
		progressBar.setIndeterminate(true);
	}
	
	// It stops the progress bar of the dialog.
	public void stopProgressBar() {
		progressBar.setIndeterminate(false);
	}
	
}
