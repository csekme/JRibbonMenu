package hu.csekme.RibbonMenu;

import java.awt.Color;
import java.awt.Point;
import javax.swing.ImageIcon;

/**
 * Virtual object in two dimensional space. Used for Swing drawing.
 * Describes a visible object with necessary properties.
 *
 * @see java.awt.Point
 * @see Bound
 */
public class VirtualObject extends Bound {

    /**
     * Unique text for identifier
     */
    String token;

    /**
     * In case the object is an image
     */
    ImageIcon image;

    /**
     *
     */
    Integer integerValue;

    public Color ForegroundColor;
    public Color BackgroundColor;
    public String title;

    public boolean hover;
    public boolean hoverTop;
    public boolean selected;
    public boolean selectedTop;


    public VirtualObject(String token) {
        this.ForegroundColor = new Color(0.0f, 0.0f, 0.0f, 0.0f);
        this.BackgroundColor = new Color(0.0f, 0.0f, 0.0f, 0.0f);
        this.token = token;
        this.hover = false;
    }

    /**
     * Tell that point obtained in the parameter is part of the object
     * @param p as coordinate x,y
     * @param token based on the token you are looking for
     * @return true if part of it, otherwise false
     * @see java.awt.Point
     */
    public boolean inBounds(Point p, String token) {

        if ((p.x > getX()) && (p.x < (getX() + getWidth())) && (p.y > getY()) && (p.y < (getY() + getHeight())) && this.token.equals(token)) {
            return true;
        }
        return false;
    }


    public boolean inBoundsPartOf(Point p, int fromTheTop, String token) {
        if ((p.x > getX()) && (p.x < (getX() + getWidth())) && (p.y > getY() + fromTheTop) && (p.y < (getY() + getHeight())) && this.token.equals(token)) {
            return false;
        }
        return true;
    }


    public Integer getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(Integer integerValue) {
        this.integerValue = integerValue;
    }

    public Color getForegroundColor() {
        return ForegroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        ForegroundColor = foregroundColor;
    }

    public Color getBackgroundColor() {
        return BackgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        BackgroundColor = backgroundColor;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelectedTop() {
        return selectedTop;
    }

    public void setSelectedTop(boolean selectedTop) {
        this.selectedTop = selectedTop;
    }

    public boolean isHoverTop() {
        return hoverTop;
    }

    public void setHoverTop(boolean hoverTop) {
        this.hoverTop = hoverTop;
    }

}
