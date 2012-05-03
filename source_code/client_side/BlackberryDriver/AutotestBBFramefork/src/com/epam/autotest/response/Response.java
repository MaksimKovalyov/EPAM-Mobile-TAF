package com.epam.autotest.response;

import com.epam.autotest.json.me.JSONException;
import com.epam.autotest.json.me.JSONObject;

/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * Response data.
 */
public class Response {
    private boolean status;
    private Object data;

    public Response(String data, boolean status) {
        this.status = status;
        this.data = data;
    }
    
    public Response(JSONObject data, boolean status) {
        this.status = status;
        this.data = data;
    }

    public String getJsonResponse() {
        JSONObject result = new JSONObject();
        try {
            result.put("status", status);
            result.put("data", data);
        } catch (JSONException e) {            
            e.printStackTrace();
        }

        return result.toString();
    }

}
