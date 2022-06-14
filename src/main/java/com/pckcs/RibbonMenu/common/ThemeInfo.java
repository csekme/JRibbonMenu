package com.pckcs.RibbonMenu.common;
import java.io.File;

public class ThemeInfo {
    public final String name;
    public final File themeFile;
    public final String lafClassName;

    public ThemeInfo(String name, File themeFile, String lafClassName) {
        this.name = name;
        this.themeFile = themeFile;
        this.lafClassName = lafClassName;
    }

    @Override
    public String toString() {
        return name;
    }
}
