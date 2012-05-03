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
 * Just a ping=)
 */
public class PingCommand implements Command {

    public Response execute() {
        Logger.log("Pinging");
        return new Response("", true);
    }

}
