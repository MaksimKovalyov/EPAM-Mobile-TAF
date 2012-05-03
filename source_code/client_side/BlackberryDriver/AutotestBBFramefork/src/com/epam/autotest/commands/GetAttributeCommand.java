package com.epam.autotest.commands;

import net.rim.device.api.ui.Field;

import com.epam.autotest.commands.interfaces.Command;
import com.epam.autotest.response.Response;
import com.epam.autotest.utils.AttributeManager;
import com.epam.autotest.utils.Logger;

/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * Command for getting attribute
 */
public class GetAttributeCommand implements Command {

    private static final String ATTRIBUTE_NOT_FOUND = "Attribute not found";
    private Field target;
    private String attribName;
    
    public GetAttributeCommand(Field target, String attribName) {
        this.target = target;
        this.attribName = attribName;
    }
    
    public Response execute() {
        Response response = null; 
        
        String result = AttributeManager.getAttribute(target, attribName);
        
        if (result == null) {
            Logger.log(ATTRIBUTE_NOT_FOUND);
            response = new Response(ATTRIBUTE_NOT_FOUND, false);
        } else {
            Logger.log("Getting attribute "+attribName+" with value = " + result);
            response = new Response(result, true);
        }
        
        return response;
    }

}
