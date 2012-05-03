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
 * Simulate trackwheel click
 */
public class TrackwheelClickCommand implements Command {

    private static final String TRACKWHEEL_CLICKED = "Trackwheel Clicked";

    public Response execute() {
        UIInvoker.simulateTrackwheelClick();
        Logger.log(TRACKWHEEL_CLICKED);
        return new Response(TRACKWHEEL_CLICKED, true);
    }

}
