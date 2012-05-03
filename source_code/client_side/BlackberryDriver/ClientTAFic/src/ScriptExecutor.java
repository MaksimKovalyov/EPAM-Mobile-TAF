import java.io.FileOutputStream;
import java.util.StringTokenizer;

/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * Execute script. 
 */
public class ScriptExecutor {

    private NativeDriver driver = new BlackberryNativeDriver();

    public ScriptExecutor(NativeDriver driver) {
        this.driver = driver;
    }
    
    public void setNativeDriver(NativeDriver driver) {
        this.driver = driver;
    }

    public boolean execute(String script) {
        boolean testStatus = true;
        script = script.replaceAll("\r", "");
        StringTokenizer tokens = new StringTokenizer(script, "\n");
        while (tokens.hasMoreTokens()) {
            boolean commandresult = true;
            StringTokenizer command = new StringTokenizer(tokens.nextToken(), " ");
            String commandName = command.nextToken().trim().toUpperCase();
            if (commandName.equals("TOUCH")) {
                String classNameChain = command.nextToken();
                try {
                    commandresult = driver.touch(classNameChain);
                    Thread.sleep(300);
                } catch (Exception e1) {
                    commandresult = false;
                }
            } else if (commandName.equals("INPUT")) {
                String classNameChain = command.nextToken();
                String inputText = command.nextToken();
                try {
                    commandresult = driver.inputText(classNameChain, inputText);
                    Thread.sleep(300);
                } catch (Exception e1) {
                    commandresult = false;
                }

            } else if (commandName.equals("WAIT")) {
                Long time = Long.valueOf(command.nextToken());
                try {
                    Thread.sleep(time);
                } catch (Exception e1) {
                    commandresult = false;
                }
            } else if (commandName.equals("SCREENSHOT")) {
                String filename = command.nextToken();
                try {
                    commandresult = driver.takeScreenshot(filename);
                } catch (Exception e) {
                    commandresult = false;
                }
            } else if (commandName.equals("STARTAPPLICATION")) {
                String appName = command.nextToken();
                try {
                    commandresult = driver.launchApplication(appName);
                } catch (Exception e) {
                    commandresult = false;
                }
            } else if (commandName.equals("STARTSIMULATOR")) {
                try {
                    ((BlackberryNativeDriver) driver)
                        .setFledgePath("C:\\Program Files (x86)\\Research In Motion\\BlackBerry Smartphone Simulators 7.0.0\\7.0.0.540 (9810)");
                    commandresult = driver.launchSimulator();
                } catch (Exception e) {
                    commandresult = false;
                }
            } else if (commandName.equals("EXIT")) {
                try {
                    driver.exit();
                } catch (Exception e) {
                    commandresult = false;
                }
            } else if (commandName.equals("ESCAPE")) {
                commandresult = driver.escape();
            } else if (commandName.equals("MENU")) {
                commandresult = driver.menu();
            } else if (commandName.equals("TRACKUP")) {
                try {
                    int count = Integer.valueOf(command.nextToken());
                    commandresult = driver.trackUp(count);
                } catch (Exception e) {
                    commandresult = false;
                }
            } else if (commandName.equals("TRACKDOWN")) {
                try {
                    int count = Integer.valueOf(command.nextToken());
                    commandresult = driver.trackDown(count);
                } catch (Exception e) {
                    commandresult = false;
                }
            } else if (commandName.equals("TRACKCLICK")) {
                try {
                    commandresult = driver.trackClick();
                } catch (Exception e) {
                    commandresult = false;
                }
            } else if (commandName.equals("GETTREE")) {
                try {
                    String s = driver.getTree();
                    String filename = command.nextToken();
                    FileOutputStream output = new FileOutputStream(filename);
                    output.write(s.getBytes());
                    output.close();
                    commandresult = true;
                } catch (Exception e) {
                    commandresult = false;
                }
            } else if (commandName.equals("WAITSCREEN")) {
                try {
                    String screen = command.nextToken();
                    commandresult = driver.wait(screen, 60000);
                } catch (Exception e) {
                    commandresult = false;
                }
            } else if (commandName.equals("CURRENTSCREEN")) {
                try {
                    String screen = driver.getCurrentScreenName();
                    String targetScreen = command.nextToken();
                    if (screen.equals(targetScreen)) {
                        commandresult = true;
                    } else {
                        commandresult = false;
                    }
                } catch (Exception e) {
                    commandresult = false;
                }
            } else if (commandName.equals("LOADCOD")) {
                commandresult = driver.loadCod(command.nextToken());                
            }
            
            if (!commandresult && tokens.hasMoreTokens()) {
                testStatus = false;
            }

        }
        driver.exitSimulator();
        return testStatus;
    }

}
