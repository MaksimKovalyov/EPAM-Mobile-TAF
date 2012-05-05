package com.epam.mobile.driver.utils;

import org.apache.xmlrpc.common.TypeFactoryImpl;
import org.apache.xmlrpc.common.XmlRpcController;
import org.apache.xmlrpc.common.XmlRpcStreamConfig;
import org.apache.xmlrpc.serializer.TypeSerializer;
import org.apache.xmlrpc.serializer.TypeSerializerImpl;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class StringExplicitTypeFactory extends TypeFactoryImpl {
	private static final TypeSerializer STRING_SERIALIZER = new StringSerializer();
 
	public StringExplicitTypeFactory(XmlRpcController pController) {
		super(pController);
	}

	@Override
	public TypeSerializer getSerializer(XmlRpcStreamConfig pConfig,
			Object pObject) throws SAXException {
		if (pObject instanceof String) {
			return STRING_SERIALIZER;
		}
		return super.getSerializer(pConfig, pObject);
	}
}

class StringSerializer extends TypeSerializerImpl {
	/**
	 * (Optional) Tag name of a string value.
	 */
	public static final String STRING_TAG = "string";

	public void write(ContentHandler pHandler, Object pObject)
			throws SAXException {
		// Original was: write(pHandler, null, pObject.toString());
		write(pHandler, STRING_TAG, pObject.toString());
	}
}
