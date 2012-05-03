package com.epam.autotest.commands;

import com.epam.autotest.commands.interfaces.Command;
import com.epam.autotest.response.Response;
import com.epam.autotest.utils.Logger;
import com.epam.autotest.utils.UIInvoker;

import net.rim.device.api.ui.Field;

/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * Simulate input to field
 */
public class InputTextCommand implements Command {

    private Field target;
    private String text;

    public InputTextCommand(Field target, String text) {
        this.target = target;
        this.text = text;
    }

    public Response execute() {
        UIInvoker.simulateTouch(target);
        UIInvoker.simulateTextInput(text);
        String message = "Input to " + target.getClass().getName() + " field.";
        Logger.log(message);
        Response response = new Response(message, true);
        return response;
    }

}
