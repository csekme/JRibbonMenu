/**
 * Copyright 2021 Krisztián Csekme
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
import java.awt.Color;

/**
 * Encapsulate color and dimensional settings
 * @author csekme
 */
public class Theme {
	
	private Color colorRibbonBackground;
	private Color colorRibbonForeground; //global font color if other fore- colors are null
	
	private Color colorTabContainerBackground;
	private Color colorTabBackground;
	private Color colorTabForeground;
	
	
	
	private Theme() {}
	
	/**
	 * Create and load default theme 
	 * @return preconfigured theme instance
	 */
	public Theme loadDefault() {
		Theme t = new Theme();
		
		return t;
	}

	public Color getColorRibbonBackground() {
		return colorRibbonBackground;
	}

	public void setColorRibbonBackground(Color colorRibbonBackground) {
		this.colorRibbonBackground = colorRibbonBackground;
	}

	public Color getColorRibbonForeground() {
		return colorRibbonForeground;
	}

	public void setColorRibbonForeground(Color colorRibbonForeground) {
		this.colorRibbonForeground = colorRibbonForeground;
	}

	public Color getColorTabContainerBackground() {
		return colorTabContainerBackground;
	}

	public void setColorTabContainerBackground(Color colorTabContainerBackground) {
		this.colorTabContainerBackground = colorTabContainerBackground;
	}

	public Color getColorTabBackground() {
		return colorTabBackground;
	}

	public void setColorTabBackground(Color colorTabBackground) {
		this.colorTabBackground = colorTabBackground;
	}

	public Color getColorTabForeground() {
		return colorTabForeground;
	}

	public void setColorTabForeground(Color colorTabForeground) {
		this.colorTabForeground = colorTabForeground;
	}
	
	
	
}
