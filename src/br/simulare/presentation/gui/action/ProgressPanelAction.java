package br.simulare.presentation.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import br.simulare.presentation.gui.dialog.DialogWithProgressPanel;
import br.simulare.util.MessageManager;

/**
 * Action related to the progress panel of DialogWithProgressPanel class. This action
 * is executed in background.
 * 
 * @author Alâynne Moreira
 * @author Rodrigo Paes
 * @since Version 1.0
 */

@SuppressWarnings("serial")
public abstract class ProgressPanelAction extends Action {

	// It executes the action in background.
	protected SwingWorker<Void, Void> worker;
	
	public ProgressPanelAction(String name, 
			DialogWithProgressPanel dialogWithProgressPanel) {		
		super(name, dialogWithProgressPanel);		
	}
		
	public void actionPerformed(ActionEvent event) {
		
		MessageManager msgManager = MessageManager.getInstance();
		
		if (msgManager.getMessage("cancel").
					equals(event.getActionCommand())) {
			if (worker != null) {
				worker.cancel(true);
			}
		} else {
			if (worker == null) {
				worker = createWorker();
				worker.execute();
			} else if (worker.isCancelled()) {
				worker = createWorker();
				worker.execute();
			} else if (!worker.isDone()) {
				((DialogWithProgressPanel)agent).
						showMessage(msgManager.
								getMessage("alert.concurrentExecution"), 
									JOptionPane.WARNING_MESSAGE);
			} else if (worker.isDone()) {
				worker = createWorker();
				worker.execute();
			}
		}

	}
	
	private SwingWorker<Void, Void> createWorker() {
		
		return new SwingWorker<Void, Void>() {
			
			protected Void doInBackground() throws Exception {
				
				((DialogWithProgressPanel)agent).
						startProgressBar();
				executeAction();
				return null;
			
			}

			public void done() {
				
				((DialogWithProgressPanel)agent).stopProgressBar();
				finishAction();
				
			}

		};
	
	}

	protected abstract void executeAction();
	
	protected abstract void finishAction();

}
