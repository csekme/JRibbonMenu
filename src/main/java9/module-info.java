

module JRibbonMenu {
  requires transitive java.desktop;
  exports com.pckcs.RibbonMenu;
  exports dist;
  exports images;
  opens dist;
  opens images;
}