package hu.csekme.RibbonMenu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
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
    
    private ToolTip tooltip;

    public Button(String token) {
        super(token);
        this.slim = false;
        this.separator = false;
        this.actions = new ArrayList<>();
        this.subMenu = new ArrayList<>();
        this.pressed = false;
        this.tooltip = null;
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

    public void addActionListener(ActionListener a) {
        actions.add(a);
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

    public void fireAction(ActionEvent e) {
        for (ActionListener a : actions) {
            a.actionPerformed(e);
        }
    }

}
