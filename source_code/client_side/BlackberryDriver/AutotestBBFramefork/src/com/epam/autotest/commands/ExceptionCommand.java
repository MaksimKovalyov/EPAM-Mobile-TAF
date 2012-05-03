package com.epam.autotest.commands;

import com.epam.autotest.commands.interfaces.Command;
import com.epam.autotest.response.Response;
import com.epam.autotest.utils.Logger;

/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * Command for exception handling
 */
public class ExceptionCommand implements Command {

    private String message;
    
    public ExceptionCommand(String message) {
        this.message = message;
    }
    
    public Response execute() {
        Response result = new Response(message, false);
        Logger.log(message);
        return result;
    }

}
