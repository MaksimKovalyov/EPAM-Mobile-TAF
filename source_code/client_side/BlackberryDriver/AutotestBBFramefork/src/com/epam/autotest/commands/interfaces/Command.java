package com.epam.autotest.commands.interfaces;

import com.epam.autotest.response.Response;

/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * Command interface.
 */
public interface Command {
    
    public Response execute();
    
}
