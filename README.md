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

 1. Try to read audio samples the examples `./gradlew execute -Pargs="-rl"`

## How to Windows

 1. Reading HID doesn't seem to work in Windows, but try setting the LED's with
 1. `gradlew execute -Pargs="-m 128 0 255 0 0"`

## Read Led Patterns

 1.  ./gradlew execute -Pargs="-rl"

## Read Audio Patterns

 1.  ./gradlew execute -Pargs="-ra"

## Manually set LED's

 1. Pass -m and 5 bytes (0-255), one for each led bar
 2. For example the following sets the first bar to 50%, the 3rd bar to 100% and turns off everything else
 2. ./gradlew execute -Pargs="-m 128 0 255 0 0"

## Play led patterns

 1. Pass -l and the pattern id (see Read Led Patterns) and the duration in seconds
 2. For example the following plays Led pattern #2 (On/Off in Firmware 2.1) for 5 seconds
 3. ./gradlew execute -Pargs="l 2 5" <- plays