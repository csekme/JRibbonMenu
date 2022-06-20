package com.pckcs.RibbonMenu;

import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.event.ChangeListener;

import com.pckcs.RibbonMenu.Icons.IconDown;
import com.pckcs.RibbonMenu.Icons.IconUp;

public class RibbonTabbedPane extends JPanel {

  private static final long serialVersionUID = 1L;

  private JTabbedPane tabbedPane;
  private JToolBar    toolBar;

  public RibbonTabbedPane() {
    GridBagLayout gbl_panel = new GridBagLayout();
    gbl_panel.columnWidths = new int[] { 184, 0 };
    gbl_panel.rowHeights = new int[] { 54, 0 };
    gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
    gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
    this.setLayout(gbl_panel);

    toolBar = new JToolBar();
    toolBar.setOpaque(false);
    toolBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    toolBar.setFloatable(false); // The toolbar must not be float

    GridBagConstraints gbc_toolBar = new GridBagConstraints();
    gbc_toolBar.anchor = GridBagConstraints.NORTHEAST;
    gbc_toolBar.gridx = 0;
    gbc_toolBar.gridy = 0;
    this.add(toolBar, gbc_toolBar);

    tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
    gbc_tabbedPane.fill = GridBagConstraints.BOTH;
    gbc_tabbedPane.gridx = 0;
    gbc_tabbedPane.gridy = 0;
    this.add(tabbedPane, gbc_tabbedPane);

  }
  
  public void addTab(RibbonTab tab) {
    tabbedPane.addTab(tab.getTitle(), tab);
  }
  
  public void addButton(JButton button) {
    toolBar.add(button);
  }
  
  /**
   * Adds a ChangeListener to this tabbedpane.
   *
   * @param l the ChangeListener to add
   */
  public void addChangeListener(ChangeListener l) {
    tabbedPane.addChangeListener(l);
  }

}
