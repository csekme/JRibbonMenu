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

}
