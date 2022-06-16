

module JRibbonMenu {
  requires transitive java.desktop;
  exports com.pckcs.RibbonMenu;
  requires com.kitfox.svg; 
  requires com.formdev.flatlaf;
  requires com.formdev.flatlaf.intellijthemes;
  requires com.formdev.flatlaf.extras;
  exports dist;
  exports images;
  exports images.svg;
  opens dist;
  opens images;
  opens images.svg;
}