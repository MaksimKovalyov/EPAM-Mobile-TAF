import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.json.me.JSONException;
import org.json.me.JSONObject;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64DecoderStream;

/**
 * EPAM Systems
 * 
 * @author German_Lomakin 
 * 
 * 03/05/2012
 * 
 * Driver Realization. P.S. Don't forget change fledgePath.
 */
public class BlackberryNativeDriver implements NativeDriver {

    private static final String ACTION_MENU = "menu";
    private static final String FLEDGE = "\\fledge.exe\"";
    private static final String OPERATION_GET_TREE = "getTree";
    private static final String TRACKBALL_RELEASE_EVENT = "/execute=TrackballRelease";
    private static final String TRACKBALL_PRESS_EVENT = "/execute=TrackballPress";
    private static final String TRACKBALL_ROLL_EVENT = "/execute=TrackballRoll(0,-35)";
    private static final int EVENT_TIMEOUT = 100;
    private static final int EVENT_TRACKBALL_TIMEOUT = 1000;
    private static final String KEY_RELEASE_END = "\")";
    private static final String KEY_RELEASE_START = "/execute=KeyRelease(\"";
    private static final String EXIT_SIMULATOR_COMMAND = "/execute=Exit";
    private static final String KEY_PRESS_END = KEY_RELEASE_END;
    private static final String KEY_PRESS_START = "/execute=KeyPress(\"";
    private static final String LOAD_COD_START = "/execute=LoadCod(\"";
    private static final String LOAD_COD_END = "\")";
    
    private static final String FLEDGE_CONTROLLER = "\\fledgecontroller.exe\" ";
    private static final String CMD_PREFIX = "cmd.exe /c \"\"";
    private static final String CMD_END = "\"";
    private static String fledgePath =
        "C:\\Program Files (x86)\\Research In Motion\\BlackBerry Smartphone Simulators 7.0.0\\7.0.0.540 (9810)";
    private static final String KEY_TIMEOUT = "timeout";
    private static final String KEY_TARGET = "target";
    private static final String OPERATION_WAIT = "wait";
    private static final String OPERATION_SET_ELEMENT_VALUE = "setElementValue";
    private static final String ACTION_TRACKWHEEL_CLICK = "trackwheel_click";
    private static final String ACTION_TRACKWHEEL_DOWN = "trackwheel_down";
    private static final String ACTION_ESCAPE = "escape";
    private static final String KEY_COUNT = "count";
    private static final String ACTION_TRACKWHEEL_UP = "trackwheel_up";
    private static final String OPERATION_GET_CURRENT_SCREEN_NAME = "getCurrentScreenName";
    private static final String ACTION_KEYBOARD = "keyboard";
    private static final String KEY_VALUE = "Value";
    // Defaults
    private static final String DEFAULT_HOST = "localhost";
    private final static int PORT = 12345;
    private static final String STREAM_END = "\0";

    // Command actions
    public static final String ACTION_TOUCH = "touch";
    // Command operations
    private static final String OPERATION_DO_ACTION = "doAction";
    private static final String OPERATION_PING = "ping";
    private static final String OPERATION_GET_ELEMENT_VALUE = "getElementValue";
    private static final String OPERATION_SCREENSHOT = "screenshot";
    // Command keys
    private static final String KEY_ACTION = "Action";
    private static final String KEY_STATUS = "status";
    private static final String KEY_OPERATION = "Operation";
    private static final String KEY_DATA = "data";
    private static final String KEY_PARAMS = "Params";
    private static final String KEY_ATTRIBUTE_NAME = "AttributeName";
    private static final String KEY_CLASS_NAME_CHAIN = "ClassNameChain";

    private String host;
    private Socket socket;

    public BlackberryNativeDriver() {
        this.host = DEFAULT_HOST;
    }

    public BlackberryNativeDriver(String host) {
        this.host = host;
    }

    private JSONObject sendRequest(JSONObject request) {
        JSONObject result = new JSONObject();
        try {
            if (socket == null) {
                socket = new Socket(host, PORT);
            }
            socket.setSoTimeout(2000);
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            String str = request.toString() + STREAM_END;
            output.write(str.getBytes());

            StringBuffer buffer = new StringBuffer();

            while (true) {
                byte[] buff = new byte[1];

                int len = input.read(buff);
                if (len != 0) {
                    String s = new String(buff);
                    if (s.equals(STREAM_END)) {
                        break;
                    }
                    buffer.append(s);
                } else {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            result = new JSONObject(buffer.toString());
            input.close();
            output.close();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                    socket = null;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public void setServerHost(String url) {
        host = url;
    }

    @Override
    public boolean isServerAlive() {
        try {
            JSONObject request = new JSONObject();
            request.put(KEY_OPERATION, OPERATION_PING);
            JSONObject response = sendRequest(request);

            if (response.has(KEY_STATUS)) {
                if (response.getBoolean(KEY_STATUS)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public String getElementValue(String classNameChain, String attributeName) throws Exception {
        JSONObject request = new JSONObject();
        request.put(KEY_OPERATION, OPERATION_GET_ELEMENT_VALUE);
        JSONObject params = new JSONObject();
        params.put(KEY_CLASS_NAME_CHAIN, classNameChain);
        params.put(KEY_ATTRIBUTE_NAME, attributeName);
        request.put(KEY_PARAMS, params);
        JSONObject response = sendRequest(request);
        if (response.getBoolean(KEY_STATUS)) {
            return response.getString(KEY_DATA);
        }
        return null;
    }

    @Override
    public UIElement getElement(String classNameChain) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean setElementValue(String className, String attributeName, String value) throws Exception {
        JSONObject request = new JSONObject();
        request.put(KEY_OPERATION, OPERATION_SET_ELEMENT_VALUE);
        JSONObject params = new JSONObject();
        params.put(KEY_CLASS_NAME_CHAIN, className);
        params.put(KEY_ATTRIBUTE_NAME, attributeName);
        params.put(KEY_VALUE, value);
        request.put(KEY_PARAMS, params);
        JSONObject response = sendRequest(request);
        if (response.getBoolean(KEY_STATUS)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean doAction(String classNameChain, String action) throws Exception {
        JSONObject request = new JSONObject();
        request.put(KEY_OPERATION, OPERATION_DO_ACTION);
        JSONObject params = new JSONObject();
        params.put(KEY_CLASS_NAME_CHAIN, classNameChain);
        params.put(KEY_ACTION, action);
        request.put(KEY_PARAMS, params);
        JSONObject response = sendRequest(request);
        if (response.getBoolean(KEY_STATUS)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean touch(String classNameChain) throws Exception {
        return doAction(classNameChain, ACTION_TOUCH);
    }

    @Override
    public boolean flash(String classNameChain) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public UIElement findElement(String xpath) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean takeScreenshot(String localFileName) throws Exception {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(KEY_OPERATION, OPERATION_SCREENSHOT);
        JSONObject result = sendRequest(jsonObj);
        result = result.getJSONObject(KEY_DATA);
        String img = result.getString(KEY_DATA);
        byte[] data = BASE64DecoderStream.decode(img.getBytes());
        FileOutputStream output = new FileOutputStream(localFileName);
        output.write(data);
        output.close();
        return true;
    }

    @Override
    public boolean inputText(String classNameChain, String text) throws Exception {
        JSONObject request = new JSONObject();
        request.put(KEY_OPERATION, OPERATION_DO_ACTION);
        JSONObject params = new JSONObject();
        params.put(KEY_CLASS_NAME_CHAIN, classNameChain);
        params.put(KEY_VALUE, text);
        params.put(KEY_ACTION, ACTION_KEYBOARD);
        request.put(KEY_PARAMS, params);
        JSONObject response = sendRequest(request);
        boolean status = response.getBoolean(KEY_STATUS);
        if (status) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getCurrentScreenName() throws Exception {
        JSONObject request = new JSONObject();
        request.put(KEY_OPERATION, OPERATION_GET_CURRENT_SCREEN_NAME);
        JSONObject response = sendRequest(request);
        return response.getString(KEY_DATA);
    }

    @Override
    public boolean trackUp(int count) throws Exception {
        JSONObject request = new JSONObject();
        request.put(KEY_OPERATION, OPERATION_DO_ACTION);
        JSONObject params = new JSONObject();
        params.put(KEY_ACTION, ACTION_TRACKWHEEL_UP);
        params.put(KEY_COUNT, count);
        request.put(KEY_PARAMS, params);
        JSONObject response = sendRequest(request);
        boolean status = response.getBoolean(KEY_STATUS);
        if (status) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean trackDown(int count) throws Exception {
        JSONObject request = new JSONObject();
        request.put(KEY_OPERATION, OPERATION_DO_ACTION);
        JSONObject params = new JSONObject();
        params.put(KEY_ACTION, ACTION_TRACKWHEEL_DOWN);
        params.put(KEY_COUNT, count);
        request.put(KEY_PARAMS, params);
        JSONObject response = sendRequest(request);
        boolean status = response.getBoolean(KEY_STATUS);
        if (status) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean trackClick() throws Exception {
        JSONObject request = new JSONObject();
        request.put(KEY_OPERATION, OPERATION_DO_ACTION);
        JSONObject params = new JSONObject();
        params.put(KEY_ACTION, ACTION_TRACKWHEEL_CLICK);
        request.put(KEY_PARAMS, params);
        JSONObject response = sendRequest(request);
        boolean status = response.getBoolean(KEY_STATUS);
        if (status) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean wait(String target, long timeout) throws Exception {
        JSONObject request = new JSONObject();
        request.put(KEY_OPERATION, OPERATION_WAIT);
        JSONObject params = new JSONObject();
        params.put(KEY_TARGET, target);
        params.put(KEY_TIMEOUT, timeout);
        request.put(KEY_PARAMS, params);
        JSONObject response = sendRequest(request);
        boolean status = response.getBoolean(KEY_STATUS);
        if (status) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void exit() {

        try {
            if (socket == null) {
                socket = new Socket(host, PORT);
            }

            OutputStream output = socket.getOutputStream();

            output.write(("exit" + STREAM_END).getBytes());

            output.close();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                    socket = null;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getTree() {
        String result = "";
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put(KEY_OPERATION, OPERATION_GET_TREE);
            JSONObject response = sendRequest(jsonObj);
            result = response.toString();

        } catch (JSONException e) {
            return result;
        }

        return result;
    }

    @Override
    public boolean launchSimulator() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(CMD_PREFIX + fledgePath + FLEDGE);
            return true;
        } catch (IOException e1) {
            return false;
        }
    }

    public void setFledgePath(String path) {
        fledgePath = path;
    }

    @Override
    public boolean launchApplication(String appName) {
        Runtime runtime = Runtime.getRuntime();
        try {

            for (int i = 0; i < appName.length(); i++) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(CMD_PREFIX);
                buffer.append(fledgePath);
                buffer.append(FLEDGE_CONTROLLER);
                buffer.append(KEY_PRESS_START);
                buffer.append(String.valueOf(appName.charAt(i)));
                buffer.append(KEY_PRESS_END);
                buffer.append(CMD_END);
                runtime.exec(buffer.toString());
                Thread.sleep(EVENT_TIMEOUT);
                buffer = new StringBuffer();
                buffer.append(CMD_PREFIX);
                buffer.append(fledgePath);
                buffer.append(FLEDGE_CONTROLLER);
                buffer.append(KEY_RELEASE_START);
                buffer.append(String.valueOf(appName.charAt(i)));
                buffer.append(KEY_RELEASE_END);
                buffer.append(CMD_END);
                runtime.exec(buffer.toString());
                Thread.sleep(EVENT_TIMEOUT);
            }

            Thread.sleep(EVENT_TRACKBALL_TIMEOUT);
            StringBuffer buffer = new StringBuffer();
            buffer.append(CMD_PREFIX);
            buffer.append(fledgePath);
            buffer.append(FLEDGE_CONTROLLER);
            buffer.append(TRACKBALL_ROLL_EVENT);
            buffer.append(CMD_END);
            runtime.exec(buffer.toString());
            Thread.sleep(EVENT_TRACKBALL_TIMEOUT);

            buffer = new StringBuffer();
            buffer.append(CMD_PREFIX);
            buffer.append(fledgePath);
            buffer.append(FLEDGE_CONTROLLER);
            buffer.append(TRACKBALL_PRESS_EVENT);
            buffer.append(CMD_END);
            runtime.exec(buffer.toString());
            Thread.sleep(EVENT_TIMEOUT);

            buffer = new StringBuffer();
            buffer.append(CMD_PREFIX);
            buffer.append(fledgePath);
            buffer.append(FLEDGE_CONTROLLER);
            buffer.append(TRACKBALL_RELEASE_EVENT);
            buffer.append(CMD_END);
            runtime.exec(buffer.toString());

            return true;
        } catch (IOException e1) {
            return false;
        } catch (InterruptedException e1) {
            return false;
        }

    }

    @Override
    public boolean escape() {
        try {
            JSONObject request = new JSONObject();
            request.put(KEY_OPERATION, OPERATION_DO_ACTION);
            JSONObject params = new JSONObject();
            params.put(KEY_ACTION, ACTION_ESCAPE);
            request.put(KEY_PARAMS, params);
            JSONObject response = sendRequest(request);
            if (response.getBoolean(KEY_STATUS)) {
                return true;
            }
        } catch (JSONException e) {
            return false;
        }

        return false;
    }

    @Override
    public boolean menu() {
        try {
            JSONObject request = new JSONObject();
            request.put(KEY_OPERATION, OPERATION_DO_ACTION);
            JSONObject params = new JSONObject();
            params.put(KEY_ACTION, ACTION_MENU);
            request.put(KEY_PARAMS, params);
            JSONObject response = sendRequest(request);
            if (response.getBoolean(KEY_STATUS)) {
                return true;
            }
        } catch (JSONException e) {
            return false;
        }

        return false;
    }

    @Override
    public boolean exitSimulator() {
        try {
            Runtime runtime = Runtime.getRuntime();
            StringBuffer buffer = new StringBuffer();
            buffer.append(CMD_PREFIX);
            buffer.append(fledgePath);
            buffer.append(FLEDGE_CONTROLLER);
            buffer.append(EXIT_SIMULATOR_COMMAND);
            buffer.append(CMD_END);
            runtime.exec(buffer.toString());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean resetSimulator() {
        // TODO maybe this feature be useful
        return false;
    }

    @Override
    public boolean loadCod(String path) {
        try {
            Runtime runtime = Runtime.getRuntime();
            StringBuffer buffer = new StringBuffer();
            buffer.append(CMD_PREFIX);
            buffer.append(fledgePath);
            buffer.append(FLEDGE_CONTROLLER);
            buffer.append(LOAD_COD_START);
            buffer.append(path);
            buffer.append(LOAD_COD_END);
            buffer.append(CMD_END);
            runtime.exec(buffer.toString());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
