package com.epam.autotest.utils;

import net.rim.device.api.system.Characters;
import net.rim.device.api.system.EventInjector;
import net.rim.device.api.system.EventInjector.KeyCodeEvent;
import net.rim.device.api.system.EventInjector.KeyEvent;
import net.rim.device.api.system.EventInjector.TouchEvent;
import net.rim.device.api.system.EventInjector.TrackwheelEvent;
import net.rim.device.api.system.KeypadListener;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.component.Dialog;

/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * Class for UI simulations. Throw events to BB system.
 */

public class UIInvoker {
    
    //TODO Move to another place
    private static synchronized XYRect calculateAbsoluteRect(Field field) {
        XYRect result;
        XYRect rect;
        result = new XYRect(field.getExtent());
        Manager manager = field.getManager();
        rect = manager.getExtent();
        result.x = rect.x + result.x;
        result.y = rect.y + result.y;

        while (manager.getManager() != null) {
            manager = manager.getManager();
            rect = manager.getExtent();
            result.x = rect.x + result.x;
            result.y = rect.y + result.y;
        }

        return result;
    }
    
    public static synchronized void simulateTouch(Field field) {        
        int x, y;
        
        XYRect rect = calculateAbsoluteRect(field);
        
        x = rect.x + rect.width / 2;
        y = rect.y + rect.height / 2;
        
        EventInjector.TouchEvent down = new TouchEvent(TouchEvent.DOWN, x, y,
                -1, -1, -1);
        EventInjector.TouchEvent click = new TouchEvent(TouchEvent.CLICK, x, y,
                -1, -1, -1);
        EventInjector.TouchEvent unclick = new TouchEvent(TouchEvent.UNCLICK,
                x, y, -1, -1, -1);
        EventInjector.TouchEvent up = new TouchEvent(TouchEvent.UP, x, y,
                -1, -1, -1);
        down.post();
        click.post();
        unclick.post();
        up.post();
    }
    
    public static synchronized void simulateTouch(int x, int y) {
        EventInjector.TouchEvent down = new TouchEvent(TouchEvent.DOWN, x, y,
                -1, -1, -1);
        EventInjector.TouchEvent click = new TouchEvent(TouchEvent.CLICK, x, y,
                -1, -1, -1);
        EventInjector.TouchEvent unclick = new TouchEvent(TouchEvent.UNCLICK,
                x, y, -1, -1, -1);
        EventInjector.TouchEvent up = new TouchEvent(TouchEvent.UP, x, y,
                -1, -1, -1);
        
        down.post();
        click.post();
        unclick.post();
        up.post();
    }
    
    public static synchronized void simulateBackKey() {
        new KeyCodeEvent(KeyCodeEvent.KEY_DOWN, Characters.ESCAPE, 0).post();
        new KeyCodeEvent(KeyCodeEvent.KEY_UP, Characters.ESCAPE, 0).post();
    }
    
    public static synchronized void simulateBBKey() {
        //TODO find symbol for menu event
        new KeyEvent(KeyCodeEvent.KEY_DOWN, Characters.CONTROL_MENU, 0).post();
        new KeyEvent(KeyCodeEvent.KEY_UP, Characters.CONTROL_MENU, 0).post();
    }
    
    public static synchronized void simulateTextInput(String text) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            EventInjector.KeyEvent downkey = new KeyEvent(KeyEvent.KEY_DOWN, c,
                    0);
            EventInjector.KeyEvent upkey = new KeyEvent(KeyEvent.KEY_UP, c, 0);
            downkey.post();
            upkey.post();
        }
    }
    
    //Need to investigate... http://stackoverflow.com/questions/1289939/blackberry-trackball-click-instead-of-responding-to-event-context-menu-is-show
    public static synchronized void simulateTrackwheelClick() {
        EventInjector.TrackwheelEvent click = new TrackwheelEvent(TrackwheelEvent.THUMB_CLICK, 0, KeypadListener.STATUS_TRACKWHEEL);
        EventInjector.TrackwheelEvent unclick = new TrackwheelEvent(TrackwheelEvent.THUMB_UNCLICK, 0, KeypadListener.STATUS_TRACKWHEEL);       
        click.post();
        unclick.post();
    }
    
    public static synchronized void simulateTrackwheelUp(int count) {       
        EventInjector.TrackwheelEvent up = new TrackwheelEvent(TrackwheelEvent.THUMB_ROLL_UP, count, 0);        
        up.post();
    }
    
    public static synchronized void simulateTrackwheelDown(int count) {
        EventInjector.TrackwheelEvent down = new TrackwheelEvent(TrackwheelEvent.THUMB_ROLL_DOWN, count, 0);
        down.post();        
    }
    
    public static synchronized void alert(final String s) {
        UiApplication.getUiApplication().invokeLater(new Runnable() {            
            public void run() {
                Dialog.alert(s);
            }
        });
    }
    
}
