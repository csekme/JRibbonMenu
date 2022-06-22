package com.pckcs.RibbonMenu;
import java.awt.*;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.event.ChangeListener;
public class RibbonTabbedPane extends JPanel {

  private static final long serialVersionUID = 1L;

  private JTabbedPane tabbedPane;
  private JToolBar    toolBar;

  public RibbonTabbedPane() {
    GridBagLayout gblPanel = new GridBagLayout();
  //  gblPanel.columnWidths = new int[] { 184, 0 };
  //  gblPanel.rowHeights = new int[] { 54, 0 };
 //   gblPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
  //  gblPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
    this.setLayout(new GridBagLayout());
    {
      toolBar = new JToolBar();
      toolBar.setOpaque(false);
      toolBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
      toolBar.setFloatable(false); // The toolbar must not be float

      GridBagConstraints gbcToolBar = new GridBagConstraints();
      gbcToolBar.anchor = GridBagConstraints.NORTHEAST;
      gbcToolBar.gridx = 0;
      gbcToolBar.gridy = 0;
      this.add(toolBar, gbcToolBar);
    }
    {
      tabbedPane = new JTabbedPane(JTabbedPane.TOP);
      GridBagConstraints gbcTabbedPane = new GridBagConstraints();
      gbcTabbedPane.fill = GridBagConstraints.BOTH;
      gbcTabbedPane.gridx = 0;
      gbcTabbedPane.gridy = 0;
      gbcTabbedPane.weightx = 1.0;
      gbcTabbedPane.weighty = 1.0;
      this.add(tabbedPane, gbcTabbedPane);
    }
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

  public void addMouseListener(MouseListener l){
    tabbedPane.addMouseListener(l);
  }

  public int getSelectedIndex() {
    return tabbedPane.getSelectedIndex();
  }

  public void setSelectedIndex(int index) {
    tabbedPane.setSelectedIndex(index);
  }

  public Component getSelectedComponent() {
    return tabbedPane.getTabComponentAt(getSelectedIndex());
  }
}
