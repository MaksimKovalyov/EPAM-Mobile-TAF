package com.epam.autotest.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.ServerSocketConnection;
import javax.microedition.io.SocketConnection;

import com.epam.autotest.commands.interfaces.Command;
import com.epam.autotest.response.Response;
import com.epam.autotest.utils.CommandParser;
import com.epam.autotest.utils.Logger;
import com.epam.autotest.utils.UIInvoker;

/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 *  
 * Main Server Thread. Listening port and wait for a command.
 */
public class ServerThread extends Thread {

    public volatile boolean ondestroy = false;

    public void run() {
        ServerSocketConnection connect = null;
        SocketConnection sc = null;
        InputStream input = null;
        OutputStream output = null;

        try {

            sleep(100);
            Logger.log("Session Started");
            connect = (ServerSocketConnection) Connector.open("socket://:12345;deviceside=true;interface=wifi");
            
            final String localAddress = connect.getLocalAddress();

            UIInvoker.alert(localAddress);

            while (!ondestroy) {
                try {
                    sc = (SocketConnection) connect.acceptAndOpen();

                    input = sc.openInputStream();
                    output = sc.openOutputStream();

                    sleep(100);

                    StringBuffer buffer = new StringBuffer();

                    while (!ondestroy) {

                        byte[] buff = new byte[1];
                        int len = input.read(buff);
                        if (len != 0) {
                            String s = new String(buff);
                            if (s.equals("\0")) {
                                break;
                            }
                            buffer.append(s);
                        } else {
                            sleep(20);
                        }
                    }
                    if (ondestroy) {
                        break;
                    }
                    final String result = buffer.toString().trim();
                    
                    if ("exit".equals(result)) {
                        Logger.log("Session Terminated");
                        if (input != null) {
                            input.close();
                        }
                        if (output != null) {
                            output.close();
                        }
                        if (sc != null) {
                            sc.close();
                        }
                        if (connect != null) {
                            connect.close();
                        }
                        System.exit(0);
                        
                        return;
                    }
                    
                    Response response = null;                
                    Command command = CommandParser.parser(result);
                    response = command.execute();                   

                    output.write(response.getJsonResponse().getBytes());
                    output.write(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (sc != null) {
                        sc.close();
                    }
                }
            }
            if (sc != null) {
                sc.close();
            }
            if (connect != null) {
                connect.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connect != null) {
                    connect.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
