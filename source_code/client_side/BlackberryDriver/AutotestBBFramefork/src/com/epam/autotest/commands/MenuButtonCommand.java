package com.epam.autotest.commands;

import com.epam.autotest.commands.interfaces.Command;
import com.epam.autotest.response.Response;
import com.epam.autotest.utils.UIInvoker;


/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * Simulate menu button.
 */
public class MenuButtonCommand implements Command {

    public Response execute() {
        UIInvoker.simulateBBKey();
        Response response = new Response("Menu key pressed", true);
        return response;
    }

}
