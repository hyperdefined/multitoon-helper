package space.hyperdefined.multitoonhelper.window;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import space.hyperdefined.multitoonhelper.accountmanager.AccountManager;
import space.hyperdefined.multitoonhelper.accountmanager.AccountManagerMessage;
import space.hyperdefined.multitoonhelper.downloader.Downloader;
import space.hyperdefined.multitoonhelper.reference.Reference;
import space.hyperdefined.multitoonhelper.versionchecker.VersionChecker;
import space.hyperdefined.multitoonhelper.versionchecker.VersionCheckerMessage;

public class MainWindow {

	public static final Logger logger = Logger.getLogger(MainWindow.class.getName());

	public static void addComponentsToPane(Container pane) {
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		JLabel accounts = new JLabel("Accounts (double click)");
		accounts.setAlignmentX(Component.CENTER_ALIGNMENT);
		accounts.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(accounts);
		JList accountList = new JList(AccountManager.labels.toArray());
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) accountList.getCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		accountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		accountList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollBar = new JScrollPane(accountList);
		scrollBar.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(scrollBar);
		accountList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {

					int index = list.getSelectedIndex();
					String username = AccountManager.usernames.get(index);
					String password = AccountManager.passwords.get(index);

					try {
						Process process = Runtime.getRuntime().exec("C:\\Windows\\System32\\cmd.exe /c start \"\" scripts\\login.bat" + " " + username + " " + password);
					} catch(IOException e) {
						e.printStackTrace();
					}
				} else {}
			}
		});
		JLabel programs = new JLabel("Extras");
		programs.setAlignmentX(Component.CENTER_ALIGNMENT);
		programs.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(programs);
		JButton controller = new JButton("Multicontroller");
		controller.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Process process = Runtime.getRuntime().exec("C:\\Windows\\System32\\cmd.exe /c start \"\" programs\\Controller.exe");
				} catch(IOException e1) {
					e1.printStackTrace();
				}
			}

		});
		controller.setAlignmentX(Component.CENTER_ALIGNMENT);
		controller.setMaximumSize(new Dimension(300, controller.getMinimumSize().height));
		pane.add(controller);
	}
	private static void createAndShowGUI() {
		//Create and set up the window.
		JFrame frame = new JFrame(Reference.title + " " + Reference.version);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		//Set up the content pane.
		addComponentsToPane(frame.getContentPane());

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception ex) {

}

		if (AccountManager.programLoads == true) {
			frame.pack();
			frame.setSize(300, 400);
			frame.setVisible(true);
		} else {}
	}

	public static void main(String[] args) throws Exception {
		File logFile = new File("log.txt");
		if (logFile.exists() && logFile.isFile()) {
			logFile.delete();
		}

		FileHandler fh = new FileHandler("log.txt", true);
		SimpleFormatter sf = new SimpleFormatter();
		fh.setFormatter(sf);
		logger.addHandler(fh);

		VersionChecker checker = new VersionChecker();
		checker.run();
		checker.getLatestVersion();
		if (!checker.latestVersion.equals(Reference.version)) {
			checker.isLatestVesion = false;
			VersionCheckerMessage.createFrame();
		}
		AccountManager.getAccountsUsernames();
		AccountManager.getAccountsPasswords();
		AccountManager.getAccountsLabels();
		AccountManagerMessage.createFrame();

		Downloader.doesFileExist();
		if (Downloader.doesFileExist) {
			logger.info("Controller was found. This is not an issue.");
		} else {
			Downloader.downloadFile();
			logger.info("Controller was not found, downloading... This is not an issue.");
		}
		fh.close();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}