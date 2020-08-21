package hu.csekme.RibbonMenu;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

// Copyright 2020 Csekme Krisztián
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Sample application for demonstrate how to use RibbonMenu
 */
public class Sample extends JFrame {

    private JPanel contentPane;
    private RibbonBar ribbonBar;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        try {
            // Select native LAF
            switch (System.getProperty("os.name")) {
                case "Linux":
                    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                    break;
                case "Windows":
                case "Windows 8":
                case "Windows 10":
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    break;
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Sample frame = new Sample();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Constructor
     */
    public Sample() {
        initGUI();
        buildMenu();
    }

    /**
     * Initialize base GUI
     */
    private void initGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 493);
        setTitle("Ribbon menu sample application");
        contentPane = new JPanel();
        contentPane.setAlignmentX(0.0f);
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        {
            ribbonBar = new RibbonBar();
            ribbonBar.setName("ribbonBar");
            contentPane.add(ribbonBar, BorderLayout.NORTH);
        }
    }

    /**
     * build up Menu
     */
    private void buildMenu() {

        // Add our first tab
        Tab tab1 = ribbonBar.addTab("Home");
        tab1.setGroupName("Group one");

        // Add button to first tab
        Button agenda = tab1.addButton("Agenda");
        agenda.setImage(Util.accessImageFile("agenda.png"));

        // create menuitem for button
        RibbonMenuItem r1 = new RibbonMenuItem("Create new date", Util.accessImageFile("newDate.png"));
        agenda.addSubMenu(r1);
        // create some more quickly
        agenda.addSubMenu(new RibbonMenuItem("Clear all"));
        agenda.addSubMenu(new RibbonMenuItem("Reminders"));
        // create selectable
        agenda.addSubMenu(new RibbonMenuItem("Forward to mail", true));

        // add some mini buttons without caption
        Button flag_1 = tab1.addSlimButton("");
        flag_1.setImage(Util.accessImageFile("flag.png"));
        Button flag_2 = tab1.addSlimButton("");
        flag_2.setImage(Util.accessImageFile("flag-1.png"));
        Button flag_3 = tab1.addSlimButton("");
        flag_3.setImage(Util.accessImageFile("hourglass.png"));

        // Add seperator (this means the end of the previous group)
        tab1.addSeperator();
        // Set new group name
        tab1.setGroupName("Group two");

        Button calendar = tab1.addButton("Calendar");
        calendar.setImage(Util.accessImageFile("calendar.png"));

        Button finance = tab1.addSlimButton("Financial pivots");
        finance.setImage(Util.accessImageFile("percent.png"));

        Button settings = tab1.addButton("Settings");
        settings.setImage(Util.accessImageFile("controls.png"));
        tab1.addSeperator();

        Button documents = tab1.addButton("Documents");
        documents.setImage(Util.accessImageFile("folder-9.png"));

        Button help = tab1.addButton("Help");
        help.setImage(Util.accessImageFile("idea.png"));

        Tab dataHandling = ribbonBar.addTab("Data handling");

        Button shares = dataHandling.addButton("Shares");
        shares.setImage(Util.accessImageFile("share-1.png"));
        dataHandling.addSeperator();

        Button filter = dataHandling.addButton("Apply filter");
        filter.setImage(Util.accessImageFile("filter.png"));
        filter.addSubMenu( (event)->{ JOptionPane.showMessageDialog(null, "You pressed");  }, "filter one" );
        filter.addSubMenu( (event)->{ JOptionPane.showMessageDialog(null, "You pressed");  }, "filter two" );

        Tab developing = ribbonBar.addTab("Developing");
        Button server = developing.addButton("Servers");
        server.setImage(Util.accessImageFile("server-1.png"));
        tab1.setSelected(true);

    }



}
