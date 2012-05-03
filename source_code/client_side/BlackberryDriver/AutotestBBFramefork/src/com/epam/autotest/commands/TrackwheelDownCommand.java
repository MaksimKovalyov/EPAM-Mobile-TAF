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
 * Simulate trackwheel down move
 */
public class TrackwheelDownCommand implements Command {

    private int count;

    public TrackwheelDownCommand(int count) {
        this.count = count;
    }

    public Response execute() {
        UIInvoker.simulateTrackwheelDown(count);
        String message = "Trackwheel Down " + count;
        Logger.log(message);
        return new Response(message, true);
    }

}
