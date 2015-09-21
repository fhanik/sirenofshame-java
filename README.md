# sirenofshame-java
Java Controller for the Siren of Shame - Uses usb4java

## Prerequisites
  Java 8 JDK Installed.
  
  You can [download Java 8 here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
## How to Linux

 1. Try the examples `./gradlew -PmainClass=com.hanik.usb.siren.Examples execute` if you get an Access Denied then
 2. Open the file [99-sirenusb.rules](src/main/resources/99-sirenusb.rules)
 3. Put in your user group instead of fhanik
 4. Copy the file to /etc/udev/rules.d/
 5. Unplug the Siren
 6. Run the command `udevadm trigger`
 7. Plug in the Siren
 8. Run the examples `./gradlew -PmainClass=com.hanik.usb.siren.Examples execute`
 
## How to Mac OS

 1. Run the examples `./gradlew -PmainClass=com.hanik.usb.siren.Examples execute`

## How to Windows

 1. Run the examples `gradlew -PmainClass=com.hanik.usb.siren.Examples execute`

## Read Led Patterns

 1.  ./gradlew -PmainClass=com.hanik.usb.siren.examples.ReadLedPatterns execute