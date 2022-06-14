package com.pckcs.RibbonMenu;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.plaf.UIResource;

/**
 * The Class QuickAccessBar.
 *
 * @author Paul Conti
 */
public class QuickAccessBar extends JPanel {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The instance. */
  private static QuickAccessBar instance = null;
  
	/** The separators. */
//	int separators;
	
  /** The quickbar width. */
  static int quickbarWidth = 0;
  
  /**
   * RibbonBar Factory to create our Singleton Object.
   * 
   * @return Ribbonbar instance only one permitted
   */
  public static synchronized QuickAccessBar create() {
    if (instance == null) {
      instance = new QuickAccessBar();
    }
    return instance;
  }

  /**
   * Constructor.
   */
  public QuickAccessBar() {
//		this.separators = 0;
    QuickBarLayout layout =  new QuickBarLayout();
    setLayout( layout );

    addPropertyChangeListener( layout );

    updateUI();
	}
	
	/**
   * addButton.
   *
   * @param button
   *          the button
   */
	public void addButton(JButton button) {
    this.add(button);
    quickbarWidth += button.getWidth();
	}
	
	/**
   * add seperator.
   */
	public void addSeperator() {
/*
		String gen = RibbonBar.generateToken(8);
		QuickButton button = new QuickButton(gen);
		button.createSeparator();
		buttons.add(button);
		separators++;
*/
	}
	
  /**
   * The Class QuickBarLayout.
   */
  private class QuickBarLayout implements LayoutManager2, PropertyChangeListener, UIResource {

    /** The layout for quickbar. */
    BoxLayout lm;

    /**
     * Instantiates a new quick bar layout.
     */
    QuickBarLayout() {
      lm = new BoxLayout(QuickAccessBar.this, BoxLayout.LINE_AXIS);
    }

    /**
     * addLayoutComponent
     *
     * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
      lm.addLayoutComponent(name, comp);
    }

    /**
     * removeLayoutComponent
     *
     * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
     */
    @Override
    public void removeLayoutComponent(Component comp) {
      lm.removeLayoutComponent(comp);
    }

    /**
     * preferredLayoutSize
     *
     * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
      return lm.preferredLayoutSize(parent);
    }

    /**
     * minimumLayoutSize
     *
     * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
      return lm.minimumLayoutSize(parent);
    }

    /**
     * layoutContainer
     *
     * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
     */
    @Override
    public void layoutContainer(Container parent) {
      lm.layoutContainer(parent);
    }

    /**
     * propertyChange
     *
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      // TODO Auto-generated method stub
      
    }

    /**
     * addLayoutComponent
     *
     * @see java.awt.LayoutManager2#addLayoutComponent(java.awt.Component, java.lang.Object)
     */
    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
      lm.addLayoutComponent(comp, constraints);
    }

    /**
     * maximumLayoutSize
     *
     * @see java.awt.LayoutManager2#maximumLayoutSize(java.awt.Container)
     */
    @Override
    public Dimension maximumLayoutSize(Container target) {
      return lm.maximumLayoutSize(target);
    }

    /**
     * getLayoutAlignmentX
     *
     * @see java.awt.LayoutManager2#getLayoutAlignmentX(java.awt.Container)
     */
    @Override
    public float getLayoutAlignmentX(Container target) {
      return lm.getLayoutAlignmentX(target);
    }

    /**
     * getLayoutAlignmentY
     *
     * @see java.awt.LayoutManager2#getLayoutAlignmentY(java.awt.Container)
     */
    @Override
    public float getLayoutAlignmentY(Container target) {
      return lm.getLayoutAlignmentY(target);
    }

    /**
     * invalidateLayout
     *
     * @see java.awt.LayoutManager2#invalidateLayout(java.awt.Container)
     */
    @Override
    public void invalidateLayout(Container target) {
      lm.invalidateLayout(target);
    }
  }


  /**
   * setLayout
   *
   * @see java.awt.Container#setLayout(java.awt.LayoutManager)
   */
  public void setLayout(LayoutManager mgr) {
    LayoutManager oldMgr = getLayout();
    if (oldMgr instanceof PropertyChangeListener) {
      removePropertyChangeListener((PropertyChangeListener)oldMgr);
    }
    super.setLayout(mgr);
  }

  /**
   * paintComponent
   *
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
}
