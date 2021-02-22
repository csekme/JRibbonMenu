/**
 * Copyright 2020 Csekme Krisztián
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
/**
 * 
 * @author Krisz
 *
 */
public class Tab extends VirtualObject implements Iterable<Button> {

	List<Button> buttons;
	Map<Integer, String> groups;
	int separators;
	boolean enabled = true;
	
	public Tab(String token) {
		super(token);
		this.groups = new HashMap<>();
		this.buttons = new ArrayList<>();
		this.separators = 0;
	}
	
	/***
	 * Button is a mid level menu item
	 * @param title caption of button
	 * @return currently created Button
	 */
	public Button addButton(String title) {
		String gen = RibbonBar.generateToken(8);
		Button button = new Button(gen);
		button.setTitle(title);
		buttons.add(button);
		return button;
	}
	
	/***
	 * Button is a mid level menu item
	 * @param title caption of slim button
	 * @return currently created Button
	 */
	public Button addSlimButton(String title) {
		String gen = RibbonBar.generateToken(8);
		Button button = new Button(gen);
		button.setSlim(true);
		button.setTitle(title);
		buttons.add(button);
		return button;
	}
	
	/**
	 * add seperator
	 */
	public void addSeperator() {
		String gen = RibbonBar.generateToken(8);
		Button button = new Button(gen);
		button.createSeparator();
		buttons.add(button);
		separators++;
	}
	
	/**
	 * Get separators (TODO: need a dedicated class for separators 
	 * @return
	 */
	public List<Button> getSeparators() {
		List<Button> sep = new ArrayList<Button>();
		for (int i=0; i<buttons.size(); i++) {
			Button b = buttons.get(i);
			if (b.isSeparator()) {
				sep.add(b);
			} 
		}
		return sep;
	}
	
	
	public Button getSeparator(int index) {
		if (separators==0 && separators>index-1) {
			return null;
		}
		int ind=0;
		for (int i=0; i<buttons.size(); i++) {
			Button b = buttons.get(i);
			if (b.isSeparator()) {
				if (ind==index)return b;
				ind++;
			} 
		}
		return null;
	}
	
	public void setGroupName( String name ) {
		groups.put(separators, name);
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}
	
	public String getGroupName(int index) {
		return this.groups.get(index);
	}
/*
	public List<String> getGroups() {
		return groups;
	}
  */
	public int getNumberOfSeparators() {
		return separators;
	}

	@Override
	public Iterator<Button> iterator() {
		return buttons.iterator();
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
		return enabled;
	}

    /**
     * Sets the enabled state of the object.
     *
     * @param enabled if true, enables this object; otherwise, disables it
     */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
 
}
