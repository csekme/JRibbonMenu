package com.pckcs.RibbonMenu;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Office styled RibbonBar main component.
 *
 * @author Csekme Krisztián
 * <p>
 * Paul Conti - June 7, 2022 Added QuickAccess Bar
 * Removed scaling since beginning with java 10 scaling should
 * work automatically with the graphics context so scaling factor
 * is always 1. If you are still running java 1.8 or 9 upgrade
 * if you need higher DPI support.
 * Reference:
 * https://stackoverflow.com/questions/50968992/how-do-i-run-a-java-app-with-windows-high-dpi-scaling
 */

public class RibbonBar extends JPanel {

  /**
   * The Constant serialVersionUID.
   */
  private static final long serialVersionUID = 6524936981221127992L;

  /**
   * The instance.
   */
  private static RibbonBar instance = null;

  /**
   * The slim button size.
   */
  public static int SLIMBUTTON_IMAGE_SIZE = 16;

  /**
   * The quick button image size.
   */
  public static int QUICKBUTTON_IMAGE_SIZE = 22;

  /**
   * The button image size.
   */
  public static int BUTTON_IMAGE_SIZE = 24;

  /**
   * The button image size.
   */
  public static int LARGEBUTTON_IMAGE_SIZE = 42;

  /**
   * The ribbon tab height.
   */
  static int ribbonTabHeight = 29;

  /**
   * The shadow height.
   */
  static int shadowHeight = 8;

  /**
   * The ribbon height.
   */
  static int ribbonHeight = 145 + shadowHeight;

  /**
   * The quickbar height.
   */
  static int quickbarHeight = 36;

  /**
   * The quickbar.
   */
  static QuickAccessBar QUICKBAR = null;

  /**
   * tabbed panel that shows our tabs
   */
  static JTabbedPane ribbonTabPanel;

  /** our size of the tab pane */
  static Dimension ribbonDimension;
  
  // our ribbonTabPanel size when minimized */
  static Dimension minimizedDim;
  
  /** The split ribbonPane */
  static JSplitPane ribbonPane = null;

  /** The minimized flag. */
  static boolean bMinimized = false;
  
  /** list of tabs - needed for when we rebuild tab panel after minimize */
  static List<RibbonTab> tabs = new ArrayList<RibbonTab>();
  
  /**
   * RibbonBar Factory to create our Singleton Object.
   *
   * @param width    the of our container
   * @param quickbar any quickbar defined, my be null
   * @return Ribbonbar instance only one permitted
   */
  public static synchronized RibbonBar create(QuickAccessBar quickbar) {
    if (instance == null) {
      instance = new RibbonBar(quickbar);
    }
    return instance;
  }

  /**
   * Constructor.
   * Used when no quickbar is needed
   */
  public RibbonBar() {
    this(null);
  }
  
  /**
   * Constructor.
   *
   * @param quickbar any quickbar
   */
  public RibbonBar(QuickAccessBar quickbar) {
    setLayout(new BorderLayout());
    QUICKBAR = quickbar;
    ribbonTabPanel = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
    ribbonTabPanel.addChangeListener(a->{ repaint(); });
    minimizedDim = new Dimension(getWidth(), ribbonTabHeight+shadowHeight);

    if (quickbar == null) {
      add(ribbonTabPanel, BorderLayout.CENTER);
    } else {
      ribbonPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
              QUICKBAR,
              ribbonTabPanel);
      ribbonPane.setDividerLocation(quickbarHeight);
      ribbonPane.setDividerSize(0);
      add(ribbonPane, BorderLayout.CENTER);
    }
  }

  /**
   * addTab
   *
   * @param tab is a top level menu for the top of ribbon.
   */
  public void addTab(RibbonTab tab) {
    ribbonTabPanel.addTab(tab.getTitle(), tab);
    tabs.add(tab);
  }

  private static void rebuildTabPanel() {
    ribbonTabPanel = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
    ribbonTabPanel.setPreferredSize(ribbonDimension);
    for (RibbonTab tab : tabs) {
      ribbonTabPanel.addTab(tab.getTitle(), tab);
    }
    ribbonTabPanel.addChangeListener(a->{ instance.repaint(); });
  }
  
  /**
   * Minimize our ribbon until just the quickbar and tabs show.
   */
  public static void minimizeTabPanel() 
  {
    if(!bMinimized)
    {
      if (ribbonPane != null) {
        ribbonDimension = ribbonTabPanel.getSize();
        ribbonTabPanel.setPreferredSize(minimizedDim);
        ribbonPane.setDividerSize(0);
        ribbonPane.resetToPreferredSizes();
      } else {
        ribbonDimension = ribbonTabPanel.getSize();
        ribbonTabPanel.setPreferredSize(minimizedDim);
      }
      bMinimized = true;
      SwingUtilities.updateComponentTreeUI(instance);
    }
  }
  
  /**
   * Restores our Tab Panel to full size
   */
  public static void restoreTabPanel() 
  {
    if(bMinimized)
    {
      if (ribbonPane != null) {
        rebuildTabPanel();
        ribbonPane.setBottomComponent(ribbonTabPanel);
        ribbonPane.setDividerSize(0);
        ribbonPane.resetToPreferredSizes();
      } else {
        ribbonTabPanel.setPreferredSize(ribbonDimension);
      }
      bMinimized = false;
      SwingUtilities.updateComponentTreeUI(instance);
    }
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
    if (colorShadowDark != null && colorShadow != null) {
      GradientPaint shadow_paint = new GradientPaint(0, getHeight() - shadowHeight,
              colorShadowDark, 0, getHeight(), colorShadow);
      g2d.setPaint(shadow_paint);
      g2d.fill(new Rectangle2D.Double(0, getHeight() - shadowHeight, getWidth(), getHeight()));
    }
    g2d.dispose();
  }

}

