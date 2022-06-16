package com.pckcs.RibbonMenu.Icons;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class IconPinned extends FlatSVGIcon {

  private static final String resourcePath = "images/pinned.svg";

  public IconPinned() {
    super(resourcePath);
    setColorFilter(new RibbonColorFilter());
  }

  public IconPinned(int width, int height) {
    super(resourcePath, width, height);
    setColorFilter(new RibbonColorFilter());
  }
}


