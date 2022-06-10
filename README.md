# JRibbonMenu

## Ribbon Menu Bar implementation in Java.

### Version 2

Now supports FlatLaf Themes, High DPI Displays using FlatSVGIcons, FlatLaf-Extra components, and a new QuickAccessBar.

### Version 1.06

Converted to a Multi-Release JAR (MRJAR). It now supports Java 1.8 without any changes and Java 9+ as module or non-module jar.

![](md/img1.png)

## Installation
The project can be build using Maven. The result will be appear in target directory as the name of **RibbonMenu-<version>.jar**.
You will need to use Java 9 or above to have a successful build. Even if your target usage is Java 1.8.

```sh
#usage
mvn clean package
```
Create runnable sample program
```sh
#usage
mvn clean install
```


## Usage

If you are using Java 9 or above and your application is Modular you must export and open any resource directories inside your
module-info.java class or JRibbonMenu will be denied access to your images.  

From our example application "Program.java":
```
module JRibbonMenu {
  requires transitive java.desktop;
  exports hu.csekme.RibbonMenu;
  exports dist;
  exports images;
  opens dist;
  opens images;
}
```

NOTE: You don't need to add dist or images to your
application's module-info.java just your own resource directories
that have images you are passing on to JRibbonMenu.

Here is an example how to use library.

```java
// Create RibbonBar instance (singleton)
        RibbonBar ribbonBar = new RibbonBar();
// Add it to NORTH   
        getContentPane().add(ribbonBar, BorderLayout.NORTH);
// Add our first tab
        Tab tab1 = ribbonBar.addTab("Home");
        tab1.setGroupName("Group one");

        // Add button to first tab
        Button agenda = tab1.addButton("Agenda");
        agenda.setImage(Util.accessImageFile("agenda.png"));

        // create menuitem for button
        RibbonMenuItem r1 = new RibbonMenuItem("Create new date", Util.accessImageFile("newDate.png"));
        agenda.addSubMenu(r1);
        // create some more quickly
        agenda.addSubMenu(new RibbonMenuItem("Clear all"));
        agenda.addSubMenu(new RibbonMenuItem("Reminders"));
        // create selectable
        agenda.addSubMenu(new RibbonMenuItem("Forward to mail", true));

        // add some mini buttons without caption
        Button flag_1 = tab1.addSlimButton("");
        flag_1.setImage(Util.accessImageFile("flag.png"));
        Button flag_2 = tab1.addSlimButton("");
        flag_2.setImage(Util.accessImageFile("flag-1.png"));
        Button flag_3 = tab1.addSlimButton("");
        flag_3.setImage(Util.accessImageFile("hourglass.png"));

        // Add seperator (this means the end of the previous group)
        tab1.addSeperator();
        // Set new group name
        tab1.setGroupName("Group two");

        Button calendar = tab1.addButton("Calendar");
        calendar.setImage(Util.accessImageFile("calendar.png"));

        Button finance = tab1.addSlimButton("Financial pivots");
        finance.setImage(Util.accessImageFile("percent.png"));

        Button settings = tab1.addButton("Settings");
        settings.setImage(Util.accessImageFile("controls.png"));
        tab1.addSeperator();

        Button documents = tab1.addButton("Documents");
        documents.setImage(Util.accessImageFile("folder-9.png"));

        Button help = tab1.addButton("Help");
        help.setImage(Util.accessImageFile("idea.png"));

        Tab dataHandling = ribbonBar.addTab("Data handling");

        Button shares = dataHandling.addButton("Shares");
        shares.setImage(Util.accessImageFile("share-1.png"));
        dataHandling.addSeperator();

        Button filter = dataHandling.addButton("Apply filter");
        filter.setImage(Util.accessImageFile("filter.png"));
        filter.addSubMenu( (event)->{ JOptionPane.showMessageDialog(null, "You pressed");  }, "filter one" );
        filter.addSubMenu( (event)->{ JOptionPane.showMessageDialog(null, "You pressed");  }, "filter two" );

        Tab developing = ribbonBar.addTab("Developing");
        Button server = developing.addButton("Servers");
        server.setImage(Util.accessImageFile("server-1.png"));
   


```
 
## The result is something like this below (depends on OS)
![](md/screenshot.png)
 

 <hr/>


## Please donate free coffee here
[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.me/csekme)
