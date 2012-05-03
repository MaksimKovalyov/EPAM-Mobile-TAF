package com.epam.autotest.utils;

import net.rim.device.api.ui.Field;


import com.epam.autotest.commands.DoTouchCommand;
import com.epam.autotest.commands.EscapeButtonCommand;
import com.epam.autotest.commands.ExceptionCommand;
import com.epam.autotest.commands.GetAttributeCommand;
import com.epam.autotest.commands.GetCurrentScreenNameCommand;
import com.epam.autotest.commands.GetDeviceInfoCommand;
import com.epam.autotest.commands.GetTreeCommand;
import com.epam.autotest.commands.InputTextCommand;
import com.epam.autotest.commands.MenuButtonCommand;
import com.epam.autotest.commands.PingCommand;
import com.epam.autotest.commands.ScreenshotCommand;
import com.epam.autotest.commands.SetAttributeCommand;
import com.epam.autotest.commands.TrackwheelClickCommand;
import com.epam.autotest.commands.TrackwheelDownCommand;
import com.epam.autotest.commands.TrackwheelUpCommand;
import com.epam.autotest.commands.WaitForScreenCommand;
import com.epam.autotest.commands.interfaces.Command;
import com.epam.autotest.json.me.JSONException;
import com.epam.autotest.json.me.JSONObject;


/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * JSON Command Parser. Validation integrated.
 */
public class CommandParser {

    public static Command parser(String source) {
        Command result = null;
        try {
            JSONObject jsonObj = new JSONObject(source);
            String s = jsonObj.getString("Operation").trim();

            if (s.equals("ping")) {
                result = new PingCommand();
            } else if (s.equals("doAction")) {
                try {
                    JSONObject params = jsonObj.getJSONObject("Params");
                    String action = params.getString("Action").trim();
                    
                    if (action.equals("touch")) {
                        Field field = UIEngine.findElement(params.getString("ClassNameChain"));
                        if (field == null) {
                            result = new ExceptionCommand("Wrong ClassNameChain");
                        } else {
                            result = new DoTouchCommand(field);
                        }                        
                    } else if (action.equals("keyboard")) {
                        String text = params.getString("Value");                        
                        Field field = UIEngine.findElement(params.getString("ClassNameChain"));
                        if (field == null) {
                            result = new ExceptionCommand("Wrong ClassNameChain");
                        } else {
                            result = new InputTextCommand(field, text);
                        }
                    } else if (action.equals("trackwheel_up")) {
                        int count = params.getInt("count");
                        result = new TrackwheelUpCommand(count);
                    } else if (action.equals("trackwheel_down")) {
                        int count = params.getInt("count");
                        result = new TrackwheelDownCommand(count);
                    } else if (action.equals("trackwheel_click")) {
                        result = new TrackwheelClickCommand();
                    } else if (action.equals("escape")) {
                        result = new EscapeButtonCommand();
                    } else if (action.equals("menu")) {
                        result = new MenuButtonCommand();
                    }
                } catch (JSONException e) {
                    result = new ExceptionCommand("Bad parameters for doAction operation");
                } 
            } else if (s.equals("getTree")) {
                result = new GetTreeCommand();
            } else if (s.equals("screenshot")) {
                result = new ScreenshotCommand();
            } else if (s.equals("getElementValue")) {
                try {
                    JSONObject params = jsonObj.getJSONObject("Params");
                    String attribName = params.getString("AttributeName");
                    Field field = UIEngine.findElement(params.getString("ClassNameChain"));
                    if (field == null) {
                        result = new ExceptionCommand("Wrong ClassNameChain");
                    } else {
                        result = new GetAttributeCommand(field, attribName);
                    }
                } catch (JSONException e) {
                    result = new ExceptionCommand("Bad parameters for getElementValue operation");
                }
            } else if (s.equals("setElementValue")) {
                try {
                    JSONObject params = jsonObj.getJSONObject("Params");
                    String attribName = params.getString("AttributeName");
                    String attribValue = params.getString("Value");
                    Field field = UIEngine.findElement(params.getString("ClassNameChain"));
                    if (field == null) {
                        result = new ExceptionCommand("Wrong ClassNameChain");
                    } else {
                        result = new SetAttributeCommand(field, attribName, attribValue);
                    }
                } catch (JSONException e) {
                    result = new ExceptionCommand("Bad parameters for setElementValue operation");
                }
            } else if (s.equals("getDeviceInfo")) {
                result = new GetDeviceInfoCommand();
            } else if (s.equals("getCurrentScreenName")) {
                result = new GetCurrentScreenNameCommand();
            } else if (s.equals("wait")) {
                try {
                    JSONObject params = jsonObj.getJSONObject("Params");
                    String target = params.getString("target");
                    Class.forName(target);
                    long timeout = params.getLong("timeout");
                    result = new WaitForScreenCommand(target, timeout);
                } catch (JSONException e) {
                    result = new ExceptionCommand("Bad parameters for wait operation");
                } catch (ClassNotFoundException e) {
                    result = new ExceptionCommand("Class not found exception");
                }
            } else {
                result = new ExceptionCommand("Unsupported Command");
            }
        } catch (JSONException e) {
            result = new ExceptionCommand("Invalid JSON");
        }
        return result;
    }

}
