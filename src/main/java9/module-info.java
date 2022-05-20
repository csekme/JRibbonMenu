

module JRibbonMenu {
  requires transitive java.desktop;
  exports hu.csekme.RibbonMenu;
  exports dist;
  exports images;
  opens dist;
  opens images;
}