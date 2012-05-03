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
 * Simulate Trackwheel up move
 */
public class TrackwheelUpCommand implements Command {

    private int count;

    public TrackwheelUpCommand(int count) {
        this.count = count;
    }

    public Response execute() {
        UIInvoker.simulateTrackwheelUp(count);
        String message = "Trackwheel Up " + count;
        Logger.log(message);
        return new Response(message, true);
    }

}
