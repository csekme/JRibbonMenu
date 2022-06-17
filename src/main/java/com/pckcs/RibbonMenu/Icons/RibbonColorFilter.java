package com.pckcs.RibbonMenu.Icons;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class RibbonColorFilter extends FlatSVGIcon.ColorFilter implements Function<Color, Color> {

  private Color lastColor;

  public RibbonColorFilter() {
    setMapper(this);
  }

  /**
   * Applies this function to the given argument.
   *
   * @param color the function argument
   * @return the function result
   */
  @Override
  public Color apply(Color color) {
    if (UIManager.getColor("ToolBar.separatorColor") == null) {
      UIManager.put("ToolBar.separatorColor", new JSeparator().getForeground());
    }
    if (color.getRGB() == new Color(90, 90, 90).getRGB()) {
      if (UIManager.getBoolean("laf.dark")) {
        lastColor = UIManager.getColor("ToolBar.separatorColor").brighter().brighter();
      } else {
        lastColor = UIManager.getColor("ToolBar.separatorColor").darker();
      }
      return lastColor;
    }
    if (lastColor != null && lastColor.getRGB() == color.getRGB()) {
      if (UIManager.getBoolean("laf.dark")) {
        lastColor = UIManager.getColor("ToolBar.separatorColor").brighter().brighter();
      } else {
        lastColor = UIManager.getColor("ToolBar.separatorColor").darker();
      }
      return lastColor;
    } else {
      return color;
    }
  }
}
