package com.pckcs.RibbonMenu;

import javax.swing.JLabel;
import java.awt.*;

public class GroupName extends JLabel {

  private static final long serialVersionUID = 1L;

  public GroupName(String text) {
    super(text);
    this.setHorizontalTextPosition(JLabel.CENTER);
    this.setVerticalTextPosition(JLabel.BOTTOM);
    this.setHorizontalAlignment(JLabel.CENTER);
  }
}
