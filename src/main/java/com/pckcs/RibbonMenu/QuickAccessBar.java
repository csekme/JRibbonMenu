package com.pckcs.RibbonMenu;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;

import javax.swing.*;

/**
 * The Class QuickAccessBar.
 *
 * @author Paul Conti
 */
public class QuickAccessBar extends JPanel {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The instance. */
  private static QuickAccessBar instance = null;
  
  /** The layout for quickbar. */
  FlowLayout layout;

  /** toolbar */
  private static BackgroundBar toolbar;
  
  /**
   * RibbonBar Factory to create our Singleton Object.
   * 
   * @return Ribbonbar instance only one permitted
   */
  public static synchronized QuickAccessBar create() {
    if (instance == null) {
      instance = new QuickAccessBar();
    }
    return instance;
  }


  /**
   * Constructor.
   */
  public QuickAccessBar() {
    toolbar = new BackgroundBar(JToolBar.HORIZONTAL);
    toolbar.setOpaque(true);
    layout = new FlowLayout(FlowLayout.LEADING, 2, 4);
    this.setLayout(layout);
    this.add(toolbar);
  }
	
	/**
   * addButton.
   *
   * @param button
   *          the button
   */
	public void addButton(JButton button) {
    if (button.getIcon() != null) {
      button.setIcon(Util.scaleIcon(button.getIcon(), DisplayState.QUICK));
      button.setBorderPainted(false);

      button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
          button.setBorderPainted(true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
          button.setBorderPainted(false);
        }
      });

    }
    toolbar.add(button);
	}

	public JToolBar get() {
	  return toolbar;
	}
	
  /**
   * Add default separator
   * @see RibbonSeparator
   */
  public void addSeparator() {
    JToolBar.Separator separator = new JToolBar.Separator();
    toolbar.add(separator);
  }

  public class BackgroundBar extends JToolBar
  {
      private static final long serialVersionUID = 1L;
      
      private Color colorBackground;
      private Color colorBorder;

      public BackgroundBar(int horizontal) {
        super(horizontal);
      }

      /**
       * Override the updateUI function to follow changes to the theme
       */
      @Override
      public void updateUI() {
        colorBackground = UIManager.getColor("Button.background");
        colorBorder = UIManager.getColor("MenuBar.borderColor");
      }

      @Override
      protected void paintComponent(Graphics g)
      {
          super.paintComponent(g);
          Graphics2D g2d = (Graphics2D) g.create();
          
          g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
          double height = (double)getHeight() - 1.0f;
          int minX = 0;
          int maxX = getWidth() - 15;

          float radius = (float) height / 2.0f;

          GeneralPath outline = new GeneralPath();

          // top left corner
            outline.moveTo(minX - 1, 0);
          // top right corner
          outline.lineTo(maxX, 0);
          // right arc
          outline.append(new Arc2D.Double(maxX - radius, 0, height,
              height, 90, -180, Arc2D.OPEN), true);
          // bottom left corner
          outline.lineTo(minX - 1, height);
          outline.lineTo(minX - 1, 0);

          g2d.setColor(colorBackground);
          g2d.fillRect(0, 0, getWidth()-1, getHeight()-1);

          Shape contour = outline;
          if (contour != null) {
            g2d.setColor(colorBorder);
            g2d.draw(contour);
          }
          g2d.dispose();

      }
  }
  
}
