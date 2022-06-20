package com.pckcs.RibbonMenu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * The Class RibbonGroup.
 *
 * @author Paul Conti
 */
public class RibbonGroup extends JPanel {

  /**
   * The Constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  private int nColumns = 0;
  private int nRows    = 0;
  private int nStacked = 0;

  GridBagLayout layout;
//  FlowLayout layout;

  GroupName lblTitle = null;
  
  /**
   * Display state for components.
   */
  List<DisplayState> displayState;

  /**
   * Instantiates a new group.
   */
  public RibbonGroup() {
    this.displayState = new ArrayList<>();
    layout = new GridBagLayout();
//    layout = new FlowLayout();
    this.setLayout(layout);
  }

  /**
   * Instantiates a new group.
   *
   * @param title
   *          the title
   */
  public RibbonGroup(String title) {
    lblTitle = new GroupName(title);
    lblTitle.setHorizontalAlignment(JLabel.CENTER);
    lblTitle.setVerticalTextPosition(JLabel.BOTTOM);
    layout = new GridBagLayout();
//    layout=new FlowLayout();
    this.setLayout(layout);
  }

  /**
   * Override updateUI to add custom logic when changing themes if needed
   */
  @Override
  public void updateUI() {
    super.updateUI();
    // some use case better visibility
    this.setBackground(UIManager.getColor("Button.background"));
    if (lblTitle!=null) {
      lblTitle.setEnabled(false);
    }
  }

  /**
   * addComponent.
   *
   * @param comp
   *          the comp
   * @param state
   *          the state
   */
  public void addComponent(JComponent comp, DisplayState state) {
    String componentName = comp.getClass().getName();
    if (componentName.endsWith("JButton")) {
      componentName = ((JButton) comp).getText();
    }
    if (comp instanceof AbstractButton) {
      AbstractButton ab = (AbstractButton) comp;
      if (ab.getIcon() != null && state != DisplayState.NORMAL) {
        ab.setIcon(Util.scaleIcon(ab.getIcon(), state));
      }
      if (comp instanceof JButton && state != DisplayState.SLIM) {
        JButton jb = (JButton) comp;
        jb.setVerticalTextPosition(SwingConstants.BOTTOM);
        jb.setHorizontalTextPosition(SwingConstants.CENTER);
      }
      ab.setBorderPainted(false);
      comp.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
          ab.setBorderPainted(true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
          ab.setBorderPainted(false);
        }
      });
    }

    GridBagConstraints c = new GridBagConstraints();
    c.weightx = 1.0;
    c.weighty = 1.0;
    c.insets = new Insets(2,2,0,0);
    c.anchor = GridBagConstraints.WEST;

    if (state == DisplayState.SLIM) {
      c.weighty = 0.0;
      c.fill = GridBagConstraints.HORIZONTAL;
      switch (nStacked) {
      case 0:
        c.gridx = nColumns;
        c.gridy = 0;
        break;
      case 1:
        c.gridx = nColumns;
        c.gridy = 1;
        break;
      case 2:
        c.gridx = nColumns++;
        c.gridy = 2;
        break;
      default:
        break;
      }
      nStacked++;
      if (nStacked > 2)
        nStacked = 0;
      this.add(comp, c);
      nRows++;
    } else if (state == DisplayState.SEPARATOR) {
      if (comp instanceof RibbonSeparator) {
        RibbonSeparator sep = (RibbonSeparator) comp;
        sep.setPreferredSize(new Dimension(sep.getWidth(),RibbonBar.ribbonHeight));
      } else {
        throw new RuntimeException("Please use RibbonSeparator component for state of SEPARATOR");
      }
      c.fill = GridBagConstraints.VERTICAL;
      c.gridheight = 4;
      c.gridx = nRows++;
      c.gridy = 0;
      this.add(comp, c);
    } else {
      c.fill = GridBagConstraints.BOTH;
      c.gridx = nRows++;
      c.gridy = 0;
      this.add(comp, c);
    }

  }

  /**
   * Add default separator
   * 
   * @see RibbonSeparator
   */
  public void addSeparator() {
    addComponent(new RibbonSeparator(), DisplayState.SEPARATOR);

    if (lblTitle != null) {
      GridBagConstraints c = new GridBagConstraints();
      c.weightx = 1;
      c.weighty = 1;
      c.anchor = GridBagConstraints.SOUTH;
//      c.fill = GridBagConstraints.BOTH;
      c.gridwidth = nColumns;
      c.gridx = 0;
      c.gridy = 3;
      this.add(lblTitle, c);
    }

  }

}
