package com.pckcs.RibbonMenu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
public class RibbonGroup extends JComponent {

  /**
   * The Constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  private int nRows = 0;
  private int nColumns;
  private int nStacked = 0;

  GridBagLayout layout;
  
  /**
   * Display state for components.
   */
  List<DisplayState> displayState;

  /**
   * The title.
   */
  private String title = null;

  /** The separators. */
  // int separators;

  /**
   * Instantiates a new group.
   */
  public RibbonGroup() {
    this.displayState = new ArrayList<>();
    layout = new GridBagLayout();
    this.setLayout(layout);
  }

  /**
   * Instantiates a new group.
   *
   * @param title
   *          the title
   */
  public RibbonGroup(String title) {
    this.title = title;
  }

  /**
   * getTitle.
   *
   * @return title
   */
  public String getTitle() {
    return title;
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
    c.weightx = 1;
    c.weighty = 1;
    c.anchor = GridBagConstraints.WEST;

    if (state == DisplayState.SLIM) {
      c.fill = GridBagConstraints.HORIZONTAL;
      switch (nStacked) {
      case 0:
        c.gridx = nRows;
        c.gridy = 0;
        break;
      case 1:
        c.gridx = nRows;
        c.gridy = 1;
        break;
      case 2:
        c.gridx = nRows++;
        c.gridy = 2;
        break;
      default:
        break;
      }
      nStacked++;
      if (nStacked > 2)
        nStacked = 0;
      this.add(comp, c);
      nColumns++;
    } else if (state == DisplayState.SEPARATOR) {
      comp.setPreferredSize(new Dimension(5,RibbonBar.ribbonHeight));
      c.fill = GridBagConstraints.VERTICAL;
      c.gridx = nColumns++;
      c.gridy = 0;
      this.add(comp, c);
    } else {
      c.fill = GridBagConstraints.BOTH;
      c.gridx = nColumns++;
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
  }

  /**
   * Add custom separator
   * 
   * @param separator
   * @see RibbonSeparator
   */
  public void addSeparator(RibbonSeparator separator) {
    addComponent(separator, DisplayState.SEPARATOR);
  }

}
