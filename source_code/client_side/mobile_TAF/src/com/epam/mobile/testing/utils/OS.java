package com.epam.mobile.testing.utils;

public enum OS
{
    WINDOWS_9, WINDOWS_NT, WINDOWS_2000, WINDOWS_XP, 
    LINUX, RED_HAT, UBUNTU, MAC_OS_X,
    NOVALUE;

    public static OS toOS(String str)
    {
        try {
            return valueOf(str.toUpperCase());
        } 
        catch (Exception ex) {
            return NOVALUE;
        }
    }   
}
