package hu.csekme.RibbonMenu;
/*
* Tooltip for VirtualObjects.
* 
* The ToolTip has descriptive text to help the user.
* The description can be multiple lines for full size buttons
* if text is separated by newlines '\n'.
* Slim buttons do not split the lines.
* 
* +--------------------------------+
* | Description text               |
* +--------------------------------+
*/
public class ToolTip {

  private String text;
  
  public ToolTip(String text) {
     this.text = text;
  }

  public String getText() {
    return text;
  }
 
}
