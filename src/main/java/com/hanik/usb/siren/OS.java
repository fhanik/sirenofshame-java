package com.hanik.usb.siren;

public class OS {
    public static void main (String[] args) {
        String s =
            "name: " + System.getProperty ("os.name");
        s += ", version: " + System.getProperty ("os.version");
        s += ", arch: " + System.getProperty ("os.arch");
        System.out.println ("OS=" + s);
    }

    public static boolean isMac() {
        return System.getProperty ("os.name").startsWith("Mac OS");
    }

    public static boolean isLinux() {
        return System.getProperty ("os.name").startsWith("Linux");
    }

    public static boolean isWindows() {
        return System.getProperty ("os.name").startsWith("Windows");
    }
}
