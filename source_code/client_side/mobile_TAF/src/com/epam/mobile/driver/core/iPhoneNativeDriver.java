package com.epam.mobile.driver.core;

import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;

import org.apache.log4j.Logger;

import com.epam.mobile.extensions.report.TAFLoggerFactory;

import net.sf.json.JSONObject;

/*
 * 
 * add find element by name, id, property(all keys of UI element for iOS)
 * add simple type as sendKeys
 * add UIElement common interface, expand and divide rights by functionality
 * provide 1-layer abstraction function for scripting
 * make meaning refactoring
 * 
 * update: clarify command status and command result
 * update: refactore all set up methods to general set up if the case has more than 2 settings
 * update: findElement or getElement - ?
 */
public class iPhoneNativeDriver implements NativeDriver{

	private static final Logger logger = TAFLoggerFactory.getLogger(iPhoneNativeDriver.class); 
	
	// Fix for different namespace contexts
	static NamespaceContext ctx2 = new NamespaceContext() {
        public String getNamespaceURI(String prefix) {
            String uri;
            if (prefix.equals("ns1"))
                uri = "http://www.davber.com/order-format";
            else if (prefix.equals("ns2"))
                uri = "http://www.davber.com/sales-format";
            else
                uri = null;
            return uri;
        }
       
        // Dummy implementation - not used!
        public Iterator<String> getPrefixes(String val) {
            return null;
        }
       
        // Dummy implemenation - not used!
        public String getPrefix(String uri) {
            return null;
        }
    };
    
/*
 * command: {
   Operation = getElementValue;
   Params =     {
       AttributeValue = non;
       AttributeName = non;
       ClassNameChain =
"UILabel=accessibilityLabel:LabelName->parent->UIView=index:9->UILabel=index:1";
       ElementIndex = 1;
       Value = text;
   };
 */
    
    public iPhoneNativeDriver() {
		logger.info(" driver set up: ");
		logger.info("       server host = " + 
							Configurator.getInstance().getServerURL());
	}
    
    public iPhoneNativeDriver(String appURL) {
		Configurator.getInstance().setServerURL(appURL);
		
		logger.info(" driver set up: ");
		logger.info("       server host = " + 
							Configurator.getInstance().getServerURL());
	}
    
	public void setServerURL(String url){
		Configurator.getInstance().setServerURL(url);
		
		logger.info(" driver set up: ");
		logger.info("       server host = " + 
							Configurator.getInstance().getServerURL());
	}
	
	public String getServerURL(){
		return Configurator.getInstance().getServerURL();
	}
	
	public boolean simulateMemoryWarning(){
		logger.info(" command: " + Constants.SIMULATE_MEMORY_WARNING); 
				//new Object(){}.getClass().getEnclosingMethod().getName());
		boolean result = false;
		
		try {
			DriverCommands.SimulateMemoryWarning command = new DriverCommands.SimulateMemoryWarning();
			result = command.execute();		
		} catch (Exception e) {
			logger.error(e.toString());
			// add Exception handler, can be used in the lower level
			
			// finally - ?
		}
		logger.info(" status: " + result);
		
		return result;
	}
	
	public boolean isServerAlive(){
		logger.info(" command: " + Constants.PING_SERVER);
		boolean executionStatus = false;
		String commandResult = "FAIL";
		
		try {
			DriverCommands.PingServer command = new DriverCommands.PingServer();
			executionStatus = command.execute();
			
			if (executionStatus) {
				commandResult = command.getCommandResult();
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		
		logger.info(" status: " + commandResult);
		
		return parseBoolean(commandResult);
	}
	
	private boolean parseBoolean(String obj){
		boolean result = false;
		if (obj.equalsIgnoreCase("PASS")) {
			result = true;
		}
		//if (obj.equalsIgnoreCase("FAIL")) {
		//	result = false;
		//}
		// other options as UNDEFINED or NULL
		return result;
	}
	
	public String getElementValue(String classNameChain, 
			String attributeName, String elementIndex){
		logger.info(" command: " + Constants.GET_ELEMENT_VALUE);
		
		String result = Constants.VALUE_NOT_FOUND;
		boolean commandStatus = false;
		
		try {
			DriverCommands.GetElementValue command = new DriverCommands.GetElementValue();
			command.setUp(classNameChain, attributeName, elementIndex);
			commandStatus = command.execute();
			
			if (commandStatus) {
				result = command.getCommandResult();
			}
		} catch (Exception e) {
			logger.error(e.toString()); clear();
		}
		
		logger.info(" status: " + commandStatus);
		
		return result;
	}

	public String getElementValue(String classNameChain, 
									 String attributeName){
		
		return getElementValue(classNameChain, attributeName, "");
	}

	
	public UIElement getElement(String classNameChain){
		
		return getElement(classNameChain, "", "", "", "");
	}
	
	public UIElement getElement(String className, 
								   String attributeName, 
								   String attributeValue, 
								   String index) {
		
		return getElement("", className, attributeName, attributeValue, index);
	}
	
	public UIElement getElement(String classNameChain, 
									String className, 
									String attributeName,
									String attributeValue, 
									String index) {
		logger.info(" command: " + Constants.GET_ELEMENT);

		// String result = Constants.VALUE_NOT_FOUND;
		UIElement element = new UIElement();
		JSONObject proxy = new JSONObject();
		boolean commandStatus = false;
		
		try {
			DriverCommands.GetElement command = new DriverCommands.GetElement();
			command.setUp(classNameChain);
			command.setUp(className, attributeName, attributeValue, index);
			commandStatus = command.execute();

			if (commandStatus) {
				proxy = command.getProxy();
				element.gainEntity(proxy);
			}
		} catch (TAFException e) {
			logger.error(e.toString()); clear();
		}

		logger.info(" status: " + commandStatus);

		return element;
	}
	
	public boolean setElementValue(String className, 
			String attributeName, String attributeValue, 
			String elementIndex, String value) {
		
		return setElementValue("", className, attributeName, attributeValue, elementIndex, value);
	}

	public boolean setElementValue(String classNameChain, 
									String value) {
		
		return setElementValue(classNameChain, "", "", "", "", value);
	}

	public boolean setElementValue(String classNameChain,
									String className,
									String attributeName, 
									String attributeValue,
									String elementIndex, 
									String value) {
		logger.info(" command: " + Constants.SET_ELEMENT_VALUE);
		boolean result = false;
		
		try {
			DriverCommands.SetElementValue command = new DriverCommands.SetElementValue();
			command.setUp(className, attributeName, attributeValue, elementIndex, value);
			result = command.execute();
		} catch (Exception e) {
			logger.error(e.toString()); clear();
		}
		
		logger.info(" status: " + result);
		
		return result;
	}

	
	public boolean touch(String className, 
			String attributeName, String attributeValue) {;

		return doAction(className, attributeName, attributeValue, "", Constants.TOUCH);
	}

	public boolean flash(String className, 
			String attributeName, String attributeValue) {;

		return doAction(className, attributeName, attributeValue, "", Constants.FLASH);
	}
	
	public boolean doAction(String className, 
			String attributeName, String attributeValue, 
			String elementIndex, String action) {
		logger.info(" command: " + Constants.DO_ACTION);
		logger.info("   action = " + action);
		boolean result = false;
		
		try {
			DriverCommands.DoAction command = new DriverCommands.DoAction();
			command.setUp(className, attributeName, attributeValue, elementIndex, action);
			command.setClassNameChain("none");		
			result = command.execute();
		} catch (Exception e) {
			logger.error(e.toString()); clear();
		}

		logger.info(" status: " + result);
		
		return result;
	}

	public String touch(String classNameChain) {
				
		return doAction(classNameChain, Constants.TOUCH);
	}
		
	public String scroll(String classNameChain, String scrollDirection) {
		
		return doAction(classNameChain, scrollDirection);
	}

	public String scrollUp(String classNameChain) {
		
		return doAction(classNameChain, Constants.UI_SCROLL_UP);
	}

	public String scrollDown(String classNameChain) {
		
		return doAction(classNameChain, Constants.UI_SCROLL_DOWN);
	}

	public String scrollRight(String classNameChain) {
		
		return doAction(classNameChain, Constants.UI_SCROLL_RIGHT);
	}
	
	public String scrollRightShort(String classNameChain) {
		
		return doAction(classNameChain, Constants.UI_SCROLL_RIGHT_SHORT);
	}


	public String scrollLeft(String classNameChain) {
		
		return doAction(classNameChain, Constants.UI_SCROLL_LEFT);
	}

	
	public String flash(String classNameChain) {
		
		return doAction(classNameChain, Constants.FLASH);
	}
	
	// update general set up
	public String doAction(String classNameChain, 
			 String action) {
		String result = "";
		boolean cmdStatus = false;
		logger.info(" command: " + Constants.DO_ACTION);
		logger.info("    action =  " + action);		
		
		try {
			DriverCommands.DoAction command = new DriverCommands.DoAction();
			//command.setUp("", "", "", "", action);
			command.setUp(classNameChain, action);
			cmdStatus = command.execute();
			
			if (cmdStatus){
				result = command.getCommandResult();
			}
		} catch (Exception e) {
			logger.error(e.toString());
			clear();
		}
		
		logger.info(" status: " + cmdStatus);
		
		return result;
	}

	public boolean isElementExist(String ui_xpath){
		return parseBoolean(getElementValue(ui_xpath, "isExist"));
	}

	public boolean waitForElementPresent(String ui_xpath, int time_period){
		return parseBoolean(doAction(ui_xpath, "wait:" + time_period));
	}

	public boolean waitForElementPresent(String ui_xpath){
		int default_time_period = 30;
		
		return parseBoolean(doAction(ui_xpath, "wait:" + default_time_period));
	}
	
	public synchronized void wait(int timeout){
		logger.info(" command: " + new Object(){}.getClass().getEnclosingMethod().getName());
		logger.info("    timeout = " + timeout);
		
		try {
			Thread.sleep(timeout);
		} catch (Exception e) {
			logger.error(e.toString()); clear();
		}
	}
	
	// default wait, pass to default system configuration
	public void wait_(){
		int defaultTimeout = 3000;
		wait(defaultTimeout);
	}
	
	private void clear(){
		//TODO: add post-exception state processor 
	}
	
	public UIElement findElement(String xpath){
		
		return getElement(xpath, "", "", "", "");
	}
	
	public UIElement findElement(LinkedString linkedPath){

		/*
		UIElement element = null; 
		
		// refactore into strategy pattern
		// support mixed strategies
		// clean up all code from magic numbers
		System.out.println("origin: " + linkedPath.getOrigin());
		System.out.println("linked path: " + linkedPath);
		
		// refactore as for getElement command
		switch (Integer.parseInt(linkedPath.getOrigin())) {
		case 1:
			element = getElement(linkedPath.getXpath());
			break;
		case 2:
			element = getElement(linkedPath.getClassName(), "", "", "");
			break;
		case 3:
			element = getElement("", linkedPath.getAttributeName(), linkedPath.getAttributeValue(), "");
			break;
		case 4:
			element = getElement("", "", "", linkedPath.getIndex());
			break;
		case 23:
			element = getElement(linkedPath.getClassName(), linkedPath.getAttributeName(), linkedPath.getAttributeValue(), "");
			break;
		case 24:
			element = getElement(linkedPath.getClassName(), "", "", linkedPath.getIndex());
			break;

		}*/
		
		return getElement(linkedPath.getXpath(), 
							linkedPath.getClassName(), 
							linkedPath.getAttributeName(), 
							linkedPath.getAttributeValue(), 
							linkedPath.getIndex());
	}
	

	// update: add unit tests.
	// Testing of main functions	
		public static void main(String[] args){		

			// smoke tests: driver
			NativeDriver driver = new iPhoneNativeDriver();
			driver.setServerURL("http://10.143.24.243:37265/");
			
			UIElement element = driver.getElement("UITextField=placeholder:Enter Username");
			System.out.println("proxy: " + element.getProxy());
			
	/*		
	  		driver.findElement(By.XPath("UITextField=placeholder:Enter Username"));
			
			driver.findElement(By.ClassName("UITextField"));
			driver.findElement(By.Attribute("placeholder", "Enter Username"));
			driver.findElement(By.Index(1));
			
			driver.findElement(By.ClassName("CCCNNN").andAttribute("NNN", "VVV"));
			driver.findElement(By.ClassName("CCCNNN").andIndex(100));		
	*/		
			/*
			String data = "{\"test_command\":\"true\",\"value\":\"835.501Th\"}";
			try {
				System.out.println("----------------------------------------");
				JSONParser.getInstance().setJsonString(data);
				JSONParser.getInstance().getObjectByKey(Constants.VALUE);
				System.out.println("----------------------------------------");		
			} catch (Exception e) {
				System.out.println(e.toString());
			}*/
			
		}
}
