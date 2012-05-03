package com.epam.autotest.commands;

import com.epam.autotest.commands.interfaces.Command;
import com.epam.autotest.response.Response;
import com.epam.autotest.utils.Logger;
import com.epam.autotest.utils.UIInvoker;

import net.rim.device.api.ui.Field;

/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * Command for simulation field touch.
 */
public class DoTouchCommand implements Command {

    private Field target;
    
    public DoTouchCommand(Field field) {
        target = field;
    }
    
    public Response execute() {
        UIInvoker.simulateTouch(target);
        String message = "Field " + target.getClass().toString()+" touched";
        Logger.log(message);
        Response response = new Response(message, true);        
        return response;
    }

}
