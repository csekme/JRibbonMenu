package hu.csekme.RibbonMenu;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

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

public class RibbonMenuItem extends JMenuItem {

    final JMenuItem ref = new JMenuItem();

    private boolean hover;
    private boolean pressed;
    private ImageIcon icon;
    private final static ImageIcon CHECKED_ICON = Util.accessImageFile("checked.png");
    private final static ImageIcon UNCHECKED_ICON = Util.accessImageFile("unchecked.png");
    private boolean checkMenu = false;
    private static Color colorHover = new Color(200, 198, 196);
    private static Color colorPressed = new Color(179, 176, 173);
    private static Color colorBackground = new Color(255, 255, 255);

    public RibbonMenuItem(String title, boolean defaultSelection) {
        super(title);
        this.checkMenu = true;
        this.setSelected(defaultSelection);
        addMouseListener(MA);
    }

    public RibbonMenuItem(String title) {
        super(title);
        addMouseListener(MA);
    }

    public RibbonMenuItem(String title, ImageIcon icon) {
        super(title);

        this.icon = icon;
        addMouseListener(MA);
    }

    public boolean isCheckMenu() {
        return checkMenu;
    }

    public void setCheckMenu(boolean checkMenu) {
        this.checkMenu = checkMenu;
    }

    public static void setHoverColor(Color color) {
        colorHover = color;
    }

    public static void setPressedColor(Color color) {
        colorPressed = color;
    }

    public static void setBackgroundColor(Color color) {
        colorBackground = color;
    }

    @Override
    public void paint(Graphics gl) {
        Graphics2D g = (Graphics2D) gl;

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.setFont(ref.getFont());
        g.setColor(colorBackground);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (hover) {
            g.setColor(colorHover);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        if (pressed) {
            g.setColor(colorPressed);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        g.setColor(ref.getForeground());
        g.drawString(getText(), getIconTextGap() + 27, 16);
        if (!isCheckMenu() && icon != null) {
            g.drawImage(icon.getImage(), 4, 3, 16, 16, this);
        }

        if (isCheckMenu()) {
            if (isSelected()) {
                g.drawImage(CHECKED_ICON.getImage(), 4, 3, 16, 16, this);

            } else {
                g.drawImage(UNCHECKED_ICON.getImage(), 4, 3, 16, 16, this);
            }
        }

        // super.paint(g);
    }

    MouseAdapter MA = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {

            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            setSelected(!isSelected());
            pressed = true;
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            hover = false;
            pressed = false;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            hover = false;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            hover = true;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            hover = true;
        }

    };

}
