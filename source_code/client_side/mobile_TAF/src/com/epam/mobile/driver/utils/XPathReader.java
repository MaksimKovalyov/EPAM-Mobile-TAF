package com.epam.mobile.driver.utils;

import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XPathReader {
	private String   xmlFile;
	private Document xmlDocument;
	private XPath    xPath;

	// it should be investigated a question of different namespace contexts schemes
	NamespaceContext ctx = new NamespaceContext() {
        public String getNamespaceURI(String prefix) {
            String uri;
            if (prefix.equals("ns1"))
                uri = "http://schemas.xmlsoap.org/soap/envelope/";
            else if (prefix.equals("ns2"))
                uri = "http://thomsonreuters.com/";
            else if (prefix.equals("ns3"))
                uri = "http://www.w3.org/2001/XMLSchema-instance";
            else
                uri = null;//"http://thomsonreuters.com/";
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


	public XPathReader(String xmlFile) {
		this.xmlFile = xmlFile;
		if (xmlFile.contains("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")){
			System.out.println("xml:" + xmlFile);
			initObjectsFromString();
			System.out.println("XML is created from String.");
		} else{
			initObjects();
		}
	}
	
	private void initObjects() {
		try {
			System.out.println("_xml: " + xmlFile);
			xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);			
			xPath = XPathFactory.newInstance().newXPath();
			xPath.setNamespaceContext(ctx);
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
		}
	}

	private void initObjectsFromString() {
		try {
			xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xmlFile)));			
			xPath = XPathFactory.newInstance().newXPath();
			xPath.setNamespaceContext(ctx);
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
		}
	}
	
	public Object read(String expression, QName returnType) {
		try {
			XPathExpression xPathExpression = xPath.compile(expression);
			return xPathExpression.evaluate(xmlDocument, returnType);
		} catch (XPathExpressionException ex) {
			ex.printStackTrace();
			return null;
		}
	}	
	
	public static void main(String[] args){     		
        XPathReader reader = new XPathReader("test\\test.xml");
        
        // To get a xml attribute.
        String expression = "/projects/project[1]/@id";
        System.out.println(reader.read(expression, 
		XPathConstants.STRING) + "\n");
        
        // To get a child element's value.'
        expression = "/projects/project[2]/name";
        System.out.println(reader.read(expression, 
		XPathConstants.STRING) + "\n");
        
        // To get an entire node
        expression = "/projects/project[3]";
        NodeList thirdProject = (NodeList)reader.read(expression, 
		XPathConstants.NODESET);
        traverse(thirdProject);        
    }
     
    private static void traverse(NodeList rootNode){
        for(int index = 0; index < rootNode.getLength();
		index ++){
            Node aNode = rootNode.item(index);
            if (aNode.getNodeType() == Node.ELEMENT_NODE){
                NodeList childNodes = aNode.getChildNodes();            
                if (childNodes.getLength() > 0){
                    System.out.println("Node Name-->" + 
					aNode.getNodeName() +
					" , Node Value-->" + 
                     aNode.getTextContent());
                }
                traverse(aNode.getChildNodes());                
            }
        }        
    }

    public String getLoginHASH(){
        String expression = "//Login";
        String result = read(expression, XPathConstants.STRING).toString();
        System.out.println("OUTPUT: Login String is " + result);
        
    	return result;
    }
    
    public String getSomethingPercentChange(){
        String expression = "//PercentChange";
        String result = read(expression, XPathConstants.STRING).toString();
        System.out.println("OUTPUT: Login String is " + result);
        
    	return result;
    }
    
    public String getSomethingWeight(){
        String expression = "//Weight";
        String result = read(expression, XPathConstants.STRING).toString();
        System.out.println("OUTPUT: Login String is " + result);
        
    	return result;
    }
    
    public String getSomethingDefaultCurrency(){
        String expression = "//DefaultCurrencyCode";
        String result = read(expression, XPathConstants.STRING).toString();
        System.out.println("OUTPUT: Login String is " + result);
        
    	return result;
    }
    
     
     
 /*   private ArrayList<String> getHostUUIDs(String prefix){
    	ArrayList<String> res = new ArrayList<String>();
    	String expression = prefix + 
    		"member[" + indexHostUUIDForClient + "]";  //"/value/struct/member";
    	
    	System.out.println("expression: " + expression);
    	
        NodeList uuids = (NodeList)read(expression, XPathConstants.NODESET);
        res = getUUIDs(uuids);
        //res.put("hostAddresses", getHostAddressesByPrefix(expression, indexHostAddresses));
        System.out.println("OUTPUT: host UUIDs: " + res);
        
        return res;
    }
    
    private ArrayList<String> getUUIDs(NodeList node){
    	ArrayList<String> store = new ArrayList<String>();
    	String argumentName = "";
    	String argumentValue = "";
    	NodeList rootNode = null;
    	
    	if (node.getLength() > 0) {
    		rootNode = node;    	
    		
    		// get 6 arguments and values, for example hostOsName and Operation System name and version
    		for(int index = 0; index < rootNode.getLength();
    		index ++){
                Node aNode = rootNode.item(index);
                if (aNode.getNodeType() == Node.ELEMENT_NODE){
                    NodeList childNodes = aNode.getChildNodes();            
                    if (childNodes.getLength() > 0){
                    	argumentName = getNodeValue(childNodes, 0);
                    	argumentValue = getNodeValue(childNodes, 1);
                        System.out.println("Node Name-->" +
                        		aNode.getNodeName() + "[" + index + "]" + "-->" + 
                        		getNodeName(childNodes, 0) +
                        		" , Node Value-->" + argumentName);
                        System.out.println("Node Name-->" +
            					aNode.getNodeName() + "[" + index + "]" + "-->" + 
            					getNodeName(childNodes, 1) +
            					" , Node Value-->" + argumentValue);
                        //result.add(aNode.getTextContent());
                        store.add(argumentValue);
                    } 
                }      
            }
    		System.out.println("OUTPUT: Node values: " + store);
    	} else{
    		System.out.println("OUTPUT: There are no Node values.");
    	}
    	
    	return store;
    }
*/    
    
    /**
     * Get NodeList object by its full path
     * @param expression
     * @return
     */
    public NodeList getNodeListByPath(final String expression)
    {
    	return (NodeList)read(expression, XPathConstants.NODESET);
    }
   
    /**
     * Get Number object by its full path
     * @param expression
     * @return
     */
    public long getLongByPath(final String expression)
    {
    	ArrayList<String> res = new ArrayList<String>();
        res = getNodeValues(getNodeListByPath(expression));
        System.out.println("OUTPUT: Host Addresses: " + res);
        
    	return (0 < res.size() ? Long.valueOf(res.get(0)) : 0);
    }
   
    
/*    private HashMap getInfo(NodeList node){
    	HashMap storeInfo = new HashMap();
    	String argumentName = "";
    	String argumentValue = "";
    	NodeList rootNode = null;
    	
    	if (node.getLength() > 0) {
    		rootNode = node;    	
    		
    		// get 6 arguments and values, for example hostOsName and Operation System name and version
    		for(int index = 0; index < rootNode.getLength();
    		index ++){
                Node aNode = rootNode.item(index);
                if (aNode.getNodeType() == Node.ELEMENT_NODE){
                    NodeList childNodes = aNode.getChildNodes();            
                    if (childNodes.getLength() > 0){
                    	argumentName = getNodeValue(childNodes, 0);
                    	argumentValue = getNodeValue(childNodes, 1);
                        System.out.println("Node Name-->" +
                        		aNode.getNodeName() + "[" + index + "]" + "-->" + 
                        		getNodeName(childNodes, 0) +
                        		" , Node Value-->" + argumentName);
                        System.out.println("Node Name-->" +
            					aNode.getNodeName() + "[" + index + "]" + "-->" + 
            					getNodeName(childNodes, 1) +
            					" , Node Value-->" + argumentValue);

                        storeInfo.put(argumentName, argumentValue);
                    } 
                }      
            }    		
    		System.out.println("OUTPUT: Node values: " + storeInfo);
    	} else{
    		System.out.println("OUTPUT: There are no Node values.");
    	}
    	
    	return storeInfo;
    }
 */   
    @SuppressWarnings("unused")
	private String getNodeName(NodeList root, int index){
    	String name = "";
    	Node aNode = root.item(index);
        if (aNode.getNodeType() == Node.ELEMENT_NODE){
            NodeList childNodes = aNode.getChildNodes();            
            if (childNodes.getLength() > 0) name = aNode.getNodeName();
        }
        
        return name;
    }
    
    @SuppressWarnings("unused")
	private String getNodeValue(NodeList root, int index){
    	String value = "";
    	Node aNode = root.item(index);
        if (aNode.getNodeType() == Node.ELEMENT_NODE){
            NodeList childNodes = aNode.getChildNodes();            
            if (childNodes.getLength() > 0) value = aNode.getTextContent();
        }
        
        return value;
    }
    
    private ArrayList<String> getNodeValues(NodeList node){
    	ArrayList<String> result = new ArrayList<String>();
    	NodeList rootNode = null;
    	
    	if (node.getLength() > 0) {
    		rootNode = node;
    		for(int index = 0; index < rootNode.getLength();
    		index ++){
                Node aNode = rootNode.item(index);
                if (aNode.getNodeType() == Node.ELEMENT_NODE){
                    NodeList childNodes = aNode.getChildNodes();            
                    if (childNodes.getLength() > 0){
                        System.out.println("Node Name-->" +
    					aNode.getNodeName() + "[" + index + "]" +
    					" , Node Value-->" + 
                         aNode.getTextContent());
                        result.add(aNode.getTextContent());
                    } 
                }
            }
    		System.out.println("OUTPUT: Node values: " + result);
    	} else{
    		System.out.println("OUTPUT: There are no Node values.");
    	}
        
        return result;
    }
    
 	public String getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}
}
