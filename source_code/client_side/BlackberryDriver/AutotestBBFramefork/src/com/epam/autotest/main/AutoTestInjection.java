package com.epam.autotest.main;

/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * Class for ServerThread initialization. 
 * 
 * PS. For integration with your project export all package "com.epam.autotest.*" to jar and add lib to project.
 * 
 */
public class AutoTestInjection {    
    public static void start() {
        new ServerThread().start();
    }
}
