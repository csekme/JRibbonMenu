package hu.csekme.RibbonMenu;

/*
* Tooltip for VirtualObjects.
* 
* The ToolTip has descriptive text to help the user.
* The description can in some themes be multiple lines
* if text is separated by newlines '\n'.
* 
* +--------------------------------+
* | Description text               |
* +--------------------------------+
*/
public class ToolTip {

  private String text;
  private String description;

  public ToolTip(String text) {
     this.text = text;
  }

  public String getText() {
    return text;
  }

}