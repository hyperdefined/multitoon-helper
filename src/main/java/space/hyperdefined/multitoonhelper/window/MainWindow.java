package space.hyperdefined.multitoonhelper.window;

import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
import space.hyperdefined.multitoonhelper.downloader.Downloader;

public class MainWindow {

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
                        Runtime.getRuntime().exec("C:\\Windows\\System32\\cmd.exe /c start \"\" scripts\\login.bat" + " " + username + " " + password);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        JLabel programs = new JLabel("Extras");
        programs.setAlignmentX(Component.CENTER_ALIGNMENT);
        programs.setAlignmentX(Component.CENTER_ALIGNMENT);
        pane.add(programs);
        JButton controller = new JButton("Multicontroller");
        controller.addActionListener(e -> {
            try {
                Runtime.getRuntime().exec("C:\\Windows\\System32\\cmd.exe /c start \"\" programs\\Controller.exe");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        controller.setAlignmentX(Component.CENTER_ALIGNMENT);
        controller.setMaximumSize(new Dimension(300, controller.getMinimumSize().height));
        pane.add(controller);

        JButton click = new JButton("Multi-Click");
        click.addActionListener(e -> {
            try {
                Runtime.getRuntime().exec("C:\\Windows\\System32\\cmd.exe /c start \"\" programs\\Multi-Click.exe");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        click.setAlignmentX(Component.CENTER_ALIGNMENT);
        click.setMaximumSize(new Dimension(300, click.getMinimumSize().height));
        pane.add(click);

        JButton toonhq = new JButton("ToonHQ.org");
        toonhq.addActionListener(e -> {
            try {
                Desktop desktop = null;
                if (Desktop.isDesktopSupported()) {
                    desktop = Desktop.getDesktop();
                }
                if (desktop.isSupported(Desktop.Action.BROWSE))
                    desktop.browse(new URI("https://toonhq.org"));
            } catch (URISyntaxException | IOException ex) {
                ex.printStackTrace();
            }
        });
        toonhq.setAlignmentX(Component.CENTER_ALIGNMENT);
        toonhq.setMaximumSize(new Dimension(300, toonhq.getMinimumSize().height));
        pane.add(toonhq);
    }
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Multitoon Helper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        addComponentsToPane(frame.getContentPane());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        frame.pack();
        frame.setSize(300, 400);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        AccountManager.getAccountsUsernames();
        AccountManager.getAccountsPasswords();
        AccountManager.getAccountsLabels();

        Downloader.downloadFile();
        javax.swing.SwingUtilities.invokeLater(MainWindow::createAndShowGUI);

        try {
            Runtime.getRuntime().exec("C:\\Windows\\System32\\cmd.exe /c start \"\" java -jar TTRUpdater.jar");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}