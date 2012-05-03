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
 * Return UI Tree via JSON representation
 */
public class GetTreeCommand implements Command {
    
    public Response execute() {
        Logger.log("Getting tree");
        JSONObject data = UIEngine.getJSONTree();
        Response response = new Response(data, true);
        return response;
    }

}
