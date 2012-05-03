package com.epam.autotest.commands;

import java.io.IOException;

import net.rim.device.api.io.Base64OutputStream;


import com.epam.autotest.commands.interfaces.Command;
import com.epam.autotest.json.me.JSONException;
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
 * return Screenshot in Base64 jpeg string
 */
public class ScreenshotCommand implements Command {

    public Response execute() {        
        byte[] array =  UIEngine.getScreenShot();
        
        try {                      
            String encodedStr = Base64OutputStream.encodeAsString(array, 0, array.length, false, false);            
            JSONObject jsonObj = new JSONObject();        
            jsonObj.put("data", encodedStr);
            Logger.log("Screenshot");
            return new Response(jsonObj, true);
        } catch (JSONException e) {            
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }          
        
        return new Response("Something wrong", false);
    }

}
