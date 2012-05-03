package com.epam.autotest.commands;

import net.rim.device.api.ui.UiApplication;

import com.epam.autotest.commands.interfaces.Command;
import com.epam.autotest.response.Response;
import com.epam.autotest.utils.Logger;

/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * Wait while current screen not changed to target. Or time expired.
 */
public class WaitForScreenCommand implements Command {

    private static final String CURRENT_SCREEN_IS = "Current Screen is ";
    private static final String INTERUPTED_EXCEPTION = "Interupted during screen waiting";
    private static final String TIMEOUT_EXCEPTION = "Timeout during screen waiting";
    private String targetName;
    private long timeout;
    
    public WaitForScreenCommand(String targetName, long timeout) {
        this.targetName = targetName;
        this.timeout = timeout;
    }
    
    public Response execute() {
        long startTime = System.currentTimeMillis();
        Response result = null;
        
        while (System.currentTimeMillis() - startTime < timeout) {
            String className = UiApplication.getUiApplication().getActiveScreen().getClass().getName();
            if (className.equals(targetName)) {
                String message = CURRENT_SCREEN_IS + className;
                Logger.log(message);
                result = new Response(message, true);
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Logger.log(INTERUPTED_EXCEPTION);
                result = new Response(INTERUPTED_EXCEPTION, false);
            }
        }
        
        if (result == null) {
            Logger.log(TIMEOUT_EXCEPTION);
            result = new Response(TIMEOUT_EXCEPTION, false);
        }
        
        return result;
    }

}
