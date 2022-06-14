

module JRibbonMenu {
  requires transitive java.desktop;
  exports com.pckcs.RibbonMenu;
  requires com.formdev.flatlaf;
  requires com.formdev.flatlaf.intellijthemes;
  exports dist;
  exports images;
  opens dist;
  opens images;
}