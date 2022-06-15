package com.pckcs.RibbonMenu;

import javax.swing.*;
import java.awt.*;

/**
 * Helps to group or visually segment different controls
 * Foreground color set by UI key of "ToolBar.separatorColor" come from FlatLaf
 * You can change it manually by set Color with {@link #setForeground(Color)}
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
   * Override default foreground color
   *
   * @param fg passed color
   */
  @Override
  public void setForeground(Color fg) {
    colorUser = fg;
  }

  /**
   * Override the updateUI function to follow changes to the theme
   */
  @Override
  public void updateUI() {
    color = UIManager.getColor("ToolBar.separatorColor");
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
  public void paint(Graphics r) {
    super.paint(r);
    Graphics2D g = (Graphics2D) r;
    if (colorUser == null) {
      g.setColor(color);
    } else {
      g.setColor(colorUser);
    }
    g.setStroke(new BasicStroke(wide));
    g.drawLine(
            getWidth() / 2 - (int) wide / 2,
            insets.top,
            getWidth() / 2 - (int) wide / 2,
            getHeight() - insets.top - insets.bottom
    );
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
