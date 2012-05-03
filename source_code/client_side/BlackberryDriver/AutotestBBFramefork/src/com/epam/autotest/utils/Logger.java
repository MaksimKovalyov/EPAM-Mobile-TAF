package com.epam.autotest.utils;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.ui.component.Dialog;

/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * Simple logger. Create log on device strore. 
 * P.S. Be careful. In long test log file can eat many space.
 */

public class Logger {        
    
    private static final String LOG_FILE = "TAF_LOG.txt";
    private static final SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss:SSS");
    
    public static synchronized void log(String message) {        
        try {            
            FileConnection fc = (FileConnection) Connector.open("file:///store/" + LOG_FILE, Connector.READ_WRITE);            
            
            if (!fc.exists()) {
                fc.create();
            }
            int fileSize = (int)fc.fileSize();
            OutputStream os = null;
            if (fileSize > 0) {               
               os = fc.openOutputStream(fileSize);
            } else {
               os = fc.openOutputStream();
            }
            PrintStream printer = new PrintStream(os);
            StringBuffer buffer = new StringBuffer();
            buffer.append("[");
            buffer.append(dateFormater.formatLocal(System.currentTimeMillis()));
            buffer.append("] ");
            buffer.append(message);
            printer.println(buffer.toString());
            printer.close();            
            os.close();
            fc.close();
        } catch (Exception e) {
            Dialog.alert("Something bad...");
            e.printStackTrace();
        }
    }
    
}
