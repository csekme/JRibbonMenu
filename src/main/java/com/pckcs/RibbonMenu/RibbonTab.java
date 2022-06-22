package com.pckcs.RibbonMenu;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

/**
 * The Class RibbonTab.
 *
 * @author Paul Conti
 */
public class RibbonTab extends JPanel implements Iterable<RibbonGroup> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

  /** The groups. */
	List<RibbonGroup> groups;

  /** The title. */
  private String title = null;

  JPanel container;

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
    this.container = new JPanel();
    this.container.setLayout(new GridBagLayout());
    this.groups = new ArrayList<>();
    this.setLayout(new BorderLayout());
    add(this.container, BorderLayout.WEST);
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
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = this.container.getComponentCount();
    c.gridy = 0;
    c.gridwidth = 1;
    c.gridheight = 2;
    c.weighty = 1;
    c.anchor = GridBagConstraints.CENTER;
    c.insets = new Insets(0, 0, RibbonBar.isDrawShadow() ? RibbonBar.getShadowHeight() : 0, 0);
    c.ipadx = 0;
    c.ipady = 0;
    c.fill = GridBagConstraints.VERTICAL;
    this.groups.add(comp);
    this.container.add(comp, c);
  }

  @Override
  @Deprecated
  public Component add(Component component) {
    throw new RuntimeException("Do not use this method. Use instead addGroup");
  }

  /**
   * Returns an iterator over elements of type {@code T}.
   *
   * @return an Iterator.
   */
  @Override
  public Iterator<RibbonGroup> iterator() {
    return groups.iterator();
  }
}
