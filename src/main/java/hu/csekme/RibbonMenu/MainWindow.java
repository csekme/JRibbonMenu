/**
 * Copyright 2020-2022 Csekme Krisztián
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hu.csekme.RibbonMenu;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



/**
 * The Class MainWindow.
 */
public class MainWindow extends JFrame implements ActionListener {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 6524936981221127992L;

    /**
     * The ribbon bar.
     */
    RibbonBar ribbonBar;

    /**
     * sample paste button
     */
    JButton btnPaste = null;

    /**
     * Create the frame.
     */
    public MainWindow() {
        double version = Double.parseDouble(System.getProperty("java.specification.version"));
        String osName = System.getProperty("os.name").toLowerCase();
        System.out.println("Java ver: " + version + " osys: " + osName);
        initGUI();
    }


    /**
     * Initializes the GUI.
     */
    public void initGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        // Create quick access bar
        QuickAccessBar quickbar = QuickAccessBar.create();
        {
            //Add some buttons
            {
                JButton qOpen = new JButton();
                qOpen.setIcon(Util.accessImageFile("dist/Open24.png"));
                qOpen.setToolTipText("Open File");
                qOpen.setActionCommand("open");
                qOpen.addActionListener(this);
                quickbar.addButton(qOpen);
            }
            {
                JButton qSave = new JButton();
                qSave.setIcon(Util.accessImageFile("dist/Save24.png"));
                qSave.setToolTipText("Save File");
                qSave.setActionCommand("save");
                qSave.addActionListener(this);
                quickbar.addButton(qSave);
            }
            {
                JButton qExit = new JButton();
                qExit.setIcon(Util.accessImageFile("dist/Stop24.png"));
                qExit.setToolTipText("Exit Application");
                qExit.setActionCommand("exit");
                qExit.addActionListener(this);
                quickbar.addButton(qExit);
            }
        }
        //Add ribbon bar to NORTH (Suggested)
        // NOTE: quickbar may be null
        this.ribbonBar = RibbonBar.create(getWidth(), quickbar);
//    this.ribbonBar = RibbonBar.create(getWidth(), null);
        getContentPane().add(this.ribbonBar, BorderLayout.NORTH);

        //Create a first tab
        RibbonTab tbHome = new RibbonTab("Home");
        ribbonBar.addTab(tbHome);
        {
//      RibbonGroup rgBase = new RibbonGroup("Base");
            RibbonGroup rgBase = new RibbonGroup();
            tbHome.addGroup(rgBase);
            //Add some button
            {
                JButton btnUser = new JButton("User");
                btnUser.setBorder(null);
                btnUser.setVerticalTextPosition(SwingConstants.BOTTOM);
                btnUser.setHorizontalTextPosition(SwingConstants.CENTER);
                btnUser.setIcon(Util.accessImageFile("dist/user.png"));
                btnUser.setToolTipText("Add new user");
                btnUser.setActionCommand("new_user");
                btnUser.addActionListener(this);
                rgBase.addComponent(btnUser, DisplayState.NORMAL);
            }
            {
                DropDownButton btnSample = new DropDownButton("Warnings");
                btnSample.setIcon(Util.accessImageFile("dist/warning.png"));
                btnSample.setBorder(null);
                btnSample.setToolTipText("Customize your settings.");
                btnSample.setVerticalTextPosition(SwingConstants.BOTTOM);
                btnSample.setHorizontalTextPosition(SwingConstants.CENTER);
                {
                    btnSample.addSubMenu(new JMenuItem("Item 1"));
                    JCheckBoxMenuItem menSel = new JCheckBoxMenuItem();
                    menSel.setText("Item 2");
                    menSel.setSelected(true);
                    btnSample.addSubMenu(menSel);
                    btnSample.addSubMenu(new JMenuItem("Item 3"));
                    btnSample.addSubMenu(new JMenuItem("Item 4"));
                    btnSample.addSubMenu(new JCheckBoxMenuItem("Check menu"));
                    ButtonGroup group = new ButtonGroup();
                    JRadioButtonMenuItem rm1 = new JRadioButtonMenuItem("Option 1");
                    JRadioButtonMenuItem rm2 = new JRadioButtonMenuItem("Option 2");
                    group.add(rm1);
                    group.add(rm2);
                    btnSample.addSeparator();
                    JMenu secondLevelMenu = new JMenu();
                    secondLevelMenu.setText("Second level");
                    secondLevelMenu.add(new JMenuItem("Item 1"));
                    secondLevelMenu.add(new JMenuItem("Item 2"));
                    btnSample.addSubMenu(secondLevelMenu);
                    btnSample.addSeparator();
                    btnSample.addSubMenu(rm1);
                    btnSample.addSubMenu(rm2);
                }
                rgBase.addComponent(btnSample, DisplayState.NORMAL);


            }
            {
                JButton btnSettings = new JButton("<html><body align=\"center\">Settings<br>Configurations</body></html>");
                btnSettings.setBorder(null);
                btnSettings.setIcon(Util.accessImageFile("dist/settings.png"));
                btnSettings.setToolTipText("Customize your settings.");
                btnSettings.setVerticalTextPosition(SwingConstants.BOTTOM);
                btnSettings.setHorizontalTextPosition(SwingConstants.CENTER);

                rgBase.addComponent(btnSettings, DisplayState.NORMAL);


            }
//      RibbonGroup rgClipboard = new RibbonGroup("Clipboard");
            RibbonGroup rgClipboard = new RibbonGroup();
            tbHome.add(rgClipboard);
            {
                JButton btnCopy = new JButton("Copy");
                btnCopy.setEnabled(true);
                btnCopy.setBorder(null);
                btnCopy.setIcon(Util.accessImageFile("dist/copy.png"));
                btnCopy.setToolTipText("Copy to Clipboard.");
                btnCopy.setVerticalTextPosition(SwingConstants.BOTTOM);
                btnCopy.setHorizontalTextPosition(SwingConstants.CENTER);
                btnCopy.setActionCommand("copy");
                btnCopy.addActionListener(this);
                rgClipboard.addComponent(btnCopy, DisplayState.NORMAL);
            }
            {
                btnPaste = new JButton("Paste\nfrom clipboard");
                btnPaste.setIcon(Util.accessImageFile("dist/paste.png"));
                btnPaste.setBorder(null);
                btnPaste.setEnabled(false);
                btnPaste.setToolTipText("Paste from Clipboard");
                btnPaste.setActionCommand("paste");
                btnPaste.addActionListener(this);
                btnPaste.setVerticalTextPosition(SwingConstants.BOTTOM);
                btnPaste.setHorizontalTextPosition(SwingConstants.CENTER);
                rgClipboard.addComponent(btnPaste, DisplayState.NORMAL);
            }
        }
        RibbonTab tbView = new RibbonTab("Security"); //Second tab
        ribbonBar.addTab(tbView);
        RibbonGroup rgClipboard = new RibbonGroup();
        tbView.add(rgClipboard);
        {
            JButton btnReminder = new JButton("Reminder");
            btnReminder.setBorder(null);
            btnReminder.setIcon(Util.accessImageFile("dist/remind.png"));
            btnReminder.setToolTipText("Manage your Reminders.");
            rgClipboard.addComponent(btnReminder, DisplayState.NORMAL);
        }
        {
            JButton btnFingerPrint = new JButton("Fingerprint");
            btnFingerPrint.setIcon(Util.accessImageFile("dist/fingerprint.png"));
            btnFingerPrint.setToolTipText("Toggle FingerPrint Security.");
            rgClipboard.addComponent(btnFingerPrint, DisplayState.NORMAL);
        }

    }


    /**
     * actionPerformed
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand().toLowerCase();
        switch (command) {
            case "copy":
                System.out.println("Copy button pressed");
                btnPaste.setEnabled(true);
                repaint();
                break;
            case "paste":
                System.out.println("Paste button pressed");
                break;
            case "exit":
                System.out.println("Exit Application button pressed");
                System.exit(0);
                break;
            case "new_user":
                System.out.println("New User button pressed");
                break;
            case "open":
                System.out.println("Open File button pressed");
                break;
            case "save":
                System.out.println("Save Application button pressed");
                break;
            default:
                break;
        }
    }

}
