package hu.csekme.RibbonMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.*;

// Copyright 2020 Csekme Krisztián
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
public class RibbonBar extends JComponent {

    // for generate tokens
    private final static String SERIES = "ABCDEFGHIJKLMNOPQRSTWZXYabcdefghijklmneopqrstzyxwv0123456789#&@{}*";

    /**
     * Semantic Versioning 2.0.0
     * https://semver.org/
     * MAJOR.MINOR.PATCH
     */
    private final static String VERSION = "1.0.0";

    // colors
    public static final int COLOR_RIBBON_BACKGROUND = 1;
    public static final int COLOR_RIBBON_TAB_CONTAINER_BACKGROUND = 2;
    public static final int COLOR_RIBBON_TAB_CONTAINER_STRIP = 3;
    public static final int COLOR_RIBBON_TAB_BACKGROUND = 4;
    public static final int COLOR_RIBBON_TAB_FOREGROUND = 5;
    public static final int COLOR_RIBBON_TAB_HOVER_BACKGROUND = 6;
    public static final int COLOR_RIBBON_TAB_HOVER_FOREGROUND = 7;
    public static final int COLOR_RIBBON_TAB_SELECTED_STRIP_BACKGROUND = 8;
    public static final int COLOR_RIBBON_TAB_SELECTED_FOREGROUND = 9;
    public static final int COLOR_RIBBON_BUTTON_BACKGROUND = 10;
    public static final int COLOR_RIBBON_BUTTON_PRESSED_BACKGROUND = 11;
    public static final int COLOR_RIBBON_BUTTON_HOVER_BACKGROUND = 12;
    public static final int COLOR_RIBBON_BUTTON_FOREGROUND = 13;
    public static final int COLOR_RIBBON_SEPARATOR_FOREGROUND = 14;
    public static final int COLOR_RIBBON_GROUP_COLOR = 15;
    public static final int COLOR_RIBBON_SHADOW_DARK = 16;
    public static final int COLOR_RIBBON_SHADOW_LIGHT = 17;
    public static final int COLOR_RIBBON_MENUITEM_HOVER = 18;
    public static final int COLOR_RIBBON_MENUITEM_PRESSED = 19;
    public static final int COLOR_RIBBON_MENUITEM_BACKGROUND = 20;

    // dimesnions
    static int ribbonTabHeight = 28;
    static int eastWestTabInset = 20;
    static int northTabInset = 0;
    static int ribbonButtonTopBase = ribbonTabHeight + 4;
    static int buttonWidth = 75;
    static int buttonHeight = 75;
    static int buttonPartialHeight = 34;
    static int slimButtonHeight = 25;
    static int separatorWidth = 7;
    static int separatorHeight = 88;
    static int shadowHeight = 10;
    static int ribbonHeight = 126 + shadowHeight;

    private static final JPopupMenu POPUP_MENU = new JPopupMenu();
    private Font font;

    // containers
    private static final Map<Integer, Color> COLORS = new HashMap<>();
    private static final Map<String, String> TOKENS = new HashMap<>();
    private static final List<Tab> TABS = new ArrayList<>();

    boolean buildMenu = true;

    /**
     * Constructor
     */
    public RibbonBar() {

        POPUP_MENU.setOpaque(true);
        POPUP_MENU.setBackground(Color.white);
        add(POPUP_MENU);

        if (font == null) {
            // inherit font from JMenuItem
            font = new JMenuItem().getFont();
        }

        {
            setMinimumSize(new Dimension(0, ribbonHeight));
            setPreferredSize(new Dimension(100, ribbonHeight));
        }

        // create default appearance
        COLORS.put(COLOR_RIBBON_BACKGROUND, new Color(243, 242, 241));
        COLORS.put(COLOR_RIBBON_TAB_CONTAINER_BACKGROUND, new Color(243, 242, 241));
        COLORS.put(COLOR_RIBBON_TAB_CONTAINER_STRIP, new Color(230, 229, 228));
        COLORS.put(COLOR_RIBBON_TAB_BACKGROUND, new Color(243, 242, 241));
        COLORS.put(COLOR_RIBBON_TAB_FOREGROUND, new Color(72, 70, 68));
        COLORS.put(COLOR_RIBBON_TAB_HOVER_BACKGROUND, new Color(250, 249, 248));
        COLORS.put(COLOR_RIBBON_TAB_HOVER_FOREGROUND, new Color(0, 0, 0));
        COLORS.put(COLOR_RIBBON_TAB_SELECTED_FOREGROUND, new Color(0, 191, 255));
        COLORS.put(COLOR_RIBBON_TAB_SELECTED_STRIP_BACKGROUND, new Color(0, 191, 255));
        COLORS.put(COLOR_RIBBON_BUTTON_BACKGROUND, new Color(243, 242, 241));
        COLORS.put(COLOR_RIBBON_BUTTON_HOVER_BACKGROUND, new Color(200, 198, 196));
        COLORS.put(COLOR_RIBBON_SEPARATOR_FOREGROUND, new Color(179, 176, 173));
        COLORS.put(COLOR_RIBBON_BUTTON_FOREGROUND, new Color(72, 70, 68));
        COLORS.put(COLOR_RIBBON_GROUP_COLOR, new Color(130, 130, 130));
        COLORS.put(COLOR_RIBBON_SHADOW_DARK, new Color(211, 211, 211));
        COLORS.put(COLOR_RIBBON_SHADOW_LIGHT, new Color(230, 230, 230));
        COLORS.put(COLOR_RIBBON_BUTTON_PRESSED_BACKGROUND, new Color(179, 176, 173));

        // add listeners
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    /**
     * Set color of an UI elements
     *
     * @param key   Class constant
     * @param value as Color
     */
    public void putColor(int key, Color value) {
        COLORS.put(key, value);
        if (key == COLOR_RIBBON_MENUITEM_HOVER) {
            RibbonMenuItem.setHoverColor(value);
        }
        if (key == COLOR_RIBBON_MENUITEM_PRESSED) {
            RibbonMenuItem.setPressedColor(value);
        }
        if (key == COLOR_RIBBON_MENUITEM_BACKGROUND) {
            RibbonMenuItem.setBackgroundColor(value);
        }
    }

    /**
     * build an entire menu structure
     */
    private void buildMenu() {

        int offset_t = 0;
        // iterates over supreme items
        for (int i = 0; i < TABS.size(); i++) {
            Tab tab = TABS.get(i);
            int w = getGraphics().getFontMetrics(font).stringWidth(tab.getTitle()) + (eastWestTabInset * 2);
            tab.setWidth(w);
            tab.setHeight(ribbonTabHeight);
            tab.setX(offset_t);
            tab.setY(northTabInset);
            offset_t += w;
            int offset_bx = 2;
            int offset_by = 0;
            int slim_count = 0;
            int slim_max = 0;

            //iterates over buttons
            for (int b = 0; b < tab.getButtons().size(); b++) {
                Button button = tab.getButtons().get(b);
                if (button.isSlim()) {
                    int sw;
                    if (button.getTitle() != null && button.getTitle().length() > 0) {
                        sw = getGraphics().getFontMetrics(font).stringWidth(button.title) + 26;
                    } else {
                        sw = 22;
                    }

                    if (slim_max < sw) {
                        slim_max = sw;
                    }
                    button.setWidth(sw);
                    button.setHeight(slimButtonHeight);
                    button.setX(offset_bx);
                    button.setY(ribbonButtonTopBase + offset_by);
                    slim_count++;
                    offset_by += slimButtonHeight;
                    if (slim_count == 3) {
                        offset_bx += slim_max;
                        offset_by = 0;
                        slim_max = 0;
                    }
                }
                if (button.isSeparator()) {
                    if (slim_count > 0) {
                        slim_count = 0;
                        offset_bx += slim_max;
                    }
                    button.setHeight(separatorHeight);
                    button.setX(offset_bx);
                    button.setY(ribbonButtonTopBase);
                    offset_by = 0;
                    offset_bx += separatorWidth;
                    slim_count = 0;
                }
                if (!button.isSlim() && !button.isSeparator()) {
                    if (slim_count > 0) {
                        slim_count = 0;
                        offset_bx += slim_max;
                    }

                    button.setWidth(buttonWidth);
                    button.setHeight(buttonHeight);
                    button.setX(offset_bx);
                    button.setY(ribbonButtonTopBase);
                    offset_bx += buttonWidth + 2;
                    slim_count = 0;
                    offset_by = 0;
                }
            }
        }

        repaint();
    }

    /**
     * Tab is a top level menu for the top of ribbon
     *
     * @param title caption of Tab button
     * @return currently created Tab
     */
    public Tab addTab(String title) {
        String gen = generateToken(8);
        TOKENS.put(gen, title);
        Tab tab = new Tab(gen);
        tab.setTitle(title);
        TABS.add(tab);
        return tab;
    }

    /**
     * Generate unique token for element
     *
     * @param length as length of token
     * @return desired token
     */
    public static String generateToken(int length) {
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(SERIES.charAt(rnd.nextInt(SERIES.length() - 1)));
        }
        return sb.toString();
    }

    /**
     * Graphics function
     *
     * @param gg
     */
    @Override
    public void paint(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;

        // set quality
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // first time build entire menu structure
        if (buildMenu) {
            buildMenu = false;
            buildMenu();
        }

        //Ribbon background
        g.setColor(COLORS.get(COLOR_RIBBON_BACKGROUND));
        g.fillRect(0, 0, getWidth(), getHeight());

        //Ribbon tab background
        g.setColor(COLORS.get(COLOR_RIBBON_TAB_CONTAINER_BACKGROUND));
        g.fillRect(0, 0, getWidth(), ribbonTabHeight);

        g.setColor(COLORS.get(COLOR_RIBBON_TAB_CONTAINER_STRIP));
        g.drawLine(0, ribbonTabHeight, getWidth(), ribbonTabHeight);

        // set graphics font
        if (font != null) {
            g.setFont(font);
        }

        // draw tabs
        for (int i = 0; i < TABS.size(); i++) {
            Tab tab = TABS.get(i);
            if (tab.isHover()) {
                g.setColor(COLORS.get(COLOR_RIBBON_TAB_HOVER_BACKGROUND));
            } else {
                g.setColor(COLORS.get(COLOR_RIBBON_TAB_BACKGROUND));
            }
            g.fillRect(tab.getX(), tab.getY(), tab.getWidth(), tab.getHeight());

            //Selected tab
            if (tab.isSelected()) {
                g.setColor(COLORS.get(COLOR_RIBBON_TAB_SELECTED_STRIP_BACKGROUND));
                if (tab.isHover()) {
                    g.fillRect(tab.getX(), tab.getY() + tab.getHeight() - 4, tab.getWidth(), 4);
                } else {
                    int half = (tab.getWidth() - g.getFontMetrics().stringWidth(tab.getTitle())) / 2;
                    g.fillRect(tab.getX() + half, tab.getY() + tab.getHeight() - 4, tab.getWidth() - half * 2, 4);
                }
            }

            if (tab.isHover()) {
                g.setColor(COLORS.get(COLOR_RIBBON_TAB_HOVER_FOREGROUND));
            } else {
                g.setColor(COLORS.get(COLOR_RIBBON_TAB_FOREGROUND));
            }
            if (tab.isSelected()) {
                g.setColor(COLORS.get(COLOR_RIBBON_TAB_SELECTED_FOREGROUND));
            }
            g.drawString(tab.getTitle(), tab.getX() + tab.getWidth() / 2 - g.getFontMetrics().stringWidth(tab.getTitle()) / 2, tab.getY() + 20);

            int horizontal_offset = 0;

            // render selected tab
            if (tab.isSelected()) {
                { //Group title
                    g.setFont(font.deriveFont(9f));
                    for (int s = 0; s < tab.getGroups().size(); s++) {
                        String groupname = tab.getGroups().get(s);
                        g.setColor(COLORS.get(COLOR_RIBBON_GROUP_COLOR));

                        int groupname_length = g.getFontMetrics().stringWidth(groupname);
                        int west = getWidth();
                        Button sep = tab.getSeparator(s);
                        if (sep != null) {
                            west = sep.getX();
                        }
                        g.drawString(groupname, horizontal_offset + (west - horizontal_offset) / 2 - groupname_length / 2, getHeight() - 8 - shadowHeight);
                        horizontal_offset += west;
                    }
                    g.setFont(font);
                }
                if (shadowHeight > 0) {
                    GradientPaint shadow_paint = new GradientPaint(0, getHeight() - shadowHeight, COLORS.get(COLOR_RIBBON_SHADOW_DARK), 0, getHeight(), COLORS.get(COLOR_RIBBON_SHADOW_LIGHT));
                    g.setPaint(shadow_paint);
                    g.fill(new Rectangle2D.Double(0, getHeight() - shadowHeight, getWidth(), getHeight()));
                }
                // Buttons under selected tab
                for (int y = 0; y < tab.getButtons().size(); y++) {
                    Button button = tab.getButtons().get(y);

                    if (button.isSeparator()) {
                        g.setColor(COLORS.get(COLOR_RIBBON_SEPARATOR_FOREGROUND));
                        g.drawLine(button.getX() + separatorWidth / 2, button.getY() + 1, button.getX() + separatorWidth / 2, button.getY() + separatorHeight);
                    } else {
                        if (button.isHover()) {
                            g.setColor(COLORS.get(COLOR_RIBBON_BUTTON_HOVER_BACKGROUND));
                        } else {
                            g.setColor(COLORS.get(COLOR_RIBBON_BUTTON_BACKGROUND));
                        }
                        if (button.isPressed()) {
                            g.setColor(COLORS.get(COLOR_RIBBON_BUTTON_PRESSED_BACKGROUND));
                        }

                        if (!button.isSlim() && button.hasDropDown() && button.isHover()) {
                            if (button.isHoverTop()) {
                                g.fillRect(button.getX(), button.getY(), button.getWidth() + 1, buttonPartialHeight);
                                g.drawRect(button.getX(), button.getY() + buttonPartialHeight, button.getWidth(), button.getHeight() - buttonPartialHeight);
                            } else {
                                g.drawRect(button.getX(), button.getY(), button.getWidth(), buttonPartialHeight);
                                g.fillRect(button.getX(), button.getY() + buttonPartialHeight, button.getWidth() + 1, button.getHeight() - buttonPartialHeight);

                            }

                        } else {
                            g.fillRect(button.getX(), button.getY(), button.getWidth(), button.getHeight());
                        }

                        g.setColor(COLORS.get(COLOR_RIBBON_BUTTON_FOREGROUND));
                        if (button.isSlim()) {
                            if (button.getImage() == null) {
                                g.drawString(button.title, button.getX() + 4, button.getY() + button.getHeight() - 8);
                            } else {
                                g.drawImage(button.getImage().getImage(), button.getX() + 2, button.getY() + 4, 16, 16, this);
                                g.drawString(button.title, button.getX() + 4 + 16, button.getY() + button.getHeight() - 8);
                            }
                        } else {
                            if (button.hasDropDown()) {
                                if (button.getImage() != null) {
                                    g.drawImage(button.getImage().getImage(), button.getX() + 26, button.getY() + 6, 24, 24, this);
                                }
                                String[] lines = button.title.split(" ");
                                for (int l = 0; l < lines.length; l++) {
                                    int w = g.getFontMetrics().stringWidth(lines[l]);
                                    g.drawString(lines[l], button.getX() + button.getWidth() / 2 - w / 2, button.getY() + button.getHeight() - 18 + (l * 14) - (lines.length > 1 ? 10 : 0));
                                }
                                g.setColor(Color.GRAY);
                                g.setStroke(new BasicStroke(1.3f));
                                g.drawLine(button.getX() + button.getWidth() / 2 - 3, button.getY() + button.getHeight() - 8, button.getX() + button.getWidth() / 2, button.getY() + button.getHeight() - 6);
                                g.drawLine(button.getX() + button.getWidth() / 2 + 3, button.getY() + button.getHeight() - 8, button.getX() + button.getWidth() / 2, button.getY() + button.getHeight() - 6);

                                //Normal classic button
                            } else {
                                if (button.getImage() != null) {
                                    g.drawImage(button.getImage().getImage(), button.getX() + 26, button.getY() + 6, 24, 24, this);
                                }
                                String[] lines = button.title.split(" ");
                                for (int l = 0; l < lines.length; l++) {
                                    int w = g.getFontMetrics().stringWidth(lines[l]);
                                    g.drawString(lines[l], button.getX() + button.getWidth() / 2 - w / 2, button.getY() + button.getHeight() - 18 + (l * 14) - (lines.length > 1 ? 10 : 0));
                                }

                            }

                        }
                    }
                }
            }

        }
        super.paint(g);
    }

    /**
     * Clear all selection and hover flags
     */
    public void clearFlag() {
        for (int i = 0; i < TABS.size(); i++) {
            for (int j = 0; j < TABS.get(i).getButtons().size(); j++) {
                TABS.get(i).getButtons().get(j).setSelected(false);
                TABS.get(i).getButtons().get(j).setHover(false);
            }
        }
        repaint();
    }

    /**
     * Global mouse adapter
     */
    final MouseAdapter mouse = new MouseAdapter() {

        @Override
        public void mouseMoved(MouseEvent e) {
            for (int i = 0; i < TABS.size(); i++) {
                TABS.get(i).setHover(false);
            }
            for (int i = 0; i < TABS.size(); i++) {
                TABS.get(i).setHover(TABS.get(i).inBounds(e.getPoint(), TABS.get(i).getToken()));
            }
            for (int i = 0; i < TABS.size(); i++) {
                Tab t = TABS.get(i);
                if (t.isSelected()) {
                    for (int j = 0; j < t.getButtons().size(); j++) {
                        Button b = t.getButtons().get(j);
                        if (!b.isSeparator()) {
                            b.setHover(b.inBounds(e.getPoint(), b.getToken()));
                            if (b.hasDropDown()) {
                                b.setHoverTop(b.inBoundsPartOf(e.getPoint(), buttonPartialHeight, b.getToken()));
                            }
                        }
                    }
                }
            }
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {

            if (e.getPoint().y <= ribbonTabHeight) {
                boolean found = false;
                for (int i = 0; i < TABS.size(); i++) {
                    if (TABS.get(i).inBounds(e.getPoint(), TABS.get(i).getToken())) {
                        found = true;
                    }
                }
                if (found) {
                    for (int i = 0; i < TABS.size(); i++) {
                        TABS.get(i).setSelected(TABS.get(i).inBounds(e.getPoint(), TABS.get(i).getToken()));
                    }
                }
            }
            if (e.getPoint().y > ribbonTabHeight) {
                for (int t = 0; t < TABS.size(); t++) {

                    Tab tab = TABS.get(t);
                    if (tab.isSelected()) {
                        for (int b = 0; b < TABS.get(t).getButtons().size(); b++) {
                            Button but = TABS.get(t).getButtons().get(b);
                            but.setPressed(false);
                            if (but.inBounds(e.getPoint(), but.getToken())) {
                                if (!but.hasDropDown() || but.hoverTop) {
                                    but.fireAction(new ActionEvent(RibbonBar.this, (int) ActionEvent.MOUSE_EVENT_MASK, "onClick"));
                                }
                                if (but.hasDropDown() && !but.hoverTop) {
                                    POPUP_MENU.removeAll();
                                    for (int i = 0; i < but.getSubMenuList().size(); i++) {
                                        if (but.getSubMenuList().get(i) instanceof JMenuItem) {
                                            POPUP_MENU.add((JMenuItem) but.getSubMenuList().get(i));
                                        }
                                        if (but.getSubMenuList().get(i) instanceof JCheckBoxMenuItem) {
                                            POPUP_MENU.add((JCheckBoxMenuItem) but.getSubMenuList().get(i));
                                        }
                                    }
                                    POPUP_MENU.show(RibbonBar.this, but.getX(), but.getY() + but.getHeight());
                                }
                            }
                        }
                    }
                }
            }
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getPoint().y > ribbonTabHeight) {
                for (int t = 0; t < TABS.size(); t++) {
                    Tab tab = TABS.get(t);
                    if (tab.isSelected()) {
                        for (int b = 0; b < TABS.get(t).getButtons().size(); b++) {
                            Button but = TABS.get(t).getButtons().get(b);
                            but.setPressed(but.inBounds(e.getPoint(), but.getToken()));
                        }
                    }
                }
            }
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            clearFlag();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

    };
}
