# JRibbonMenu 2.0
### Ribbon Menu Bar implementation in Java.

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
...

```
 
## The result is something like this below (depends on OS)
 

