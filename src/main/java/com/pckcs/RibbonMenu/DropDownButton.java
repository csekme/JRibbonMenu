package com.pckcs.RibbonMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dropdown button that allow to use submenu
 *
 * @author csk
 * @see #addSeparator()
 * @see #addSubMenu(JMenuItem)
 */
public class DropDownButton extends JButton implements ActionListener {
  
  private static final long serialVersionUID = 1L;
  
  // label name template
  private static final String TEMPLATE = "<html><body align=\"center\">%s<br>&ensp;</body></html>";
  // container for submenu
  private JPopupMenu popupMenu;

  public DropDownButton() {
    addActionListener(this);
  }

  public DropDownButton(String text) {
    setText(text);
    addActionListener(this);
  }

  /**
   * setText add some text to your button you can use plain text
   * it will force to html format for the following reasons:
   * <li>text must be center aligned<li/>
   * <li>add newline if necessary<li/>
   * <li>force a small space for the dropdown indicator<li/>
   *
   * @param text
   */
  @Override
  public void setText(String text) {
    if (text != null && !text.contains("<html>")) {
      if (text.contains("\n")) {
        text = text.replace("\n", "<br>");
      }
      super.setText(String.format(TEMPLATE, text));
    } else {
      //<br>&ensp; TODO: if html text is entered you have to check for the existence of white characters with line breaks
      super.setText(text);
    }
  }

  @Override
  protected void paintComponent(Graphics r) {
    Graphics2D g = (Graphics2D) r;
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    super.paintComponent(g);
    { //draw dropdown indicator
      int y = getHeight() - 12;
      int mid = getWidth() / 2;
      g.setStroke(new BasicStroke(1.5f));
      g.drawLine(mid - 3, y, mid, y + 3);
      g.drawLine(mid + 3, y, mid, y + 3);
    }
  }

  /**
   * Add separator to your submenu
   */
  public void addSeparator() {
    if (popupMenu == null) {
      popupMenu = new JPopupMenu();
    }
    popupMenu.addSeparator();
  }

  /**
   * Override updateUI to load new Look &#38; Feel if necessary
   * on entire component tree
   */
  @Override
  public void updateUI() {
    super.updateUI();
    if (popupMenu != null) {
      popupMenu.updateUI();
      SwingUtilities.updateComponentTreeUI(popupMenu);
    }
  }

  /**
   * Add menu item to your submenu
   *
   * @param item submenu item {JMenuItem, JCheckBoxMenuItem, JRadioButtonMenuItem}
   */
  public void addSubMenu(JMenuItem item) {
    if (popupMenu == null) {
      popupMenu = new JPopupMenu();
    }
    popupMenu.add(item);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    popupMenu.show(DropDownButton.this, 0, DropDownButton.this.getHeight());
  }
}
