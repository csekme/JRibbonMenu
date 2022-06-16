package com.pckcs.RibbonMenu;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
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
  
  private Color colorBorder;
  private Color colorBackground;

  /** The layout for quickbar. */
  BoxLayout lm;

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
    this.add(Box.createRigidArea(new Dimension(10, 10)));
    QuickBarLayout layout = new QuickBarLayout();
    setLayout(layout);
    addPropertyChangeListener(layout);
    updateUI();
  }
	
  /**
   * Override the updateUI function to follow changes to the theme
   */
  @Override
  public void updateUI() {
    colorBorder = UIManager.getColor("MenuBar.borderColor");  //"MenuBar.foreground"
    colorBackground = UIManager.getColor("Button.background");
  }

	/**
   * addButton.
   *
   * @param button
   *          the button
   */
	public void addButton(JButton button) {
    if (button.getIcon() != null) {
//      FlatSVGIcon icon = (FlatSVGIcon)button.getIcon();
//      button.setIcon(icon.derive(BUTTON_SIZE,BUTTON_SIZE));
      button.setIcon(Util.scaleIcon(button.getIcon(), DisplayState.QUICK));
      button.setBorderPainted(false);
      button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
          button.setBorderPainted(true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
          button.setBorderPainted(false);
        }
      });

    }
    this.add(Box.createRigidArea(new Dimension(2,0)));
    this.add(button);
	}

  /**
   * Add default separator
   * @see RibbonSeparator
   */
  public void addSeparator() {
    this.add(Box.createRigidArea(new Dimension(2,0)));
    RibbonSeparator separator = new RibbonSeparator();
    separator.setForeground(UIManager.getColor("ToolBar.separatorColor"));
    this.add(separator);
  }

  /**
   * Add custom separator
   * @param separator
   * @see RibbonSeparator
   */
  public void addSeparator(RibbonSeparator separator) {
    this.add(Box.createRigidArea(new Dimension(2,0)));
    this.add(separator);
  }
	
	/**
	 * paintChildren
	 *
	 * @see javax.swing.JComponent#paintChildren(java.awt.Graphics)
	 */
	@Override
	public void paintChildren(Graphics g) {

	  
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    double height = (double)RibbonBar.quickbarHeight - 1.0f;
    int minX = 0;
    Dimension size = lm.preferredLayoutSize(this);
    int maxX = size.width;
 
    float radius = (float) height / 2.0f;

    GeneralPath outline = new GeneralPath();

    // top left corner
    outline.moveTo(minX - 1, 0);
    // top right corner
    outline.lineTo(maxX, 0);
    // right arc
    outline.append(new Arc2D.Double(maxX - radius, 0, height,
        height, 90, -180, Arc2D.OPEN), true);
    // bottom left corner
    outline.lineTo(minX - 1, height);
    outline.lineTo(minX - 1, 0);

    Shape contour = outline;
    if (contour != null) {
      g2d.setColor(colorBackground);
      g2d.fill(contour);
      super.paintChildren(g2d);
      g2d.setColor(colorBorder);
      g2d.draw(contour);
    }
    g2d.dispose();
 }
	
  /**
   * The Class QuickBarLayout.
   */
  private class QuickBarLayout implements LayoutManager2, PropertyChangeListener, UIResource {

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

}
