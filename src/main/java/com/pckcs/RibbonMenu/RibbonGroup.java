package com.pckcs.RibbonMenu;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.plaf.UIResource;

/**
 * The Class RibbonGroup.
 *
 * @author Paul Conti
 */
public class RibbonGroup extends JComponent {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;


    /**
     * The components.
     */
    List<JComponent> components;

    /**
     * Display state for components.
     */
    List<DisplayState> displayState;

    /**
     * The title.
     */
    private String title = null;

    /** The separators. */
//	int separators;

    /**
     * Instantiates a new group.
     */
    public RibbonGroup() {
        this.components = new ArrayList<>();
        this.displayState = new ArrayList<>();
        RibbonGroupLayout layout = new RibbonGroupLayout(this);
        this.setLayout(layout);

        addPropertyChangeListener(layout);

        updateUI();
    }

    /**
     * Instantiates a new group.
     *
     * @param title the title
     */
    public RibbonGroup(String title) {
        this.title = title;
        this.components = new ArrayList<>();
        this.displayState = new ArrayList<>();
    }

    /**
     * getTitle.
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * addComponent.
     *
     * @param comp  the comp
     * @param state the state
     */
    public void addComponent(JComponent comp, DisplayState state) {
        this.add(comp);
        displayState.add(state);
        if (comp instanceof AbstractButton) {
            AbstractButton ab = (AbstractButton)comp;
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

    }

    /**
     * Add default separator
     * @see RibbonSeparator
     */
    public void addSeparator() {
        addComponent(new RibbonSeparator(), DisplayState.SEPARATOR);
    }

    /**
     * Add custom separator
     * @param separator
     * @see RibbonSeparator
     */
    public void addSeparator(RibbonSeparator separator) {
        addComponent(separator, DisplayState.SEPARATOR);
    }

    /**
     * paintComponent.
     *
     * @param g the g
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Component[] components = this.getComponents();
        for (Component compo : components) {
            String componentName = compo.getClass().getName();
            if (componentName.endsWith("JButton")) {
                componentName = ((JButton) compo).getText();
             //   System.out.println(componentName);
            } else {
//        compo.paintComponent(g);
             //   System.out.println(compo.getClass().getName().substring(componentName.indexOf("swing.") + "swing.".length(),componentName.length()));
            }
        }
    }

    /**
     * The Class RibbonGroupLayout.
     */
    private class RibbonGroupLayout implements LayoutManager2, PropertyChangeListener, UIResource {

        /**
         * The layout for quickbar.
         */
        BoxLayout lm;

        /**
         * Instantiates a new quick bar layout.
         *
         * @param group the group
         */
        RibbonGroupLayout(RibbonGroup group) {
            lm = new BoxLayout(group, BoxLayout.LINE_AXIS);
        }

        /**
         * addLayoutComponent.
         *
         * @param name the name
         * @param comp the comp
         * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String,
         * java.awt.Component)
         */
        @Override
        public void addLayoutComponent(String name, Component comp) {
            lm.addLayoutComponent(name, comp);
        }

        /**
         * removeLayoutComponent.
         *
         * @param comp the comp
         * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
         */
        @Override
        public void removeLayoutComponent(Component comp) {
            lm.removeLayoutComponent(comp);
        }

        /**
         * preferredLayoutSize.
         *
         * @param parent the parent
         * @return the <code>dimension</code> object
         * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
         */
        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return lm.preferredLayoutSize(parent);
        }

        /**
         * minimumLayoutSize.
         *
         * @param parent the parent
         * @return the <code>dimension</code> object
         * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
         */
        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return lm.minimumLayoutSize(parent);
        }

        /**
         * layoutContainer.
         *
         * @param parent the parent
         * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
         */
        @Override
        public void layoutContainer(Container parent) {
            lm.layoutContainer(parent);
        }

        /**
         * propertyChange.
         *
         * @param evt the evt
         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            // TODO Auto-generated method stub

        }

        /**
         * addLayoutComponent.
         *
         * @param comp        the comp
         * @param constraints the constraints
         * @see java.awt.LayoutManager2#addLayoutComponent(java.awt.Component,
         * java.lang.Object)
         */
        @Override
        public void addLayoutComponent(Component comp, Object constraints) {
            lm.addLayoutComponent(comp, constraints);
        }

        /**
         * maximumLayoutSize.
         *
         * @param target the target
         * @return the <code>dimension</code> object
         * @see java.awt.LayoutManager2#maximumLayoutSize(java.awt.Container)
         */
        @Override
        public Dimension maximumLayoutSize(Container target) {
            return lm.maximumLayoutSize(target);
        }

        /**
         * getLayoutAlignmentX.
         *
         * @param target the target
         * @return the layout alignment X
         * @see java.awt.LayoutManager2#getLayoutAlignmentX(java.awt.Container)
         */
        @Override
        public float getLayoutAlignmentX(Container target) {
            return lm.getLayoutAlignmentX(target);
        }

        /**
         * getLayoutAlignmentY.
         *
         * @param target the target
         * @return the layout alignment Y
         * @see java.awt.LayoutManager2#getLayoutAlignmentY(java.awt.Container)
         */
        @Override
        public float getLayoutAlignmentY(Container target) {
            return lm.getLayoutAlignmentY(target);
        }

        /**
         * invalidateLayout.
         *
         * @param target the target
         * @see java.awt.LayoutManager2#invalidateLayout(java.awt.Container)
         */
        @Override
        public void invalidateLayout(Container target) {
            lm.invalidateLayout(target);
        }
    }


    /**
     * setLayout.
     *
     * @param mgr the new layout
     * @see java.awt.Container#setLayout(java.awt.LayoutManager)
     */
    public void setLayout(LayoutManager mgr) {
        LayoutManager oldMgr = getLayout();
        if (oldMgr instanceof PropertyChangeListener) {
            removePropertyChangeListener((PropertyChangeListener) oldMgr);
        }
        super.setLayout(mgr);
    }

}
