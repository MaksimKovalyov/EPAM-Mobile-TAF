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
 * Setting attribute
 */
public class SetAttributeCommand implements Command {

    private static final String WRONG_ATTRIBUTE_NAME = "Wrong attribute name";
    private static final String ATTRIBUTE_NOT_FOUND = "Attribute not found";
    private Field target;
    private String attribName;
    private String attribValue;
    
    public SetAttributeCommand(Field target, String attribName, String attribValue) {
        this.target = target;
        this.attribName = attribName;
        this.attribValue = attribValue;
    }
    
    public Response execute() {
        Response response = null;
        
        try {
            if (AttributeManager.setAttribute(target, attribName, attribValue)) {
                String message = "Attribute " + attribName + " set to " + attribValue;
                Logger.log(message);
                response = new Response(message, true);    
            } else {
                Logger.log(ATTRIBUTE_NOT_FOUND);
                response = new Response(ATTRIBUTE_NOT_FOUND, false);
            }            
        } catch(NumberFormatException e) {
            Logger.log(WRONG_ATTRIBUTE_NAME);
            response = new Response(WRONG_ATTRIBUTE_NAME, false);
        }
        
        return response;
    }

}
