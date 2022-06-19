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
package com.pckcs.RibbonMenu;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.intellijthemes.FlatAllIJThemes;
import com.pckcs.RibbonMenu.Icons.IconDown;
import com.pckcs.RibbonMenu.Icons.IconPinned;
import com.pckcs.RibbonMenu.Icons.IconUp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
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
  QuickAccessBar quickbar;
  /**
   * sample paste button
   */
  JButton btnPaste = null;
  /**
   * The themes.
   */
  public static List<ThemeInfo> themes;
  /**
   * Theme selector
   **/
  JComboBox<ThemeInfo> cbThemeSelector;
  /**
   * Base panel
   **/
  JPanel pnlContent;

  /**
   * Create the frame.
   */
  public MainWindow() {
    this.setTitle("JRibbonMenu Example");
    double version = Double.parseDouble(System.getProperty("java.specification.version"));
    String osName = System.getProperty("os.name").toLowerCase();
    System.out.println("Java ver: " + version + " osys: " + osName);
    loadThemes();
    initGUI();
    initRibbonTabs();
    addEvents();
    SwingUtilities.invokeLater(() -> {
      cbThemeSelector.setSelectedIndex(5);
    });
  }

  /**
   * Initializes the GUI.
   */
  public void initGUI() {
    {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(1000, 600);
      setLayout(new BorderLayout());
    }
    this.quickbar = QuickAccessBar.create();
    initQuickAccessUI();
    this.ribbonBar = RibbonBar.create(this.quickbar);
    add(this.ribbonBar, BorderLayout.NORTH);
    {
      JPanel pnlBase = new JPanel();
      pnlBase.setLayout(new BorderLayout());
      add(pnlBase, BorderLayout.CENTER);
      {
        JPanel pnlTop = new JPanel();
        pnlTop.setLayout(new GridBagLayout());
        pnlBase.add(pnlTop, BorderLayout.NORTH);
        {
          cbThemeSelector = new JComboBox<ThemeInfo>(new Vector<ThemeInfo>(themes));
          //cbThemeSelector.setPrototypeDisplayValue(((ThemeInfo)cbThemeSelector.getSelectedItem()).getShortName());
        //  pnlTop.add(cbThemeSelector, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(4, 4, 4, 4), 0, 0));
        }
      }
      {
        pnlContent = new JPanel();
        pnlContent.setLayout(new GridBagLayout());
        pnlBase.add(pnlContent, BorderLayout.CENTER);
      }

      { // IconUp example
        JButton btnUp = new JButton();
        btnUp.setIcon(new IconUp(24, 24));
        btnUp.addActionListener(new ActionListener() {
          @Override public void actionPerformed(ActionEvent e) {
            RibbonBar.minimizeTabPanel();
          }
        });
        pnlContent.add(btnUp, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
      }
      { // IconDown example
        JButton btnDown = new JButton();
        btnDown.setIcon(new IconDown(24, 24));
        btnDown.addActionListener(new ActionListener() {
          @Override public void actionPerformed(ActionEvent e) {
            RibbonBar.restoreTabPanel();
          }
        });
        pnlContent.add(btnDown, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
      }
      { // IconPinned example
        JButton btnPinned = new JButton();
        btnPinned.setIcon(new IconPinned(24, 24));
        pnlContent.add(btnPinned, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
      }
    }
  } // initGUI

  public void initRibbonTabs() {

    //Create a first tab
    RibbonTab tbHome = new RibbonTab("Home");
    ribbonBar.addTab(tbHome);
    {
      RibbonGroup rgBase = new RibbonGroup("Base");
      tbHome.addGroup(rgBase);
      //Add some button
      {
        JButton btnUser = new JButton("User");

        btnUser.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnUser.setHorizontalTextPosition(SwingConstants.CENTER);
        btnUser.setIcon(new FlatSVGIcon("images/user.svg", 42, 42));
        btnUser.setToolTipText("Add new user");
        btnUser.setActionCommand("new_user");
        btnUser.addActionListener(this);
        rgBase.addComponent(btnUser, DisplayState.LARGE);
      }
      {
        DropDownButton btnSample = new DropDownButton("Warnings");
        btnSample.setIcon(new FlatSVGIcon("images/warning.svg", 42, 42));

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
        rgBase.addComponent(btnSample, DisplayState.LARGE);


      }
      {
        JButton btnSettings = new JButton("<html><body align=\"center\">Settings<br>Configurations</body></html>");
        btnSettings.setIcon(new FlatSVGIcon("images/svg/settings.svg"));

        btnSettings.setToolTipText("Customize your settings.");
        btnSettings.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSettings.setHorizontalTextPosition(SwingConstants.CENTER);

        rgBase.addComponent(btnSettings, DisplayState.LARGE);

      }
      {
        rgBase.addSeparator();
      }
      RibbonGroup rgClipboard = new RibbonGroup("Clipboard");
      tbHome.addGroup(rgClipboard);
      {
        JButton btnCopy = new JButton("Copy");
        btnCopy.setEnabled(true);

        btnCopy.setIcon(Util.accessImageFile("dist/copy.png"));
        btnCopy.setToolTipText("Copy to Clipboard.");
        btnCopy.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnCopy.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCopy.setActionCommand("copy");
        btnCopy.addActionListener(this);
        rgClipboard.addComponent(btnCopy, DisplayState.LARGE);
      }
      {
        btnPaste = new JButton("<html><body align=\"center\">Paste<br>from clipboard</body></html>");
        btnPaste.setIcon(Util.accessImageFile("dist/paste.png"));

        btnPaste.setEnabled(false);
        btnPaste.setToolTipText("Paste from Clipboard");
        btnPaste.setActionCommand("paste");
        btnPaste.addActionListener(this);
        btnPaste.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnPaste.setHorizontalTextPosition(SwingConstants.CENTER);
        rgClipboard.addComponent(btnPaste, DisplayState.LARGE);
      }
      rgClipboard.addSeparator();

      RibbonGroup rgTestControl = new RibbonGroup("Interesting");
      tbHome.addGroup(rgTestControl);
      rgTestControl.addComponent(cbThemeSelector, DisplayState.SLIM);
      cbThemeSelector.setToolTipText("Select your favourite theme");

      JComboBox<String> cmbDevelopers = new JComboBox<>();
      cmbDevelopers.setToolTipText("Please choose a developer.");
      cmbDevelopers.addItem("Developers");
      cmbDevelopers.addItem("Paul Conti");
      cmbDevelopers.addItem("Krisztián Csekme");
      rgTestControl.addComponent(cmbDevelopers, DisplayState.SLIM);
      rgTestControl.addSeparator();

      RibbonGroup rgContacts = new RibbonGroup("Contacts");
      tbHome.addGroup(rgContacts);
      {
        JButton btnLetter = new JButton("Send email");
        btnLetter.setIcon(Util.accessImageFile("dist/letter.png"));
        btnLetter.setToolTipText("Compose and Send Email.");
        rgContacts.addComponent(btnLetter, DisplayState.SLIM);
      }
      /*
      {
        JButton btnFavourites = new JButton("Favourites");
        btnFavourites.setIcon(Util.accessImageFile("dist/kedvencek.png"));
        btnFavourites.setToolTipText("View list of your favourites.");
        rgContacts.addComponent(btnFavourites, DisplayState.SLIM);
      }
      */

      rgContacts.addSeparator();

      RibbonGroup rgTestGroup = new RibbonGroup("Without separator");
      tbHome.addGroup(rgTestGroup);
      {
        JButton btnFavourites = new JButton("Favourites");
        btnFavourites.setIcon(Util.accessImageFile("dist/kedvencek.png"));
        btnFavourites.setToolTipText("View list of your favourites.");
        rgTestGroup.addComponent(btnFavourites, DisplayState.SLIM);
      }

    }
    RibbonTab tbView = new RibbonTab("Security"); //Second tab
    ribbonBar.addTab(tbView);
    RibbonGroup rgClipboard = new RibbonGroup("Clipboard");
    tbView.addGroup(rgClipboard);
    {
      JButton btnReminder = new JButton("Reminder");

      btnReminder.setIcon(Util.accessImageFile("dist/remind.png"));
      btnReminder.setToolTipText("Manage your Reminders.");
      rgClipboard.addComponent(btnReminder, DisplayState.LARGE);
    }
    {
      JButton btnFingerPrint = new JButton("Fingerprint");
      btnFingerPrint.setIcon(Util.accessImageFile("dist/fingerprint.png"));
      btnFingerPrint.setToolTipText("Toggle FingerPrint Security.");
      rgClipboard.addComponent(btnFingerPrint, DisplayState.LARGE);
    }

  } // initRibbonUI

  public void initQuickAccessUI() {
    // Create quick access bar
    {
      //Add some buttons
      {
        JButton qOpen = new JButton();
        qOpen.setIcon(new FlatSVGIcon("images/open.svg"));
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
      quickbar.addSeparator();
      {
        JButton qFavourite = new JButton();
        qFavourite.setIcon(new FlatSVGIcon("images/svg/favourite.svg"));
        qFavourite.setToolTipText("User");
        qFavourite.setActionCommand("user");
        qFavourite.addActionListener(this);
        quickbar.addButton(qFavourite);
      }
      {
        JButton qUser = new JButton();
        qUser.setIcon(new FlatSVGIcon("images/user.svg"));
        qUser.setToolTipText("Favourite");
        qUser.setActionCommand("favourite");
        qUser.addActionListener(this);
        quickbar.addButton(qUser);
      }
      quickbar.addSeparator();
      {
        JButton qExit = new JButton();
        qExit.setIcon(Util.accessImageFile("dist/Stop24.png"));
        qExit.setToolTipText("Exit Application");
        qExit.setActionCommand("exit");
        qExit.addActionListener(this);
        quickbar.addButton(qExit);
      }
    }
    
  } // initQuickAccessUI
  
  public void addEvents() {
    cbThemeSelector.addActionListener(a -> {
      setLookAndFeel((ThemeInfo) cbThemeSelector.getSelectedItem());
    });
  } // addEvents


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
      case "favourite":
        System.out.println("Favourite button pressed");
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


  public void setLookAndFeel(ThemeInfo selectedLaf) {
    try {
      // scan themes for a match
      ThemeInfo themeInfo = null;
      for (ThemeInfo ti : themes) {
        if (ti.name.equals(selectedLaf.name)) {
          themeInfo = ti;
        }
      }
      if (themeInfo != null) {
        if (themeInfo.lafClassName != null) {
          UIManager.setLookAndFeel(themeInfo.lafClassName);
          SwingUtilities.updateComponentTreeUI(this);
          return;
        } else if (themeInfo.themeFile != null) {
          FileInputStream inStream = new FileInputStream(themeInfo.themeFile);
          FlatLaf.setup(IntelliJTheme.createLaf(inStream));
          inStream.close();
          SwingUtilities.updateComponentTreeUI(this);
          return;
        }
      }
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      SwingUtilities.updateComponentTreeUI(this);
    } catch (Exception ex) {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.updateComponentTreeUI(this);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  } // end setLookAndFeel

  /**
   * load themes from templates folder
   */
  public void loadThemes() {
    themes = new ArrayList<ThemeInfo>();

    for (UIManager.LookAndFeelInfo look_and_feel : UIManager.getInstalledLookAndFeels()) {
      JFrame.setDefaultLookAndFeelDecorated(false);
      JDialog.setDefaultLookAndFeelDecorated(false);
      themes.add(new ThemeInfo(look_and_feel.getName(),
              null, look_and_feel.getClassName()));
    }

    themes.add(new ThemeInfo("Flat Light", null, FlatLightLaf.class.getName()));
    themes.add(new ThemeInfo("Flat Dark", null, FlatDarkLaf.class.getName()));
    themes.add(new ThemeInfo("Flat IntelliJ", null, FlatIntelliJLaf.class.getName()));
    themes.add(new ThemeInfo("Flat Darcula", null, FlatDarculaLaf.class.getName()));

    // add intellij themes next
    for (FlatAllIJThemes.FlatIJLookAndFeelInfo info : FlatAllIJThemes.INFOS) {
      themes.add(new ThemeInfo(info.getName(), null, info.getClassName()));
    }
  }

  private class ThemeInfo {
    public final String name;
    public final File themeFile;
    public final String lafClassName;

    public String getShortName() {
      int ch = name.length();
      if (ch>20) {
        return  name.substring(0,20) + "...";
      }
      return name;
    }

    public ThemeInfo(String name, File themeFile, String lafClassName) {
      this.name = name;
      this.themeFile = themeFile;
      this.lafClassName = lafClassName;
    }

    @Override
    public String toString() {
      return getShortName();
    }
  }
}
