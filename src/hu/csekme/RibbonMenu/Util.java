package hu.csekme.RibbonMenu;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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

public class Util {

	/**
	 * Get image from resource/image folder via filename
	 * @param filename
	 * @return desired image
	 */
    public static ImageIcon accessImageFile(String filename) {
    	InputStream in = accessStream("images/" + filename);
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
    	ImageIcon imageIcon = new ImageIcon(im);
    	 
    	return imageIcon;
    }

	/**
	 * Get input stream from resource folder
	 * @param filename
	 * @return desired file stream
	 */
	public static InputStream accessStream(String filename) {
        // this is the path within the jar file
        InputStream input = Util.class.getResourceAsStream("/resources/" + filename);
        if (input == null) {
            // this is how we load file within editor (eg eclipse)
            input = Util.class.getClassLoader().getResourceAsStream("/resources/" + filename);
        }
        return input;
    }

	/**
	 * Get Font from file
	 * @param fontPath
	 * @param size
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
}
