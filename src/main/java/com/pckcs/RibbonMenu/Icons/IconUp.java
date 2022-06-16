package com.pckcs.RibbonMenu.Icons;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class IconUp extends FlatSVGIcon {

  private static final String resourcePath = "images/up.svg";

  public IconUp() {
    super(resourcePath);
    setColorFilter(new RibbonColorFilter());
  }

  public IconUp(int width, int height) {
    super(resourcePath, width, height);
    setColorFilter(new RibbonColorFilter());
  }
}
