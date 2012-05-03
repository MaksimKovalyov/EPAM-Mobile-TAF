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
 * Return Current Screen Name
 */
public class GetCurrentScreenNameCommand implements Command {

    public Response execute() {
        String className = UiApplication.getUiApplication().getActiveScreen().getClass().getName();        
        Logger.log("Screen name " + className);
        return new Response(className, true);
    }

}
