package com.pckcs.RibbonMenu;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * The Class RibbonTab.
 *
 * @author Paul Conti
 */
public class RibbonTab extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

  /** The groups. */
	List<RibbonGroup> groups;

  /** The title. */
  private String title = null;

	/**
   * The separators.
   *
   * @param title
   *          the title
   */
//	int separators;

	/**
   * Instantiates a new tab.
   *
   * @param title
   *          the title
   */
	public RibbonTab(String title) {
      this.title = title;
      this.groups = new ArrayList<>();
	  this.setLayout(new FlowLayout(FlowLayout.LEFT));
	}

    /**
     * Override updateUI to add custom logic when changing themes if needed
     */
    @Override
    public void updateUI() {
      super.updateUI();
      // some use case better visibility
      this.setBackground(UIManager.getColor("Button.background"));
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
   */
	public void addGroup(RibbonGroup comp) {
		this.add(comp);
	}

  /**
   * paintComponent
   *
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Component[] components = this.getComponents();
    for (Component compo : components) {
        String componentName = compo.getClass().getName();
        if (componentName.endsWith("RibbonGroup")) {
          componentName = ((RibbonGroup)compo).getTitle();
       //   System.out.println(componentName);
        } else {
//        compo.paintComponent(g);
        System.out.println(compo.getClass().getName().substring(componentName.indexOf("swing.") + "swing.".length(),
            componentName.length()));
        }
    }
  }
}
