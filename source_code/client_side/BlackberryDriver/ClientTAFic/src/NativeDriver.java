/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * General interface for BB driver.
 */
public interface NativeDriver {

    public void setServerHost(String url);

    public boolean isServerAlive() throws Exception;

    public String getTree();

    public String getElementValue(String classNameChain, String attributeName) throws Exception;

    public UIElement getElement(String classNameChain) throws Exception;

    public boolean setElementValue(String className, String attributeName, String value) throws Exception;

    public boolean doAction(String classNameChain, String action) throws Exception;

    public boolean touch(String classNameChain) throws Exception;

    public boolean inputText(String classNameChain, String text) throws Exception;

    public boolean takeScreenshot(String localFileName) throws Exception;

    // TODO Investigate?
    public boolean flash(String classNameChain) throws Exception;

    public boolean wait(String target, long timeout) throws Exception;

    public UIElement findElement(String xpath) throws Exception;

    // TODO Asserts realization
    public String getCurrentScreenName() throws Exception;

    public boolean trackUp(int count) throws Exception;

    public boolean trackDown(int count) throws Exception;

    public boolean trackClick() throws Exception;

    public boolean launchApplication(String appName);

    public boolean launchSimulator();
    
    public boolean exitSimulator();
    
    public boolean resetSimulator();
    
    public boolean loadCod(String path);

    public boolean escape();
    
    public boolean menu();

    public void exit();
}
