package com.pckcs.RibbonMenu.Icons;

import com.formdev.flatlaf.extras.FlatSVGIcon;

public class IconDown extends FlatSVGIcon {

  private static final String resourcePath = "images/down.svg";

  public IconDown() {
    super(resourcePath);
    setColorFilter(new RibbonColorFilter());
  }

  public IconDown(int width, int height) {
    super(resourcePath, width, height);
    setColorFilter(new RibbonColorFilter());
  }

}
