# sirenofshame-java
Java Controller for the Siren of Shame - Uses usb4java

## Prerequisites
  Java 8 JDK Installed.
  
  You can [download Java 8 here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
## How to Linux

 1. Open the file [99-sirenusb.rules](src/main/resources/99-sirenusb.urles)
 2. Put in your user group instead of fhanik
 3. Copy the file to /etc/udev/rules.d/
 4. Unplug the Siren
 5. Run the command `udevadm trigger`
 6. Plug in the Siren
 7. Run the examples `./gradlew -PmainClass=com.hanik.usb.siren.Examples execute`
 
## How to Mac OS

 1. Run the examples `./gradlew -PmainClass=com.hanik.usb.siren.Examples execute`git st
