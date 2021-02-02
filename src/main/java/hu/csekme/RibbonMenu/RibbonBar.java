/**
 * Copyright 2021 Csekme Krisztián
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hu.csekme.RibbonMenu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
/**
 * Windows styled RibbonBar main component
 * @author Csekme Krisztián
 * @see Tab
 * @see Button
 * @see RibbonMenuItem
 */
public class RibbonBar extends JComponent {

	private static final long serialVersionUID = -2268640468339894311L;

	// for generate tokens
	private final static String SERIES = "ABCDEFGHIJKLMNOPQRSTWZXYabcdefghijklmneopqrstzyxwv0123456789#&@{}*";

	private static RibbonBar instance = null;

	
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
	public static final int COLOR_RIBBON_TAB_SELECTED_BACKGROUND = 21;
	public static final int COLOR_RIBBON_BUTTON_HOVER_BORDER_COLOR = 22;
	
	public static final double SCALING_FACTOR  =((double)java.awt.Toolkit.getDefaultToolkit().getScreenResolution()) / 96 ;
	static {
		// SCALING_FACTOR = 1.0;
		// SCALING_FACTOR =((double)java.awt.Toolkit.getDefaultToolkit().getScreenResolution()) / 96 ;
	}
	public static int SIZE_BUTTON_WIDTH = 75;
	private static int SIZE_BUTTON_HEIGHT = 75;
	public static int BUTTON_IMAGE_SIZE = 24;
	
	
	// dimesnions
	static int tabLayoutWestEastMargin = 8;
	static int ribbonTabHeight = (int)(28 * SCALING_FACTOR);
	static int stripHeight = 0;
	static int eastWestTabInset = 20;
	static int northTabInset = 0;
	static int buttonLeftRightMargin = 4;
	static int ribbonButtonTopBase = ribbonTabHeight + 4;
	static int buttonWidth = (int)(SIZE_BUTTON_WIDTH * SCALING_FACTOR);
	static int buttonHeight = (int)(SIZE_BUTTON_HEIGHT * SCALING_FACTOR);
	static int buttonPartialHeight = (int)(35 * SCALING_FACTOR);
	static int slimButtonHeight = (int)(25 * SCALING_FACTOR);
	static int separatorWidth = 7;
	static int separatorHeight = (int)(88 * SCALING_FACTOR);
	static int shadowHeight = 10;
	static int ribbonHeight = (int)(126 * SCALING_FACTOR) + shadowHeight;

	boolean minimized = false;
	boolean reminimized = false;

	static final JPopupMenu POPUP_MENU = new JPopupMenu();
	private Font font;

	// containers
	static final Map<Integer, Color> COLORS = new HashMap<>();
	static final Map<String, String> TOKENS = new HashMap<>();
	static final List<Tab> TABS = new ArrayList<>();

	static final Button toggle = new Button(generateToken(20));
	private static ImageIcon pinned = Util.accessImageFile("images/pinned.png");

	boolean buildMenu = true;

 

	/**
	 * Constructor
	 */
	public RibbonBar() {
		buttonWidth = (int)(SIZE_BUTTON_WIDTH * SCALING_FACTOR);
		POPUP_MENU.setOpaque(true);
		POPUP_MENU.setBackground(Color.white);
		add(POPUP_MENU);

		if (font == null) {
			// inherit font from JMenuItem
			font = new JMenuItem().getFont().deriveFont(Font.PLAIN).deriveFont(12f);
		}
		{
			setMinimumSize(new Dimension(0, ribbonHeight));
			setPreferredSize(new Dimension(100, ribbonHeight));
		}
		toggle.setImage(Util.accessImageFile("images/minimize.png"));

		// create default appearance
		COLORS.put(COLOR_RIBBON_BACKGROUND, new Color(245, 246, 247));
		COLORS.put(COLOR_RIBBON_TAB_CONTAINER_BACKGROUND, new Color(255, 255, 255));
		COLORS.put(COLOR_RIBBON_TAB_CONTAINER_STRIP, new Color(230, 229, 228));
	
		//tab backgrounds
		COLORS.put(COLOR_RIBBON_TAB_BACKGROUND, new Color(255, 255, 255));
		COLORS.put(COLOR_RIBBON_TAB_HOVER_BACKGROUND, new Color(250, 251, 252));
		COLORS.put(COLOR_RIBBON_TAB_SELECTED_BACKGROUND,   new Color(245, 246, 247));
		COLORS.put(COLOR_RIBBON_TAB_FOREGROUND, new Color(70, 70, 70));
		COLORS.put(COLOR_RIBBON_TAB_HOVER_FOREGROUND,  new Color(70, 70, 70));
		COLORS.put(COLOR_RIBBON_TAB_SELECTED_FOREGROUND,  new Color(70, 70, 70));
		COLORS.put(COLOR_RIBBON_TAB_SELECTED_STRIP_BACKGROUND, new Color(245, 246, 247));
		
		COLORS.put(COLOR_RIBBON_BUTTON_BACKGROUND, new Color(245, 246, 247));
		COLORS.put(COLOR_RIBBON_BUTTON_HOVER_BACKGROUND, new Color(232, 239, 247));
		COLORS.put( COLOR_RIBBON_BUTTON_HOVER_BORDER_COLOR, new Color(164, 206, 249));
		
		COLORS.put(COLOR_RIBBON_SEPARATOR_FOREGROUND, new Color(179, 176, 173));
		COLORS.put(COLOR_RIBBON_BUTTON_FOREGROUND, new Color(72, 70, 68));
		COLORS.put(COLOR_RIBBON_GROUP_COLOR, new Color(130, 130, 130));
		COLORS.put(COLOR_RIBBON_SHADOW_DARK, new Color(211, 211, 211));
		COLORS.put(COLOR_RIBBON_SHADOW_LIGHT, new Color(230, 230, 230));
		COLORS.put(COLOR_RIBBON_BUTTON_PRESSED_BACKGROUND, new Color(201, 224, 247));	
		
		// add listeners
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		// register for tooltips
	    ToolTipManager.sharedInstance().registerComponent(this);		
	}

	/**
	 * Set color of an UI elements
	 *
	 * @param key   Class constant
	 * @param value as Color
	 */
	public static void putColor(int key, Color value) {
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

	void toggle() {
		if (minimized) {
			setMinimumSize(new Dimension(100, ribbonTabHeight + shadowHeight));
			setPreferredSize(new Dimension(getWidth(), ribbonTabHeight + shadowHeight));
			setSize(new Dimension(getWidth(), ribbonTabHeight + shadowHeight));
		} else {
			setMinimumSize(new Dimension(0, ribbonHeight));
			setPreferredSize(new Dimension(getWidth(), ribbonHeight));
			setSize(new Dimension(getWidth(), ribbonHeight));
		}
		getParent().revalidate();
	}
	
	
	int getMaxLineWidth(String text) {
		String lines[] = text.split("\n");
		int w = 0;
		for (String line : lines) {
			 int l = getGraphics().getFontMetrics(font).stringWidth(line);
			 if (l>w) {
				 w = l;
			 }
		}
		return w;
	}
	

	/**
	 * build an entire menu structure
	 */
	private void buildMenu() {

		{
			toggle.setX(getWidth() - 18);
			toggle.setY(getHeight() - 18 - shadowHeight);
			toggle.setWidth(16);
			toggle.setHeight(16);
		}

		int offset_t = 0;
		// iterates over supreme items
		for (int i = 0; i < TABS.size(); i++) {
			Tab tab = TABS.get(i);
			
			int w =   getMaxLineWidth(tab.getTitle()) + (eastWestTabInset * 2);
			tab.setWidth(w);
			tab.setHeight(ribbonTabHeight);
			tab.setX(offset_t);
			tab.setY(northTabInset);
			offset_t += w;
			int offset_bx = tabLayoutWestEastMargin; 
			int offset_by = 0;
			int slim_count = 0;
			int slim_max = 0;

			// iterates over buttons
			for (int b = 0; b < tab.getButtons().size(); b++) {
				Button button = tab.getButtons().get(b);
				if (button.isSlim()) {
					int sw;
					if (button.getTitle() != null && button.getTitle().length() > 0) {
						sw = getGraphics().getFontMetrics(font).stringWidth(button.getTitle()) + (int)(26 * SCALING_FACTOR);
					} else {
						sw = (int)(22 * SCALING_FACTOR);
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
					if (slim_count%3 ==0) {
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

					//Megváltoztatjuk a szélességet a szöveg alapján
					int bw = getMaxLineWidth(button.getTitle()) + buttonLeftRightMargin * 2 ;
					button.setWidth(bw);
					button.setHeight(buttonHeight);
					button.setX(offset_bx);
					button.setY(ribbonButtonTopBase);
					offset_bx += bw + 2;
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
	public static Tab addTab(String title) {
		String gen = generateToken(8);
		TOKENS.put(gen, title);
		Tab tab = new Tab(gen);
		tab.setTitle(title);
		if (TABS.size()==0) {
			tab.setSelected( true );
		}
		TABS.add(tab);
		return tab;
	}
	
	public static Image scale(Image im, int width, int height) {
		Image b = im.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		MediaTracker tracker = new MediaTracker(new java.awt.Container());
		tracker.addImage(b, 0);
		try {
		    tracker.waitForAll();
		} catch (InterruptedException ex) {
		   Logger.getLogger(RibbonBar.class.getName()).log(Level.WARNING, null, ex);
		}
		return b;
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
	 * @param gg the Graphics context in which to paint
	 */
	@Override
	public void paint(Graphics gg) {
		Graphics2D g = (Graphics2D) gg;		 
		// set quality
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// the very first time build entire menu structure
		if (buildMenu) {
			buildMenu = false;
			buildMenu();
		}

		// Ribbon background
		g.setColor(COLORS.get(COLOR_RIBBON_BACKGROUND));
		g.fillRect(0, 0, getWidth(), getHeight());

		// Ribbon tab background
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
				if (tab.isSelected()) {
					g.setColor(COLORS.get(COLOR_RIBBON_TAB_SELECTED_BACKGROUND));
				} else {
					g.setColor(COLORS.get(COLOR_RIBBON_TAB_BACKGROUND));
				}
			}
			g.fillRect(tab.getX(), tab.getY(), tab.getWidth(), tab.getHeight());

			// Selected tab
			if (tab.isSelected()) {
				g.setColor(COLORS.get(COLOR_RIBBON_TAB_CONTAINER_STRIP));
				g.drawRect(tab.getX(), tab.getY(), tab.getWidth()-1, tab.getHeight());
				g.setColor(COLORS.get(COLOR_RIBBON_BACKGROUND));
				g.drawLine(tab.getX(), tab.getHeight(), tab.getX()+tab.getWidth(), tab.getHeight());
				
				g.setColor(COLORS.get(COLOR_RIBBON_TAB_SELECTED_STRIP_BACKGROUND));
				if (tab.isHover()) {
					g.fillRect(tab.getX(), tab.getY() + tab.getHeight() - stripHeight, tab.getWidth(), stripHeight);
				} else {
					int half = (tab.getWidth() - g.getFontMetrics().stringWidth(tab.getTitle())) / 3;
					g.fillRect(tab.getX() + half, tab.getY() + tab.getHeight() - stripHeight, tab.getWidth() - half * 2, stripHeight);
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
			g.drawString(tab.getTitle(),
					tab.getX() + tab.getWidth() / 2 - g.getFontMetrics().stringWidth(tab.getTitle()) / 2,
					tab.getY() +  (int)(20 * SCALING_FACTOR)  );

		 
			int lastSeparatorposition = 0;
			// render selected tab
			if (tab.isSelected() && !minimized) {
				{ // Group title
					g.setFont(font.deriveFont(9f * (float) SCALING_FACTOR));
					int index = 0;
					for (Button separator : tab.getSeparators()) {
						String groupname = tab.getGroupName(index);
						if (groupname != null) {
							g.setColor(COLORS.get(COLOR_RIBBON_GROUP_COLOR));
							int groupname_length = g.getFontMetrics().stringWidth(groupname);
							int west = separator.getX();
							if (index==0) {
								g.drawString( groupname, west/2-groupname_length/2, getHeight() - 6 - shadowHeight );
							} else  {
								g.drawString( groupname, lastSeparatorposition +(((west - lastSeparatorposition)/2) - groupname_length/2), getHeight() - 6 - shadowHeight );
							} 
							
						}
						
						 
						lastSeparatorposition = separator.getX();
						index++;
					}
					
					
					
					
					/*
					for (int s = 0; s < tab.getNumberOfSeparators(); s++) {
						String groupname = tab.getGroupName(s);
						if (groupname != null) {
							g.setColor(COLORS.get(COLOR_RIBBON_GROUP_COLOR));
							int groupname_length = g.getFontMetrics().stringWidth(groupname);
							int west = getWidth();
							Button sep = tab.getSeparator(s);
							if (sep != null) {
								west = sep.getX();
							}
							g.drawString(groupname,
									horizontal_offset + (west - horizontal_offset) / 2 - groupname_length / 2,
									getHeight() - 6 - shadowHeight);
							horizontal_offset += west;
						}
					}
					*/
					g.setFont(font);
				}

				// Buttons under selected tab
				for (int y = 0; y < tab.getButtons().size(); y++) {
					Button button = tab.getButtons().get(y);

					if (button.isSeparator()) {
						g.setColor(COLORS.get(COLOR_RIBBON_SEPARATOR_FOREGROUND));
						g.drawLine(button.getX() + separatorWidth / 2, button.getY() + 1,
								button.getX() + separatorWidth / 2, button.getY() + separatorHeight);
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
								
								g.setColor( COLORS.get(COLOR_RIBBON_BUTTON_HOVER_BORDER_COLOR) );
								g.setStroke(new BasicStroke(1.0f));
								
								g.drawRect(button.getX(), button.getY() + buttonPartialHeight, button.getWidth() + 1,
										button.getHeight() - buttonPartialHeight);
								
								g.drawRect(button.getX(), button.getY(), button.getWidth() + 1, buttonPartialHeight);
								
								
							} else {
								g.setColor( COLORS.get(COLOR_RIBBON_BUTTON_HOVER_BORDER_COLOR) );
								g.setStroke(new BasicStroke(1.0f));

								g.drawRect(button.getX(), button.getY(), button.getWidth() + 1, buttonPartialHeight);

								g.setColor( COLORS.get(COLOR_RIBBON_BUTTON_HOVER_BACKGROUND) );

								
								g.fillRect(button.getX(), button.getY() + buttonPartialHeight, button.getWidth() + 1,
										button.getHeight() - buttonPartialHeight);

								g.setColor( COLORS.get(COLOR_RIBBON_BUTTON_HOVER_BORDER_COLOR) );
								g.setStroke(new BasicStroke(1.0f));

								g.drawRect(button.getX(), button.getY() + buttonPartialHeight, button.getWidth() + 1,
										button.getHeight() - buttonPartialHeight);

							}

						} else {
							g.fillRect(button.getX(), button.getY(), button.getWidth(), button.getHeight());
							if (button.isHover()) {
								g.setColor( COLORS.get(COLOR_RIBBON_BUTTON_HOVER_BORDER_COLOR) );
								g.setStroke(new BasicStroke(1.0f));
								g.drawRect(button.getX(), button.getY(), button.getWidth(), button.getHeight()-1);
							}
						}

						g.setColor(COLORS.get(COLOR_RIBBON_BUTTON_FOREGROUND));
						if (button.isSlim()) {
							if (button.getImage() == null) {
								g.drawString(button.getTitle(), button.getX() + 4, button.getY() + button.getHeight() - 8);
							} else {
								
								g.drawImage(scale(button.getImage().getImage(), (int)(16*SCALING_FACTOR), (int)(16*SCALING_FACTOR)) , button.getX() + 2, button.getY() + 4,
										(int)(16 * SCALING_FACTOR), 
										(int)(16 * SCALING_FACTOR),
										this);
								
								g.drawString(button.getTitle(), button.getX() + 4 + (int)(16 * SCALING_FACTOR),
										button.getY() + button.getHeight() - (int)(8 * SCALING_FACTOR));
							}
						} else {
							if (button.hasDropDown()) {
								if (button.getImage() != null) {
									
									int image_size = (int)(BUTTON_IMAGE_SIZE * SCALING_FACTOR);
									 
									int shift = 2;
									if (button.getTitle().contains("\n")) {
										shift=4;
									}
									 
								 
									g.drawImage(scale(button.getImage().getImage(), image_size, image_size), 
											button.getX() + (button.getWidth() / 2) - image_size / 2 , 
											button.getY() + (button.getHeight() / 2) - image_size - shift , 
											image_size,
											image_size, 
											this);
									
									
									//g.drawImage(button.getImage().getImage(), button.getX() + 26, button.getY() + 6, 24,
									//		24, this);
								}
								String[] lines = button.getTitle().split("\n");
								for (int l = 0; l < lines.length; l++) {
									int w = g.getFontMetrics().stringWidth(lines[l]);
									g.drawString(lines[l], button.getX() + button.getWidth() / 2 - w / 2, button.getY()
											+ button.getHeight() - 16 + (l * 14) - (lines.length > 1 ? 10 : 0));
								}
								g.setColor(Color.GRAY);
								g.setStroke(new BasicStroke(1.3f));
								g.drawLine(button.getX() + button.getWidth() / 2 - 3,
										button.getY() + button.getHeight() - 6, button.getX() + button.getWidth() / 2,
										button.getY() + button.getHeight() - 4);
								g.drawLine(button.getX() + button.getWidth() / 2 + 3,
										button.getY() + button.getHeight() - 6, button.getX() + button.getWidth() / 2,
										button.getY() + button.getHeight() - 4);

								// Normal classic button
							} else {
								if (button.getImage() != null) {
									int image_size = (int)(BUTTON_IMAGE_SIZE * SCALING_FACTOR);
								 
									g.drawImage(scale(button.getImage().getImage(), image_size, image_size), 
											button.getX() + (button.getWidth() / 2) - image_size / 2 , 
											button.getY() + (button.getHeight() / 2) - image_size , 
											image_size,
											image_size, 
											this);
								}
								String[] lines = button.getTitle().split("\n");
								for (int l = 0; l < lines.length; l++) {
									if (l>1) {
										break;
									}
									int w = g.getFontMetrics().stringWidth(lines[l]);
									g.drawString(lines[l], button.getX() + button.getWidth() / 2 - w / 2, 
											button.getY()
											+ button.getHeight() - (int)(16*SCALING_FACTOR) + (l * (int)(14 * SCALING_FACTOR)) - (lines.length > 1 ? 10 : 0)  );
								}

							}

						}
					}
				}
			}

			// draw shadow
			if (shadowHeight > 0) {
				GradientPaint shadow_paint = new GradientPaint(0, getHeight() - shadowHeight,
						COLORS.get(COLOR_RIBBON_SHADOW_DARK), 0, getHeight(), COLORS.get(COLOR_RIBBON_SHADOW_LIGHT));
				g.setPaint(shadow_paint);
				g.fill(new Rectangle2D.Double(0, getHeight() - shadowHeight, getWidth(), getHeight()));
			}
		}
		{
			toggle.setX(getWidth() - 20 - tabLayoutWestEastMargin);
			toggle.setY(getHeight() - 18 - shadowHeight);
			toggle.setWidth(16);
			toggle.setHeight(16);
		}
		if (!minimized) {
			if (reminimized) {
				g.drawImage(pinned.getImage(), toggle.getX(), toggle.getY(), 16, 16, this);
			} else {
				g.drawImage(toggle.getImage().getImage(), toggle.getX(), toggle.getY(), 16, 16, this);							
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
	
	public static void fired() {
		if (instance!=null) {
			if (instance.reminimized) {
				instance.minimized = true;
				instance.toggle();
			}
		}
	}
	
	  @Override
	  public String getToolTipText(MouseEvent e) {
	    for (int i = 0; i < TABS.size(); i++) {
	      Tab t = TABS.get(i);
	      if (t.isSelected()) {
	        for (int j = 0; j < t.getButtons().size(); j++) {
	          Button b = t.getButtons().get(j);
	          if (!b.isSeparator()) {
	            if (b.inBounds(e.getPoint(), b.getToken())) {
	              return b.getToolTip();
	            }
	          }
	        } // end for button search
	      } // end t.isSelected()
	    } // end for tab search
	    // no tooltip
	    return null;
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
			boolean found = false;
			if (e.getPoint().y <= ribbonTabHeight) {
				
				for (int i = 0; i < TABS.size(); i++) {
					if (TABS.get(i).inBounds(e.getPoint(), TABS.get(i).getToken())) {
						found = true;
					}
				}
				if (found) {
					for (int i = 0; i < TABS.size(); i++) {
						TABS.get(i).setSelected(TABS.get(i).inBounds(e.getPoint(), TABS.get(i).getToken()));
					}
          minimized = false;
  			  toggle();
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
								if (!but.hasDropDown() || but.isHoverTop()) {
									but.fireAction(new ActionEvent(RibbonBar.this, (int) AWTEvent.MOUSE_EVENT_MASK,
											"onClick"));
								}
								if (but.hasDropDown() && !but.isHoverTop()) {
									POPUP_MENU.removeAll();
									for (int i = 0; i < but.getSubMenuList().size(); i++) {
										if (but.getSubMenuList().get(i) instanceof JMenuItem) {
											POPUP_MENU.add((JMenuItem)but.getSubMenuList().get(i));
										}
										if (but.getSubMenuList().get(i) instanceof JCheckBoxMenuItem) {
											POPUP_MENU.add((JCheckBoxMenuItem) but.getSubMenuList().get(i));
										}
										if (but.getSubMenuList().get(i) instanceof RibbonMenuItem) {
											RibbonMenuItem ri =	((RibbonMenuItem)but.getSubMenuList().get(i));
											int w = new JMenuItem(ri.getText()).getPreferredSize().width + 28 + ri.getIconTextGap();
											ri.setPreferredSize(new Dimension(w, 22)); //TODO maybe scaling issue
										}
									}
									
									POPUP_MENU.show(RibbonBar.this, but.getX(), but.getY() + but.getHeight());
									found = true;
								}
							}
						}
					}
				}
			}

			if (toggle.isBound(e.getPoint())) {
				reminimized = !reminimized;
				if (reminimized) {
				minimized = !minimized;
				}
				toggle();
				
			} else {
				if (reminimized && !found) {
					minimized = true;
					toggle();
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
