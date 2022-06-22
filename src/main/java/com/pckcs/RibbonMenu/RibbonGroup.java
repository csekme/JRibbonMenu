package com.pckcs.RibbonMenu;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * The Class RibbonGroup.
 *
 * @author Paul Conti
 */
public class RibbonGroup extends JPanel implements Iterable<JComponent> {

  // The Constant serialVersionUID.
  private static final long serialVersionUID = 1L;

  // some must have variables
  private int nColumns = 0;
  private int nRows    = 0;
  private int nStacked = 0;

  // default layout
  private GridBagLayout layout;

  // title for group
  private GroupName lblTitle = null;

  // list of added components
  List<JComponent> components;

  /**
   * Instantiates a new group.
   */
  public RibbonGroup() {
    this.components = new ArrayList<>();
    layout = new GridBagLayout();
    this.setLayout(layout);
  }

  /**
   * Instantiates a new group with default title.
   *
   * @param title the title
   */
  public RibbonGroup(String title) {
    this.components = new ArrayList<>();
    lblTitle = new GroupName(title);
    lblTitle.setHorizontalAlignment(JLabel.CENTER);
    lblTitle.setVerticalTextPosition(JLabel.BOTTOM);
    layout = new GridBagLayout();
    this.setLayout(layout);
    GridBagConstraints c = new GridBagConstraints();
    c.weightx = 1;
    c.weighty = 1;
    c.anchor = GridBagConstraints.SOUTH;
    c.gridwidth = nColumns;
    c.gridx = 0;
    c.gridy = 3;
    c.insets = new Insets(0,0,2,0);
    this.add(lblTitle, c);
  }

  /**
   * Override updateUI to add custom logic when changing themes if needed
   */
  @Override
  public void updateUI() {
    super.updateUI();
    // some use case better visibility
    this.setBackground(UIManager.getColor("Button.background"));
    if (lblTitle!=null) {
      lblTitle.setEnabled(false);
    }
  }

  /**
   * Add component to ribbon group.
   *
   * @param comp the comp JButton, JCombobox etc.
   * @param state the state
   */
  public void addComponent(JComponent comp, DisplayState state) {
    components.add(comp);
    if (comp instanceof AbstractButton) {
      AbstractButton ab = (AbstractButton) comp;
      if (ab.getIcon() != null && state != DisplayState.NORMAL) {
        ab.setIcon(Util.scaleIcon(ab.getIcon(), state));
      }
      if (comp instanceof JButton && state != DisplayState.SLIM) {
        JButton jb = (JButton) comp;
        jb.setVerticalTextPosition(SwingConstants.BOTTOM);
        jb.setHorizontalTextPosition(SwingConstants.CENTER);
      }
      ab.setBorderPainted(false);

      comp.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
          ab.setBorderPainted(true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
          ab.setBorderPainted(false);
        }
      });
    }


    GridBagConstraints c = new GridBagConstraints();
    c.weightx = 1.0;
    c.weighty = 1.0;
    c.insets = new Insets(2,2,0,0);
    c.anchor = GridBagConstraints.WEST;

    if (state == DisplayState.SLIM) {
      c.weighty = 0.0;
      c.fill = GridBagConstraints.HORIZONTAL;
      switch (nStacked) {
      case 0:
        c.gridx = nColumns;
        c.gridy = 0;
        break;
      case 1:
        c.gridx = nColumns;
        c.gridy = 1;
        break;
      case 2:
        c.gridx = nColumns++;
        c.gridy = 2;
        break;
      default:
        break;
      }
      nStacked++;
      if (nStacked > 2)
        nStacked = 0;
      this.add(comp, c);
      nRows++;
    } else if (state == DisplayState.SEPARATOR) {
      if (comp instanceof RibbonSeparator) {
        RibbonSeparator sep = (RibbonSeparator) comp;
        sep.setPreferredSize(new Dimension(sep.getWidth(),RibbonBar.getInstance().getHeight()));
      } else {
        throw new RuntimeException("Please use RibbonSeparator component for state of SEPARATOR");
      }
      c.fill = GridBagConstraints.VERTICAL;
      c.gridheight = 4;
      c.gridx = nRows++;
      c.gridy = 0;
      this.add(comp, c);
    } else {
      c.fill = GridBagConstraints.BOTH;
      c.gridx = nRows++;
      c.gridy = 0;
      this.add(comp, c);
    }
    Method actionListener;
    try {
      actionListener = comp.getClass().getMethod("addActionListener",  ActionListener.class);
      actionListener.invoke(comp, RibbonBar.getInstance());
      if (comp instanceof DropDownButton) {
        JPopupMenu handle = ((DropDownButton)comp).getPopupMenu();
        handle.addPopupMenuListener(new PopupMenuListener() {
          @Override
          public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

          }

          @Override
          public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            RibbonBar.getInstance().actionPerformed(new ActionEvent(handle, -1, "MENU_ACTION"  ));
          }

          @Override
          public void popupMenuCanceled(PopupMenuEvent e) {
            RibbonBar.getInstance().actionPerformed(new ActionEvent(handle, -1, "MENU_ACTION"  ));
          }
        });
      }
    } catch (NoSuchMethodException e) {
      //no actionListener has found
    } catch (InvocationTargetException e) {
      //no ne
    } catch (IllegalAccessException e) {
      //
    }
  }

  @Override
  @Deprecated
  public Component add(Component component) {
    throw new RuntimeException("Do not use this method. Use instead addComponent(JComponent comp, DisplayState state) signature");
  }

  /**
   * Add default separator
   * 
   * @see RibbonSeparator
   */
  public void addSeparator() {
    addComponent(new RibbonSeparator(), DisplayState.SEPARATOR);
  }

  /**
   * Returns an iterator over elements of type {@code T}.
   *
   * @return an Iterator.
   */
  @Override
  public Iterator<JComponent> iterator() {
    return components.iterator();
  }

  public GroupName getTitle() {
    return lblTitle;
  }

}
