package com.pckcs.RibbonMenu;

import javax.swing.*;
import java.awt.*;

/**
 * Helps to group or visually segment different controls
 * Foreground color set by UI key of "ToolBar.separatorColor" come from FlatLaf
 * You can change it manually by set Color with {@link #setColor(Color)}
 *
 * @author csk
 * @see RibbonGroup
 */
public class RibbonSeparator extends JComponent {

  private static final short defaultMarinTop = 2;
  private static final short defaultMarinBottom = 2;
  private static final short defaultMarinLeft = 6;
  private static final short defaultMarinRight = 6;
  private static final float defaultWide = 1f;

  //padding (space between content and border)
  private Insets insets = new Insets(defaultMarinTop, defaultMarinLeft, defaultMarinBottom, defaultMarinRight);
  //separator width
  private float wide = defaultWide;
  //separator color
  private Color color;
  //separator with user specified color
  private Color colorUser;

  /**
   * Set color manually
   * @param color passed color
   */
  public void setColor(Color color) {
    this.colorUser = color;
  }

  /**
   * Override the updateUI function to follow changes to the theme
   */
  @Override
  public void updateUI() {
    color = UIManager.getColor("ToolBar.separatorColor");
    if (UIManager.getBoolean("laf.dark")) {
      color = color.brighter();
    }
  }

  /**
   * Virtual width calculated from margin and brush wide.
   *
   * @return brush wide + margin left + margin right
   */
  @Override
  public int getWidth() {
    return (int) wide + insets.left + insets.right;
  }

  @Override
  public Dimension getMaximumSize() {
    return new Dimension(getWidth(), Integer.MAX_VALUE);
  }

  /**
   * Override preferred size by use getWidth and getHeight functions
   *
   * @return Calculated preferred size
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(getWidth(), getHeight());
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setStroke(new BasicStroke(wide));
    if (colorUser == null) {
      g2d.setColor(color);
    } else {
      g2d.setColor(colorUser);
    }
    System.out.println(g2d.getColor());
    g2d.drawLine(
            getWidth() / 2 - (int) wide / 2,
            insets.top,
            getWidth() / 2 - (int) wide / 2,
            getHeight() - insets.top - insets.bottom
    );
    g2d.dispose();
  }

  /**
   * Set space between content and border
   *
   * @param insets {top, left, bottom, right}
   */
  public void setPadding(Insets insets) {
    if (insets == null) {
      this.insets = new Insets(0, 0, 0, 0);
    } else {
      this.insets = insets;
    }
  }

  /**
   * Separator draw line with this width
   *
   * @param wide line width
   */
  public void setWide(float wide) {
    this.wide = wide;
  }
}
