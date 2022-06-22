package com.pckcs.RibbonMenu;

import com.pckcs.RibbonMenu.Icons.IconDown;
import com.pckcs.RibbonMenu.Icons.IconUp;
import com.pckcs.RibbonMenu.annotations.FeaturePreview;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Office style ribbon menu. Which is a three levels of menu system.
 * The top level is tabs, followed by the set of controls for that.
 * Optional third level can be drop-down button.
 * The component implements a single design pattern.
 *
 * @author Krisztián Csekme and Paul Conti
 *
 * <p>
 * Paul Conti - June 7, 2022 Added QuickAccess Bar
 * Removed scaling since beginning with java 10 scaling should
 * work automatically with the graphics context so scaling factor
 * is always 1. If you are still running java 1.8 or 9 upgrade
 * if you need higher DPI support.
 * Reference:
 * https://stackoverflow.com/questions/50968992/how-do-i-run-a-java-app-with-windows-high-dpi-scaling
 * </p>
 * @see RibbonGroup
 * @see QuickAccessBar
 * @see RibbonTab
 * @see RibbonSeparator
 * @see DropDownButton
 */

public final class RibbonBar extends JPanel implements MouseListener, ActionListener, Iterable<RibbonTab> {

  // The Constant serialVersionUID.
  private static final long serialVersionUID = 6524936981221127992L;

  // The instance.
  private static RibbonBar instance = null;

  // The ribbon tab height.
  private static int ribbonTabHeight = RibbonBarConstants.DEFAULT_TABBED_PANE_TAB_HEIGHT;


  // The shadow height.
  private static int shadowHeight = RibbonBarConstants.DEFAULT_SHADOW_HEIGHT;

  // The quickbar height.
  private static int quickBarHeight = RibbonBarConstants.DEFAULT_QUICK_BAR_HEIGHT;

  // The quickbar.
  private static QuickAccessBar quickBar = null;

  // tabbed panel that shows our tabs
  private static RibbonTabbedPane ribbonTabPanel;

  // our size of the tab pane
  private static Dimension ribbonDimension;

  // our ribbonTabPanel size when minimized
  private static Dimension minimizedDim;

  // The split ribbonPane
  private static JSplitPane ribbonPane = null;

  // The minimized flag.
  private static boolean bMinimized = false;

  // helper boolean for minimization
  private static boolean bNeedToMin = false;

  // reference of the minimize button
  private static JButton btnMinimize;

  // reference of the maximize button
  private static JButton btnMaximize;

  // list of tabs
  private static final List<RibbonTab> tabs = new ArrayList<>();

  // draw shadow
  private static boolean drawShadow = true;

  /**
   * RibbonBar Factory to create our Singleton Object.
   *
   * @param quickBar any quickbar defined, my be null
   * @return Ribbonbar instance only one permitted
   */
  public static synchronized RibbonBar create(QuickAccessBar quickBar) {
    if (instance == null) {
      instance = new RibbonBar(quickBar);
    }
    return instance;
  }

  /**
   * RibbonBar Factory to create our Singleton Object.
   *
   * @return Ribbonbar instance only one permitted
   */
  public static synchronized RibbonBar create() {
    if (instance == null) {
      instance = create(null);
    }
    return instance;
  }

  /**
   * Get RibbonBar instance, if is null return with a new instance
   *
   * @return instance of RibbonBar
   */
  public static RibbonBar getInstance() {
    return create();
  }

  /**
   * Constructor is hidden according to singleton pattern.
   */
  private RibbonBar() {
    this(null);
  }

  /**
   * Constructor is hidden according to singleton pattern.
   * ribbonTabPanel gets listeners like MouseListener and ChangeListener
   *
   * @param quickBar any quickbar
   */
  private RibbonBar(QuickAccessBar quickBar) {
    setLayout(new BorderLayout());

    RibbonBar.quickBar = quickBar;
    ribbonTabPanel = new RibbonTabbedPane();
    ribbonTabPanel.addChangeListener(a -> repaint());
    addDefaultButtons();
    determineTabHeight();

    if (quickBar == null) {
      add(ribbonTabPanel, BorderLayout.CENTER);
    } else {
      ribbonPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, RibbonBar.quickBar, ribbonTabPanel);
      ribbonPane.setDividerLocation(quickBarHeight + 2); // TODO: +2 is necessary for some reason (to be investigated)
      ribbonPane.setDividerSize(0);
      add(ribbonPane, BorderLayout.CENTER);
    }
    ribbonTabPanel.addMouseListener(this);
  }

  /**
   * Under development. Do not use in production environment.
   * If you want to enable this feature preview then please enable in Util.enableFeatures();
   */
  @FeaturePreview
  public static void shrinkToSimpleView() {
    if (!Util.isFeaturesEnabled())
      return;
    getInstance().forEach(tab -> {
      tab.forEach(group -> {
        group.forEach(component -> {
          if (component instanceof AbstractButton) {
            AbstractButton btn = ((AbstractButton) component);
            if (btn.getIcon() != null) {
              btn.setIcon(Util.scaleIcon(btn.getIcon(), DisplayState.MEDIUM));
              btn.setToolTipText(btn.getText());
              btn.setText(null);
              btn.revalidate();
            }
          } else {
            component.setVisible(false);
          }
          group.getTitle().setVisible(false);
          group.revalidate();

        });
        tab.revalidate();
      });
      getInstance().setPreferredSize(new Dimension(getInstance().getWidth(), 120));
      SwingUtilities.updateComponentTreeUI(getInstance());
    });
  }

  /**
   * Determine tab height based on shadow
   */
  private static void determineTabHeight() {
    try {
      ribbonTabHeight = UIManager.getInt("TabbedPane.tabHeight");
      minimizedDim = new Dimension(instance == null ? 0 : instance.getWidth(), ribbonTabHeight + (drawShadow ? shadowHeight : 0));
    } catch (NullPointerException ex) {
      ribbonTabHeight = RibbonBarConstants.DEFAULT_TABBED_PANE_TAB_HEIGHT;
      minimizedDim = new Dimension(instance == null ? 0 : instance.getWidth(), ribbonTabHeight + (drawShadow ? shadowHeight : 0));
    }
  }

  @Override
  public void updateUI() {
    determineTabHeight();
    super.updateUI();
  }

  /**
   * addTab
   *
   * @param tab is a top level menu for the top of ribbon.
   */
  public void addTab(RibbonTab tab) {
    ribbonTabPanel.addTab(tab);
    tab.addMouseListener(this);
    tabs.add(tab);
  }

  /**
   * Add some default button like minimize and maximize
   */
  private static void addDefaultButtons() {
    // IconUp 
    btnMinimize = new JButton();
    btnMinimize.setIcon(new IconUp(RibbonBarConstants.SLIM_BUTTON_IMAGE_SIZE, RibbonBarConstants.SLIM_BUTTON_IMAGE_SIZE));
    btnMinimize.addActionListener(e -> {
      bNeedToMin = true;
      btnMinimize.setEnabled(false);
      btnMaximize.setEnabled(true);
      minimizeTabPanel();
    });
    ribbonTabPanel.addButton(btnMinimize);
    // IconDown 
    btnMaximize = new JButton();
    btnMaximize.setEnabled(false);
    btnMaximize.setIcon(new IconDown(RibbonBarConstants.SLIM_BUTTON_IMAGE_SIZE, RibbonBarConstants.SLIM_BUTTON_IMAGE_SIZE));
    btnMaximize.addActionListener(e -> {
      bNeedToMin = false;
      btnMinimize.setEnabled(true);
      btnMaximize.setEnabled(false);
      RibbonBar.restoreTabPanel();
    });
    ribbonTabPanel.addButton(btnMaximize);
  }

  /**
   * Minimize our ribbon until just the quickbar and tabs show.
   */
  public static void minimizeTabPanel() {
    determineTabHeight();
    if (!bMinimized) {
      setTabContentsVisible(false);
      ribbonDimension = ribbonTabPanel.getSize();
      ribbonTabPanel.setPreferredSize(minimizedDim);
      bMinimized = true;
      SwingUtilities.invokeLater(() -> {
        instance.revalidate();
        SwingUtilities.updateComponentTreeUI(instance);
      });
    }
  }

  /**
   * Restores our Tab Panel to full size
   */
  public static void restoreTabPanel() {
    if (bMinimized) {
      setTabContentsVisible(true);

      if (ribbonPane != null) {
        ribbonTabPanel.setPreferredSize(ribbonDimension);
        ribbonPane.setBottomComponent(ribbonTabPanel);
        ribbonPane.setDividerSize(0);
        ribbonPane.resetToPreferredSizes();
        ribbonPane.revalidate();
      } else {
        ribbonTabPanel.setPreferredSize(ribbonDimension);
      }

      bMinimized = false;

      SwingUtilities.invokeLater(() -> SwingUtilities.updateComponentTreeUI(instance));
    }
  }

  /**
   * paintChildren
   * override for draw shadow
   *
   * @see javax.swing.JComponent#paintChildren(java.awt.Graphics)
   */
  @Override
  public void paintChildren(Graphics g) {
    super.paintChildren(g);
    Graphics2D g2d = (Graphics2D) g.create();
    if (drawShadow) {
      Color colorShadowDark;
      Color colorShadow;
      try {
        colorShadowDark = UIManager.getColor("Panel.background");
        colorShadow = UIManager.getColor("Panel.background");
      } catch (Exception e) {
        colorShadowDark = Color.DARK_GRAY;
        colorShadow = Color.GRAY;
      }
      if (colorShadowDark != null && colorShadow != null) {
        colorShadowDark = colorShadowDark.darker();
        GradientPaint shadowPaint = new GradientPaint(
                0, getHeight() - (float) shadowHeight,
                colorShadowDark,
                0, getHeight(),
                colorShadow);
        g2d.setPaint(shadowPaint);
        g2d.fill(new Rectangle2D.Double(0, getHeight() - (double) shadowHeight, getWidth(), getHeight()));
      }
    }
    g2d.dispose();
  }

  /**
   * Find all visual contents and set their visibility
   *
   * @param value of visibility
   */
  private static void setTabContentsVisible(boolean value) {
    getInstance().forEach(tab -> {
      tab.setVisible(value);
      tab.forEach(group -> {
        group.setVisible(value);
        group.forEach(component -> component.setVisible(value));
      });
    });
  }

  /**
   * Invoked when the mouse button has been clicked (pressed
   * and released) on a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    processMouseEvent(e);
  }


  /**
   * Invoked when a mouse button has been pressed on a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mousePressed(MouseEvent e) {
    super.processMouseEvent(e);
  }

  /**
   * Invoked when a mouse button has been released on a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    if (bMinimized) {
      restoreTabPanel();
    }
  }

  /**
   * Invoked when the mouse enters a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseEntered(MouseEvent e) {
    super.processMouseEvent(e);
  }

  /**
   * Invoked when the mouse exits a component.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseExited(MouseEvent e) {
    super.processMouseEvent(e);
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (bNeedToMin && !(e.getSource() instanceof DropDownButton)) {
      minimizeTabPanel();
    }
  }

  /**
   * Get ribbon shadow height
   *
   * @return shadow height
   */
  public static int getShadowHeight() {
    return shadowHeight;
  }

  /**
   * Returns an iterator over elements of type {@code T}.
   *
   * @return an Iterator.
   */
  @Override
  public Iterator<RibbonTab> iterator() {
    return tabs.iterator();
  }

  public static boolean isDrawShadow() {
    return drawShadow;
  }


  /**
   * @param drawShadow
   */
  @SuppressWarnings("unchecked")
  public static void setDrawShadow(boolean drawShadow) {
    RibbonBar.drawShadow = drawShadow;
  }
}

