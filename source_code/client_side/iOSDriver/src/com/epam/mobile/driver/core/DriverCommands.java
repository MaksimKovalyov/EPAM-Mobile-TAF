package com.epam.mobile.driver.core;

import net.sf.json.JSONObject;

import com.epam.mobile.driver.guard.controllability.ServerConnectionLostException;
import com.epam.mobile.driver.utils.JSONGenerator;
import com.epam.mobile.driver.utils.JSONParser;
import com.epam.mobile.driver.utils.StubHost;

class DeviceCommands{
	private StubHost host = new StubHost(0);
	private String cmdStatus = "";
	
	// add Exception handler for stubhost
	// add finally: release resources
	public boolean simulateMemoryWarning() throws ServerConnectionLostException{
		boolean result = host.sendCommand(
				Configurator.getInstance().getServerURL(),
				Constants.SIMULATE_MEMORY_WARNING);
		
		return result;
	}
	
	public boolean pingServer() throws ServerConnectionLostException{
		boolean result = host.sendCommand(
				Configurator.getInstance().getServerURL(),
				Constants.PING_SERVER);
		
		if (result) {
			JSONParser.getInstance().setJsonString(host.getResponse());
			try {
				String cmdStatus = JSONParser.getInstance().getValueByKey(Constants.COMMAND_STATUS);
				this.setCmdStatus(cmdStatus);
			} catch (Exception e) {
				System.out.println("[Exception][PingServer]" + e.toString());
				result = false;
			}
		}
		
		return result;
	}

	public String getCmdStatus() {
		return cmdStatus;
	}

	public void setCmdStatus(String cmdStatus) {
		this.cmdStatus = cmdStatus;
	}
}

class UICommands{
	private String className;
	private String classNameChain;
	private String attributeName;
	private String attributeValue;
	private String value;
	private String action;
	private String timeout;	
	private String index;
	private String valueResponse;
	private JSONObject proxy;	
	
	private StubHost host = new StubHost(0);
	
	public boolean setElementValue() throws ServerConnectionLostException{
		
		String dataJSON = JSONGenerator.getInstance().getRequestSetElementValue(
							classNameChain,
							className, 
							attributeName, 
							attributeValue, 
							index, 
							value);
		
		boolean result = host.sendCommand(
							Configurator.getInstance().getServerURL(), 
							Constants.RUN_SCRIPT, 
							dataJSON);
	
		return result;
	}
	
	public boolean getElementValue() throws ServerConnectionLostException{

		String dataJSON = JSONGenerator.getInstance().getRequestGetElementValue(
				classNameChain, 
				attributeName, 
				index);

		boolean result = host.sendCommand(
				Configurator.getInstance().getServerURL(), 
				Constants.RUN_SCRIPT, 
				dataJSON);
		
		if (result) {
			JSONParser.getInstance().setJsonString(host.getResponse());
			try {
				this.setValueResponse(JSONParser.getInstance().getObjectByKey(Constants.RESULT).getString(Constants.RESULT_VALUE));
			} catch (Exception e) {
				System.out.println(e.toString());
				result = false;
			}
		}

		return result;
	}

	public boolean getElement() throws ServerConnectionLostException{

		String dataJSON = JSONGenerator.getInstance().getRequestGetElement(
				classNameChain,
				className,
				attributeName,
				attributeValue,
				index);

		boolean result = host.sendCommand(
				Configurator.getInstance().getServerURL(), 
				Constants.RUN_SCRIPT, 
				dataJSON);
		
		if (result) {
			JSONParser.getInstance().setJsonString(host.getResponse());
			try {
				JSONObject resultObject = JSONParser.getInstance().getObjectByKey(Constants.RESULT);
				JSONParser.getInstance().setJsonObject(resultObject);
				this.setProxy(JSONParser.getInstance().getObjectByKey(Constants.RESULT_PROXY));
			} catch (Exception e) {
				System.out.println("[Exception][GetElement]" + e.toString());
				result = false;
			}
		}

		return result;
	}

	
	public boolean doAction() throws ServerConnectionLostException{
		
		String dataJSON = JSONGenerator.getInstance().getRequestDoAction(
				            classNameChain,
							className, 
							attributeName, 
							attributeValue, 
							index, 
							action, 
							timeout);

		boolean result = host.sendCommand(
				Configurator.getInstance().getServerURL(), 
				Constants.RUN_SCRIPT, 
				dataJSON);

		if (result) {
			JSONParser.getInstance().setJsonString(host.getResponse());
			try {
				this.setValueResponse(JSONParser.getInstance().getObjectByKey(Constants.RESULT).getString(Constants.RESULT_VALUE));
			} catch (Exception e) {
				System.out.println(e.toString());
				result = false;
			}
		}
		
		return result;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public void setClassNameChain(String classNameChain) {
		this.classNameChain = classNameChain;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getValueResponse() {
		return valueResponse;
	}

	public void setValueResponse(String valueResponse) {
		this.valueResponse = valueResponse;
	}
	
	public void setProxy(JSONObject proxy_) {
		this.proxy = proxy_;
	}
	
	public JSONObject getProxy() {
		return proxy;
	}
}

public class DriverCommands{

	static UICommands     uiCommands     = new UICommands();
	static DeviceCommands deviceCommands = new DeviceCommands();
	
	
	public static class SetElementValue extends BidirectionalCommand{
		public boolean execute() throws ServerConnectionLostException {
			boolean result = uiCommands.setElementValue();
			this.setCommandResult(uiCommands.getValueResponse());
			
			return result;
		}
		
		public void setUp(String className, String attributeName, String attributeValue, 
				String elementIndex, String value) {
			uiCommands.setClassName(className);
			uiCommands.setAttributeName(attributeName);
			uiCommands.setAttributeValue(attributeValue);
			uiCommands.setIndex(elementIndex);
			uiCommands.setValue(value);
		}
		
		public void setUp(String classNameChain, String value) {
			uiCommands.setClassNameChain(classNameChain);
			uiCommands.setValue(value);
		}
		
		public void setClassName(String className) {
			uiCommands.setClassName(className);
		}
		
		public void setAttributeName(String attributeName) {
			uiCommands.setAttributeName(attributeName);
		}
		
		public void setAttributeValue(String attributeValue) {
			uiCommands.setAttributeValue(attributeValue);
		}
		
		public void setElementIndex(String index) {
			uiCommands.setIndex(index);
		}
		
		public void setValue(String value) {
			uiCommands.setValue(value);
		}
	}
	
	public static class GetElementValue extends BidirectionalCommand{
		public boolean execute() throws ServerConnectionLostException {
			boolean result = uiCommands.getElementValue();
			this.setCommandResult(uiCommands.getValueResponse());
			
			return result;
		}
		
		public void setUp(String classNameChain, String attributeName, String elementIndex) {
			uiCommands.setClassNameChain(classNameChain);
			uiCommands.setAttributeName(attributeName);
			uiCommands.setIndex(elementIndex);
		}
		
		public void setClassNameChain(String classNameChain) {
			uiCommands.setClassNameChain(classNameChain);
		}
		
		public void setAttributeName(String attributeName) {
			uiCommands.setAttributeName(attributeName);
		}
				
		public void setElementIndex(String index) {
			uiCommands.setIndex(index);
		}		
	}

	public static class GetElement extends BidirectionalCommand{
		private JSONObject proxy = null;
			
		public JSONObject getProxy() {
			return proxy;
		}

		public void setProxy(JSONObject proxy) {
			this.proxy = proxy;
		}

		public boolean execute() throws ServerConnectionLostException {
			boolean result = uiCommands.getElement();
			this.setProxy(uiCommands.getProxy());
			
			return result;
		}
		
		private void clean(){
			this.setUp("", "", "", "", "");
		}
		
		public void setUp(String classNameChain) {
			this.clean();
			uiCommands.setClassNameChain(classNameChain);
		}

		public void setUp(String className, String attributeName, String attributeValue, String index) {
			this.clean();
			uiCommands.setClassName(className);
			uiCommands.setAttributeName(attributeName);
			uiCommands.setAttributeValue(attributeValue);
			uiCommands.setIndex(index);			
		}

		public void setUp(String classNameChain, String className, String attributeName, String attributeValue, String index) {
			uiCommands.setClassNameChain(classNameChain);
			uiCommands.setClassName(className);
			uiCommands.setAttributeName(attributeName);
			uiCommands.setAttributeValue(attributeValue);
			uiCommands.setIndex(index);	
		}
		
		public void setClassNameChain(String classNameChain) {
			uiCommands.setClassNameChain(classNameChain);
		}
		
		public void setAttributeName(String attributeName) {
			uiCommands.setAttributeName(attributeName);
		}

		public void setAttributeValue(String attributeValue) {
			uiCommands.setAttributeValue(attributeValue);
		}
		
		public void setElementIndex(String index) {
			uiCommands.setIndex(index);
		}		
	}
	
	public static class DoAction extends BidirectionalCommand{
		public boolean execute() throws ServerConnectionLostException {
			boolean result = uiCommands.doAction();
			this.setCommandResult(uiCommands.getValueResponse());
			
			return result;
		}
		
		public void setUp(String className, String attributeName, String attributeValue, 
				String elementIndex, String action) {
			uiCommands.setClassName(className);
			uiCommands.setAttributeName(attributeName);
			uiCommands.setAttributeValue(attributeValue);
			uiCommands.setIndex(elementIndex);
			uiCommands.setAction(action);
		}
		
		public void setUp(String classNameChain,
							String action) {
			uiCommands.setClassNameChain(classNameChain);
			uiCommands.setAction(action);
		}
		
		public void setClassName(String className) {
			uiCommands.setClassName(className);
		}
		
		public void setTimeout(double timeout) {
			uiCommands.setTimeout(String.valueOf(timeout));
		}
		
		public void setClassNameChain(String classNameChain) {
			uiCommands.setClassNameChain(classNameChain);
		}
		
		public void setAttributeName(String attributeName) {
			uiCommands.setAttributeName(attributeName);
		}
		
		public void setAttributeValue(String attributeValue) {
			uiCommands.setAttributeValue(attributeValue);
		}
		
		public void setElementIndex(String index) {
			uiCommands.setIndex(index);
		}
		
		public void setAction(String action) {
			uiCommands.setAction(action);
		}
	}
	
	public static class SimulateMemoryWarning implements Command{
		public boolean execute() throws ServerConnectionLostException {
			boolean result = deviceCommands.simulateMemoryWarning();
	
			return result;
		}
	}	
	
	public static class PingServer extends BidirectionalCommand{
		public boolean execute() throws ServerConnectionLostException{
			boolean result = deviceCommands.pingServer();
			this.setCommandResult(deviceCommands.getCmdStatus());
			
			return result;
		}
	}	
}
