package com.pckcs.RibbonMenu;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Office styled RibbonBar main component.
 *
 * @author Csekme Krisztián
 * 
 * Paul Conti - June 7, 2022 Added QuickAccess Bar 
 * Removed scaling since beginning with java 10 scaling should 
 * work automatically with the graphics context so scaling factor
 * is always 1. If you are still running java 1.8 or 9 upgrade 
 * if you need higher DPI support.
 * Reference:
 * https://stackoverflow.com/questions/50968992/how-do-i-run-a-java-app-with-windows-high-dpi-scaling
 */

public class RibbonBar extends JPanel {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 6524936981221127992L;

	/** The instance. */
	private static RibbonBar instance = null;
	
	/** The size button width. */
	public static int SIZE_BUTTON_WIDTH = 75;
	
	/** The size button height. */
	private static int SIZE_BUTTON_HEIGHT = 75;
	
  /** The quick button image size. */
  public static int QUICKBUTTON_IMAGE_SIZE = 22;
  
	/** The button image size. */
	public static int BUTTON_IMAGE_SIZE = 24;
	
	
	/** The quickbar width. */
	static int quickbarWidth = 0;
	
	/** The quickbar height. */
	static int quickbarHeight = 0;
	
	/** The tab layout west east margin. */
	static int tabLayoutWestEastMargin = 8;
	
	/** The ribbon tab height. */
	static int ribbonTabHeight = 28;
	
	/** The strip height. */
	static int stripHeight = 0;
	
	/** The east west tab inset. */
	static int eastWestTabInset = 20;
	
	/** The north tab inset. */
	static int northTabInset = 0;
	
	/** The button left right margin. */
	static int buttonLeftRightMargin = 4;
	
	/** The ribbon button top base. */
	static int ribbonButtonTopBase = ribbonTabHeight + 4;
	
	/** The button width. */
	static int buttonWidth = SIZE_BUTTON_WIDTH;
	
	/** The button height. */
	static int buttonHeight = SIZE_BUTTON_HEIGHT;
	
	/** The button partial height. */
	static int buttonPartialHeight = 35;
	
	/** The slim button height. */
	static int slimButtonHeight = 25;
	
	/** The separator width. */
	static int separatorWidth = 7;
	
	/** The separator height. */
	static int separatorHeight = 88;
	
	/** The shadow height. */
	static int shadowHeight = 10;
	
	/** The ribbon height. */
	static int ribbonHeight = 145 + shadowHeight;
	
	/** The font. */
	protected Font font = null;

  /** The quickbar. */
  protected static QuickAccessBar QUICKBAR = null;
	
  /** tabbed panel that shows our tabs */
  JTabbedPane ribbonTabPanel;
  
  /** */
  JSplitPane ribbonPane;
  
  /**
   * RibbonBar Factory to create our Singleton Object.
   *
   * @param width the of our container
   * @param quickbar any quickbar defined, my be null
   * @return Ribbonbar instance only one permitted
   */
  public static synchronized RibbonBar create(int width, QuickAccessBar quickbar) {
    if (instance == null) {
      instance = new RibbonBar(width, quickbar);
    }
    return instance;
  }

  /**
   * Constructor.
   * @param width the of our container
   * @param quickbar any quickbar
   */
	public RibbonBar(int width, QuickAccessBar quickbar) {
    QUICKBAR = quickbar;
    ribbonTabPanel = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
    if (quickbar == null) {
      ribbonTabPanel.setMinimumSize(new Dimension(0, ribbonHeight));
      ribbonTabPanel.setPreferredSize(new Dimension(width, ribbonHeight));
      add(ribbonTabPanel, BorderLayout.CENTER);
    } else {
      quickbarHeight = QUICKBUTTON_IMAGE_SIZE + buttonLeftRightMargin;
      ribbonPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
          QUICKBAR,
          ribbonTabPanel);
      ribbonPane.setDividerLocation(quickbarHeight);
      ribbonPane.setMinimumSize(new Dimension(0, quickbarHeight+ribbonHeight));
      ribbonPane.setPreferredSize(new Dimension(width, quickbarHeight+ribbonHeight));
//      ribbonPane.setOneTouchExpandable(true);
      ribbonPane.setDividerLocation(quickbarHeight);
			//ribbonPane.setDividerSize(0);
      add(ribbonPane, BorderLayout.CENTER);
    }
	}

	/**
	 * addTab
	 * @param tab is a top level menu for the top of ribbon.
	 */
  public void addTab(RibbonTab tab) {
    ribbonTabPanel.addTab(tab.getTitle(), tab);
  }
	
  /**
   * paintChildren
   *
   * @see javax.swing.JComponent#paintChildren(java.awt.Graphics)
   */
  @Override
  public void paintChildren(Graphics g) {
    super.paintChildren(g);

    Graphics2D g2d = (Graphics2D) g.create();
    Color colorShadowDark = UIManager.getColor("InternalFrame.borderDarkShadow");
    Color colorShadow = UIManager.getColor("InternalFrame.borderShadow");
    GradientPaint shadow_paint = new GradientPaint(0, getHeight() - shadowHeight,
        colorShadowDark, 0, getHeight(), colorShadow);
    g2d.setPaint(shadow_paint);
    g2d.fill(new Rectangle2D.Double(0, getHeight() - shadowHeight, getWidth(), getHeight()));

    g2d.dispose();

  }
}