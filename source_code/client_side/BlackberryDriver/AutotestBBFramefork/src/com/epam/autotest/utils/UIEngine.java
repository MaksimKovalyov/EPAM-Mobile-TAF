package com.epam.autotest.utils;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.Display;
import net.rim.device.api.system.JPEGEncodedImage;
import net.rim.device.api.system.Memory;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;

import com.epam.autotest.json.me.JSONArray;
import com.epam.autotest.json.me.JSONException;
import com.epam.autotest.json.me.JSONObject;

/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * Class for represent UI tree and find UI element via XPath like script.
 */
public class UIEngine {

    private static int[] parseXPath(String xpath) {
        int[] result = null;
        int count = 0;
        for (int i = 0; i < xpath.length(); i++) {
            if (xpath.charAt(i) == '/') {
                count++;
            }
        }

        result = new int[count];
        
        try {
            for (int i = 0; i < count; i++) {
                xpath = xpath.substring(xpath.indexOf("/[") + 2);
                result[i] = Integer.parseInt(xpath.substring(0, xpath.indexOf("]")));
            }
        } catch (Exception e) {
            return new int[0];
        }
        return result;
    }

    private static JSONObject getTree(Field field, String xpath) {
        JSONObject result = new JSONObject();

        try {
            result.put("ClassName", field.getClass().getName());
            result.put("ClassNameChain", xpath);
            if (field instanceof Manager) {
                Manager manager = (Manager) field;

                JSONArray childrens = new JSONArray();

                for (int i = 0; i < manager.getFieldCount(); i++) {
                    childrens.put(getTree(manager.getField(i), xpath + "/[" + i + "]"));
                }
                // XD
                result.put("Childrens", childrens);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    // TODO exception handling
    public static Field findElement(String xpath) {

        Field result = null;
        Manager rootManager = UiApplication.getUiApplication().getActiveScreen();

        int[] indecies = parseXPath(xpath);
        
        if (indecies.length == 0) {
            return null;
        }
        
        for (int i = 0; i < indecies.length - 1; i++) {
            try {
                rootManager = (Manager) rootManager.getField(indecies[i]);
            } catch (IndexOutOfBoundsException e) {
                return null;
            } catch (Exception e) {                
                return null;
            }
        }

        try {
            result = rootManager.getField(indecies[indecies.length - 1]);
        } catch (IndexOutOfBoundsException e) {            
            return null;
        }

        return result;
    }

    public static JSONObject getJSONTree() {
        return getTree(UiApplication.getUiApplication().getActiveScreen(), "");
    }

    public static byte[] getScreenShot() {
        Bitmap bitmap = new Bitmap(Display.getWidth(), Display.getHeight());
        Display.screenshot(bitmap);
        JPEGEncodedImage image = JPEGEncodedImage.encode(bitmap, 100);
        byte[] result = image.getData();
        return result;
    }

    public static JSONObject getDeviceInfo() {
        JSONObject result = new JSONObject();

        try {
            result.put("Battery level", DeviceInfo.getBatteryLevel());
            result.put("Device Name", DeviceInfo.getDeviceName());
            result.put("Software Version", DeviceInfo.getSoftwareVersion());
            result.put("Total Flash Size", DeviceInfo.getTotalFlashSize());
            result.put("Free Ram", Memory.getRAMStats().getFree());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

}
