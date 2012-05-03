package com.epam.autotest.commands;


import com.epam.autotest.commands.interfaces.Command;
import com.epam.autotest.json.me.JSONObject;
import com.epam.autotest.response.Response;
import com.epam.autotest.utils.Logger;
import com.epam.autotest.utils.UIEngine;


/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * return basic device info
 */
public class GetDeviceInfoCommand implements Command {

    public Response execute() {
        Logger.log("Getting device info");
        JSONObject jsonObj = UIEngine.getDeviceInfo();
        Response response = new Response(jsonObj, true);
        return response;
    }

}
