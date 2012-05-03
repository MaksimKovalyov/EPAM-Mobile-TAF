package com.epam.autotest.commands;

import com.epam.autotest.commands.interfaces.Command;
import com.epam.autotest.response.Response;
import com.epam.autotest.utils.Logger;
import com.epam.autotest.utils.UIInvoker;

/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * Simulate escape button pressed
 */
public class EscapeButtonCommand implements Command {

    private static final String ESCAPE_MESSAGE = "Escape button pressed"; 
    
    public Response execute() {
        UIInvoker.simulateBackKey();        
        Logger.log(ESCAPE_MESSAGE);
        return new Response(ESCAPE_MESSAGE, true);
    }

}
