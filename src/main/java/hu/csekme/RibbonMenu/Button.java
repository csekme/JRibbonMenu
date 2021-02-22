package hu.csekme.RibbonMenu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
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

public class Button extends VirtualObject {

    private boolean slim;
    private boolean separator;
    private List<ActionListener> actions;
    private List<Object> subMenu;
    private boolean pressed;

    private boolean enabled;
     
    /** The tooltip, if any */
    private ToolTip tooltip;

   /** Optional image to display of button not enabled */
    private ImageIcon disabledImage;
    
    /** The action command string fired by the button. */
    private String actionCommand = null;


    public Button(String token) {
        super(token);
        
        this.slim = false;
        this.separator = false;
        this.actions = new ArrayList<>();
        this.subMenu = new ArrayList<>();
        this.pressed = false;
        this.tooltip = null;

        this.disabledImage = null;
        this.enabled = true;

    }

    public void createSeparator() {
        this.separator = true;
    }
    
    public boolean isSlim() {
        return slim;
    }

    public void setSlim(boolean slim) {
        this.slim = slim;
    }

    public boolean isSeparator() {
        return separator;
    }


    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    /**
     * Sets the enabled state of the object.
     *
     * @param b if true, enables this object; otherwise, disables it
     */
    public void setEnabled(boolean enabled) {
      this.setReloadIcons(true);	//this will refresh cached container
      this.enabled = enabled;
    }
    
    /**
     * Determines whether this component is enabled. An enabled component
     * can respond to user input and generate events. Components are
     * enabled initially by default. A component may be enabled or disabled by
     * calling its <code>setEnabled</code> method.
     * @return <code>true</code> if the component is enabled,
     *          <code>false</code> otherwise
     * @see #setEnabled
     */
    public boolean isEnabled() {
    	return this.enabled;
    }

    public void setDisabledImage(ImageIcon disabledImage) {
      this.disabledImage = disabledImage;
    }
  
    /**
     * In case of ClassCastException like
     * sun.awt.image.ToolkitImage cannot be cast to java.awt.image.BufferedImage
     * @param image as source
     * @return converted BufferedImage
     */
	public static BufferedImage convertToBufferedImage(Image image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return newImage;
	}
    
    /**
     * Convert ImageIcon to grayscale keep alpha chanel
     * @param image as original ImageIcon
     * @return image as grayscaled ImageIcon
     */
    private static ImageIcon convertToGrayScale(ImageIcon image) {
    	BufferedImage source = null;
    	try {
    		//explicit type conversion
    		source = (BufferedImage)image.getImage();
    	} catch (ClassCastException err) {
    		//in case of failure try to convert
    		source = convertToBufferedImage(image.getImage());
    	}
    	
    	if (source == null) {
    		return image;
    	}
    	BufferedImage img = new BufferedImage(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
    	for (int x=0; x<img.getWidth(); x++) {
    		for (int y=0; y<img.getHeight(); y++) {
    	
    			int p = source.getRGB(x,y); //get pixel
    			int a = (p>>24)&0xff; //alpha chanel
    			int r = (p>>16)&0xff; //red chanel
    			int g = (p>>8)&0xff; //green chanel
    			int b = p&0xff; // blue chanel
    			// https://en.wikipedia.org/wiki/Grayscale
    			// use luma coding
    			int avg = (int)(r * 0.299) + (int)(g * 0.587) + (int)(b * 0.114);
    			//use 50% transparency on alpha channel  
    			p = ((int)(a*.5)<<24) | (avg<<16) | (avg<<8) | avg;
    			// set new pixel
    			img.setRGB(x, y, p);	
    	
    		} //end for button width
    	} //end for button height
    	
    	return new ImageIcon(img);
    }
    
    /**
     * Button image
     * @param image as ImageIcon
     */
    @Override
    public void setImage(ImageIcon image) {
        super.setImage(image);
        
        //If no default disabled image create it from original image
        if (disabledImage==null) {
        	disabledImage = convertToGrayScale(image);
        }
    }

   @Override
    public ImageIcon getImage() {
      if (!enabled) {
        return disabledImage;
      }
      return super.getImage();
    }

    public void addActionListener(ActionListener a) {
        actions.add(a);
    }

    public void setActionCommand(String actionCommand) {
      this.actionCommand = actionCommand;
    }

    public String getActionCommand() {
      return actionCommand;
    }

    public void addSubMenu(JMenuItem a) {
        a.setForeground(Color.DARK_GRAY);
        subMenu.add(a);
    }

    public void addSubMenu(JCheckBoxMenuItem a) {
        subMenu.add(a);
    }


    public void addSubMenu(ActionListener a, String caption) {
        RibbonMenuItem m = new RibbonMenuItem(caption);
        m.addActionListener(a);
        subMenu.add(m);
    }

    public void addToolTip(String text) {
      this.tooltip = new ToolTip(text);
    }
    
    public String getToolTip() {
      if (tooltip==null)
        return null;
      return this.tooltip.getText();
    }
    
    public List<Object> getSubMenuList() {
        return subMenu;
    }

    public boolean hasDropDown() {
        return subMenu.size() == 0 ? false : true;
    }

    public void fireAction(ActionEvent event) {
      if (enabled) {
        ActionEvent e = event;
        if (actionCommand != null) {
          e = new ActionEvent(this,
              ActionEvent.ACTION_PERFORMED,
              actionCommand,
              event.getWhen(),
              event.getModifiers());
        }
        for (ActionListener a : actions) {
           a.actionPerformed(e);
        }
      }
    }

}
