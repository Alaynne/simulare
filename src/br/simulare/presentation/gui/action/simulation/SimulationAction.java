package br.simulare.presentation.gui.action.simulation;

import java.util.Date;
import java.util.Hashtable;
import javax.swing.JOptionPane;

import br.simulare.business.simulator.MySimulationResult;
import br.simulare.control.appcontroller.AppController;
import br.framesim.util.DBException;
import br.simulare.util.InvalidDataException;
import br.simulare.presentation.gui.action.ProgressPanelAction;
import br.simulare.presentation.gui.dialog.SimulationDialog;
import br.simulare.util.MessageManager;

/**
 * Action for the execution of a new simulation. It is related to the progress panel of
 * SimulationDialog class. It is executed in background, since it extends  
 * ProgressPanelAction class. 
 * 
 * @author Alâynne Moreira
 * @author Rodrigo Paes
 * @since Version 1.0
 */

@SuppressWarnings("serial")
public class SimulationAction extends ProgressPanelAction {

	private MySimulationResult mySimulationResult;

	public SimulationAction(SimulationDialog simulationDialog) {
		
		super(MessageManager.getInstance().getMessage("simulate"), 
				simulationDialog);

	}

	protected void executeAction() {
		
		MessageManager msgManager = MessageManager.getInstance();
		SimulationDialog myAgent = ((SimulationDialog)agent);
		
		Date startingDate = myAgent.getStartingDate();
		Date endingDate = myAgent.getEndingDate();
		String[] stockCodes = myAgent.getStockCodes();
		String initialInvestment = myAgent.getInitialInvestment();
		String longPositionSizePercentage = myAgent.getLongPositionSizePercentage();
		String periodicity = myAgent.getPeriodicity();
		String tradingPriceType = myAgent.getTradingPriceType();
		String tradingFeeType = myAgent.getTradingFeeType();
		String tradingFee = myAgent.getTradingFee();
		Hashtable<String, String> tradingFees = new Hashtable<String, String>();
		tradingFees.put(tradingFeeType, tradingFee);
		Hashtable<Integer, Hashtable<Integer, String[]>> taMethods = 
				myAgent.getTAMethods();
		
		try {
			mySimulationResult = AppController.getInstance().
				simulate(startingDate, endingDate, stockCodes, initialInvestment, 
					longPositionSizePercentage, tradingFees, taMethods,
					periodicity, tradingPriceType);
		} catch (InvalidDataException e) {
			myAgent.showMessage(msgManager.
					getMessage("error.invalidData.simulation") + '\n' + 
							e.getMessage(), JOptionPane.ERROR_MESSAGE);
			mySimulationResult = null;
		} catch (DBException e) {
			myAgent.showMessage(msgManager.
					getMessage("error.unexpectedEvent.simulation"), 
							JOptionPane.ERROR_MESSAGE);
			mySimulationResult = null;
		}
	
	}
	
	protected void finishAction() {
		
		if (mySimulationResult != null) {
			((SimulationDialog)agent).
					showSimulationResult(mySimulationResult);
		}
	
	}

}
