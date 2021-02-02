package hu.csekme.RibbonMenu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
    }

    public void setDisabledImage(ImageIcon disabledImage) {
      this.disabledImage = disabledImage;
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
