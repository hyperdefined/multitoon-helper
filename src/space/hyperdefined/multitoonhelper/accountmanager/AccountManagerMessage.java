package space.hyperdefined.multitoonhelper.accountmanager;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AccountManagerMessage {
	public static void createFrame() {
		EventQueue.invokeLater(new Runnable() {@Override
			public void run() {
				JFrame frame = new JFrame("Error");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JPanel pane = new JPanel();
				pane.setLayout(new GridLayout(2, 1));
				frame.add(pane);
				JLabel msg1 = new JLabel("Uh oh! Looks like we can't find accounts.json. Make sure it is located in the 'config' folder.");
				JLabel msg2 = new JLabel("Uh oh! There seems to be a problem with your accounts.json file. Double check that it is correct.");
				frame.setSize(250, 100);
				frame.setResizable(false);
				if (AccountManager.passwords.isEmpty() && AccountManager.usernames.isEmpty()) {
					if (AccountManager.fileNotFound) {
						frame.add(msg1);
						frame.pack();
						frame.setVisible(true);
					}
					if (AccountManager.jsonError) {
						frame.add(msg2);
						frame.pack();
						frame.setVisible(true);
					}
				}
			}
		});
	}
}