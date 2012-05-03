
/**
 * EPAM Systems
 * @author German_Lomakin
 * 
 * 03/05/2012
 * 
 * This class not used. Maybe in the future...
 */
public class BBUIElement implements UIElement {

    private String xPath;
    private BlackberryNativeDriver driver;
    
    public BBUIElement(String xPath, BlackberryNativeDriver driver) {
        this.xPath = xPath;
        this.driver = driver;
    }

    @Override
    public boolean touch() {
        try {
            boolean result = driver.touch(xPath);
            return result;
        } catch (Exception e) {
            return false;
        }        
    }

    @Override
    public boolean input(String input) {
        try {
            boolean result = driver.inputText(xPath, input);
            return result;
        } catch (Exception e) {
            return false;
        }        
    }
    
}
