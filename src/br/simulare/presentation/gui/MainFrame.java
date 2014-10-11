package br.simulare.presentation.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import br.simulare.control.appcontroller.AppController;
import br.framesim.util.DBException;
import br.simulare.presentation.gui.action.simulation.NewSimulationFlowAction;
import br.simulare.presentation.gui.dialog.SimulationDialog;
import br.simulare.presentation.gui.tabcontent.chartgeneration.PriceChartTabContent;
import br.simulare.util.MessageManager;

/** 
 *  Main frame. It implements Singleton design pattern.
 *   
 *  @author Alâynne Moreira
 *  @author Rodrigo Paes
 *  @since Version 1.0 
 */

public class MainFrame {
	
	private static MainFrame singletonInstance = null;
	
	private MessageManager msgManager;
	
	private JFrame mainFrameComponent;
	private JMenuBar menuBar;
	private JTabbedPane tabbedPane;
	
	private PriceChartTabContent priceChartTabContent;
	private SimulationDialog simulationDialog;
	
	public static final Dimension MAIN_FRAME_INITIAL_SIZE = new Dimension(1200, 700);

	private MainFrame() {
		init();
	}
	
	private void init() {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		msgManager = MessageManager.getInstance();
		mainFrameComponent = new JFrame(msgManager.getMessage("mainFrame.title"));
		
		createMenu();
		
		priceChartTabContent = new PriceChartTabContent(this);
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab(msgManager.getMessage("mainFrame.priceTab.title"), 
				priceChartTabContent.getTabContentComponent());
		mainFrameComponent.setContentPane(tabbedPane);
		
		mainFrameComponent.setPreferredSize(MAIN_FRAME_INITIAL_SIZE);
		mainFrameComponent.
				setLocation((screenSize.width - MAIN_FRAME_INITIAL_SIZE.width)/2,
						(screenSize.height - MAIN_FRAME_INITIAL_SIZE.height)/2);

		mainFrameComponent.
				setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		mainFrameComponent.pack();
		mainFrameComponent.setVisible(true);

	}

	private void createMenu() {
		
		menuBar = new JMenuBar();
		
		JMenu simulationMenu = new JMenu(msgManager.getMessage("mainFrame.simulationMenu"));
		
		JMenuItem newSimulation = new JMenuItem(new NewSimulationFlowAction(this));

		simulationMenu.add(newSimulation);
	
		menuBar.add(simulationMenu);
		
		mainFrameComponent.setJMenuBar(menuBar);
	
	}

	// It implements Singleton design pattern.
	public static MainFrame getInstance() {
		
		if (singletonInstance == null) {
			singletonInstance = new MainFrame();
		}
		return singletonInstance;
	
	}

	public void showMessageDialog(String title, String msg, int msgType) {
	   JOptionPane.showMessageDialog(mainFrameComponent, msg, title, msgType);
	}
	
	public void showSimulationDialog() {
		
		if (simulationDialog == null) {
			simulationDialog = new SimulationDialog(mainFrameComponent);
		}
		simulationDialog.show();
	
	}

	public static void main(String[] args) {
		
		MessageManager msgManager;
		
		try {
			AppController.getInstance().connectDataBase();
			MainFrame.getInstance();
		} catch (DBException e) {
			msgManager = MessageManager.getInstance();
			JOptionPane.showMessageDialog(null, 
					msgManager.getMessage("error.dbConnection"), 
					msgManager.getMessage("mainFrame.title"), 
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
	}

}
