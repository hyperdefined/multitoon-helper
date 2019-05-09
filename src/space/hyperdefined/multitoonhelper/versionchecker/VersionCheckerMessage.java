package space.hyperdefined.multitoonhelper.versionchecker;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VersionCheckerMessage {
	public static void createFrame() {
		EventQueue.invokeLater(new Runnable() {@Override
			public void run() {
				JFrame frame = new JFrame("Updater");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JPanel pane = new JPanel();
				pane.setLayout(new GridLayout(2, 1));
				frame.add(pane);
				JLabel msg1 = new JLabel("An update for this program is available. Please download the new update here:");
				JLabel msg2 = new JLabel("<html><a href=''>Download from GitHub</a>.</html>");
				pane.add(msg1);
				pane.add(msg2);

				msg2.addMouseListener(new MouseAdapter() {@Override
					public void mouseClicked(MouseEvent e) {
						try {
							URI url = new URI("https://github.com/hypertjs/multitoon-helper/releases/tag/" + VersionChecker.latestVersion);
							Desktop.getDesktop().browse(url);
						} catch(URISyntaxException | IOException ex) {

						}
					}
				});
				frame.setSize(250, 100);
				frame.pack();
				frame.setVisible(true);
				frame.setResizable(false);
			}
		});
	}
}