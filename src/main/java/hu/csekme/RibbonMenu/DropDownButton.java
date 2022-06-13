package hu.csekme.RibbonMenu;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Dropdown button that allow to use submenu
 * @see #addSeparator()
 * @see #addSubMenu(JMenuItem)
 * @author csk
 */
public class DropDownButton extends JButton {

    private static final String template = "<html><body align=\"center\">%s<br>&ensp;</body></html>";

    private JPopupMenu popupMenu;

    public DropDownButton() {
        addMouseListener(ma);
    }

    public DropDownButton(String text) {
        setText(text);
        addMouseListener(ma);
    }

    /**
     * setText add some text to your button you can use plain text
     * it will force to html format for the following reasons:
     * <li>text must be center aligned<li/>
     * <li>add newline if necessary<li/>
     * <li>force a small space for the dropdown indicator<li/>
     * @param text
     */
    @Override
    public void setText(String text) {
        if (text != null && !text.contains("<html>")) {
            if (text.contains("\n")) {
                text = text.replace("\n", "<br>");
            }
            super.setText(String.format(template, text));
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
     * Add menu item to your submenu
     * @param item submenu item {JMenuItem, JCheckBoxMenuItem, JRadioButtonMenuItem}
     */
    public void addSubMenu(JMenuItem item) {
        if (popupMenu == null) {
            popupMenu = new JPopupMenu();
        }
        popupMenu.add(item);
    }

    private MouseAdapter ma = new MouseAdapter() {
        final JButton handle = DropDownButton.this;
        @Override
        public void mouseClicked(MouseEvent e) {
            // default click event to show submenu when the user clicked on mouse button 1
            if (e.getButton() == MouseEvent.BUTTON1 && popupMenu!=null) {
                popupMenu.show(handle,0, handle.getHeight());
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

            handle.setBorderPainted(true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            handle.setBorderPainted(false);
        }
    };
}
