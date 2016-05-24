# sirenofshame-java
Java Controller for the Siren of Shame - Uses usb4java

## Prerequisites
  Java 8 JDK Installed.

  You can [download Java 8 here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
## How to Linux

 1. Try to read info from device `./gradlew run -Pargs="-i"` if you get an Access Denied then
 2. Open the file [99-sirenusb.rules](src/main/resources/99-sirenusb.rules)
 3. Put in your user group instead of sirenofshame
 4. Copy the file to /etc/udev/rules.d/
 5. Unplug the Siren
 6. Run the command `udevadm trigger`
 7. Plug in the Siren
 8. Run the examples `./gradlew run -Pargs="-i"`
 
## How to Mac OS

 1. Try to read device info `./gradlew run -Pargs="-i"`

## How to Windows

 1. Try to read device info `./gradlew run -Pargs="-i"`

# Commands

## Read Device Info

 1.  ./gradlew run -Pargs="-i"

## Read Led Patterns

 1.  ./gradlew run -Pargs="-rl"

## Read Audio Patterns

 1.  ./gradlew run -Pargs="-ra"

## Manually set LED's

 1. Pass -m and 5 bytes (0-255), one for each LED bar
 2. For example the following sets the first bar to 50%, the 3rd bar to 100% and turns off everything else
 3. ./gradlew run -Pargs="-m 128 0 255 0 0"

## Play led patterns

 1. Pass -l and the pattern id (see Read Led Patterns) and the duration in seconds
 2. For example the following plays Led pattern #2 (On/Off in Firmware 2.1) for 5 seconds
 3. ./gradlew run -Pargs="-l 2 5"

## Play audio patterns

  1. Pass -a and the pattern id (see Read Audio Patterns) and the duration in seconds
  2. For example the following plays Audio pattern #1 (Sad Trombone in Firmware 2.1) for 5 seconds
  3. ./gradlew run -Pargs="-a 1 5"

## Stop led pattern
 1. Pass -sl to stop long running led patterns
 2. ./gradlew run -Pargs="-sl"

## Stop audio pattern
  1. Pass -sa to stop long running audio patterns
  2. ./gradlew run -Pargs="-sa"