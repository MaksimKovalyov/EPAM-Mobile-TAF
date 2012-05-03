package com.epam.autotest.utils;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.TextField;

/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * Class for Managing attributes
 */

public class AttributeManager {

    private static final String ATTRIBUTE_TEXT = "text";
    private static final String ATTRIBUTE_HEIGTH = "heigth";
    private static final String ATTRIBUTE_WIDTH = "width";
    private static final String ATTRIBUTE_TOP = "top";
    private static final String ATTRIBUTE_LEFT = "left";

    public static boolean setAttribute(Field field, String attribName, String attribValue) throws NumberFormatException {        
        
        // TODO Add more classes
        if (field instanceof ButtonField) {
            if (attribName.equals(ATTRIBUTE_TEXT)) {
                ((ButtonField) field).setLabel(attribValue);
                return true;
            }
        }
        
        if (field instanceof LabelField) {
            if (attribName.equals(ATTRIBUTE_TEXT)) {
                ((LabelField) field).setText(attribValue);
                return true;
            }
        }
        
        if (field instanceof TextField) {
            if (attribName.equals(ATTRIBUTE_TEXT)) {
                ((TextField) field).setText(attribValue);
                return true;
            }
        } 
        
        if (field instanceof EditField) {
            if (attribName.equals(ATTRIBUTE_TEXT)) {
                ((EditField) field).setText(attribValue);
                return true;
            }
        }

        return false;
    }

    public static String getAttribute(Field field, String attribName) {
        String result = null;
        
        if (field instanceof ButtonField) {
            if (attribName.equals(ATTRIBUTE_TEXT)) {                
                return ((ButtonField) field).getLabel();
            }
        }
        
        if (field instanceof LabelField) {
            if (attribName.equals(ATTRIBUTE_TEXT)) {                
                return ((LabelField) field).getText();
            }
        }
        
        if (field instanceof TextField) {
            if (attribName.equals(ATTRIBUTE_TEXT)) {
                return ((TextField) field).getText();
            }
        } 
        
        if (field instanceof EditField) {
            if (attribName.equals(ATTRIBUTE_TEXT)) {
                return ((EditField) field).getText();
            }
        }

        if (field instanceof Field) {
            if (attribName.equals(ATTRIBUTE_HEIGTH)) {
                result = String.valueOf(field.getHeight());
            } else if (attribName.equals(ATTRIBUTE_WIDTH)) {
                result = String.valueOf(field.getWidth());
            } else if (attribName.equals(ATTRIBUTE_TOP)) {
                result = String.valueOf(field.getTop());
            } else if (attribName.equals(ATTRIBUTE_LEFT)) {
                result = String.valueOf(field.getLeft());
            }
        }

        return result;
    }

}
