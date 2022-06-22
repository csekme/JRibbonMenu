/**
 * Copyright 2020-2022 Csekme Krisztián
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
package com.pckcs.RibbonMenu;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;

import com.formdev.flatlaf.extras.FlatSVGIcon;

/**
 * The Class Util.
 */
public class Util {

	/**
   * Get image from resource/image folder via filename.
   *
   * @param filename
   *          desired file path to image
   * @param width
   *          specify width
   * @param height
   *          specify height
   * @return desired image
   */
    public static ImageIcon accessImageFile(String filename, int width, int height) {
    	ImageIcon i = new ImageIcon(accessImageFile(filename).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    	return i;
    }

	/**
   * Get image from resource/image folder via filename.
   *
   * @param filename
   *          desired file path to image
   * @return desired image
   */
    public static ImageIcon accessImageFile(String filename) {
		InputStream in = accessStream(filename);
    	ImageIcon imageIcon = null;
    	if (in!=null) {
    	BufferedImage im = null;
    	try {
			im = ImageIO.read(in);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (in!=null)
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	imageIcon = new ImageIcon(im);
    	}
    	return imageIcon;
    }

	/**
   * Get input stream from resource folder.
   *
   * @param filename
   *          name of the desired resource
   * @return desired file stream
   */
	public static InputStream accessStream(String filename) {
        // this is the path within the jar file
        InputStream input = Util.class.getResourceAsStream(filename);
        if (input == null) {
            // this is how we load file within editor (eg eclipse)
            input = Util.class.getClassLoader().getResourceAsStream(filename);
        }
        return input;
    }

	/**
   * Get Font from file.
   *
   * @param fontPath
   *          path of the desired font
   * @param size
   *          size of the font
   * @return desired Font instance
   */
	public static Font loadFont(String fontPath, float size) {
		InputStream is = Util.class.getResourceAsStream(fontPath);
		try {
			 Font f = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
			 return f;
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
  public static ImageIcon scaleIcon(Icon icon, DisplayState state) {
    int size = RibbonBarConstants.BUTTON_IMAGE_SIZE;
    switch (state) {
      case SLIM:
        size = RibbonBarConstants.SLIM_BUTTON_IMAGE_SIZE;
        break;
      case LARGE:
        size = RibbonBarConstants.LARGE_BUTTON_IMAGE_SIZE;
        break;
      case QUICK:
        size = RibbonBarConstants.QUICK_BUTTON_IMAGE_SIZE;
        break;
      default:
        break;
    }
    if (icon instanceof FlatSVGIcon) {
      FlatSVGIcon flaticon = null;

      String name = ((FlatSVGIcon)icon).getName();
      flaticon = new FlatSVGIcon(name, size, size);
      return flaticon;
    } else {
      Image iconImage = iconToImage(icon);
      Image resized = iconImage.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
      BufferedImage newImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

      Graphics2D g2d = newImage.createGraphics();
      g2d.drawImage(resized, 0, 0, null);
      g2d.dispose();
      return new ImageIcon(newImage);
    }
    
  }

  public static Image iconToImage(Icon icon) {
    if (icon instanceof ImageIcon) {
       return ((ImageIcon)icon).getImage();
    } 
    else {
       int w = icon.getIconWidth();
       int h = icon.getIconHeight();
       GraphicsEnvironment ge = 
         GraphicsEnvironment.getLocalGraphicsEnvironment();
       GraphicsDevice gd = ge.getDefaultScreenDevice();
       GraphicsConfiguration gc = gd.getDefaultConfiguration();
       BufferedImage image = gc.createCompatibleImage(w, h);
       Graphics2D g = image.createGraphics();
       icon.paintIcon(null, g, 0, 0);
       g.dispose();
       return image;
    }
  }

  /**
   * is a dark theme in use
   * @return true if yes
   */
  public static boolean isFlatDark() {
    try {
      return UIManager.getBoolean("laf.dark");
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Enable feature previews
   * It will put system property <code>feature.preview=true</code>
   */
  public static void enableFeatures() {
    System.setProperty("feature.preview", Boolean.toString( true ));
  }

  /**
   * is features enabled
   * @return true if enabled otherwise false
   */
  public static boolean isFeaturesEnabled() {
    return Boolean.valueOf(System.setProperty("feature.preview",Boolean.toString(false )));
  }
}
