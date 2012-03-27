package com.epam.mobile.extensions.report;

import java.lang.reflect.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class XRay {

	public void printAll(Class c) {

		Constructor[] cs = this.getAllConstructorsFromClass(c);
		System.out.println("  Constructors:");
		for (Constructor constructor : cs) {
			System.out.print("    " + constructor);
			if (constructor.getDeclaringClass() != c)
		        System.out.println(" (inherited from "
		          + constructor.getDeclaringClass().getName()
		          + ")");
		      else
		        System.out.println();
		}

		System.out.println("  Methods:");
		Method[] ms = this.getAllMethodsFromClass(c);
		for (Method method : ms) {
			System.out.print("    " + method);
			if (method.getDeclaringClass() != c)
		        System.out.println(" (inherited from "
		          + method.getDeclaringClass().getName()
		          + ")");
		      else
		        System.out.println();
		}

		System.out.println("  Classes:");
		Class[] cls = this.getClassesFromClass(c);
		for (Class class1 : cls) {
			System.out.println("    " + class1);
		}

		System.out.println("  Fields:");
		Field[] fs = this.getFieldsFromClass(c);
		for (Field field : fs) {
			System.out.print("    " + field);
			if (field.getDeclaringClass() != c)
		        System.out.println(" (inherited from "
		          + field.getDeclaringClass().getName()
		          + ")");
		      else
		        System.out.println();
		}

		System.out.println("  Interfaces:");
		Class[] is = this.getInterfacesFromClass(c);
		for (Class class2 : is) {
			System.out.println("    " + class2);
		}

		System.out.println("  Local Methods:");
		Method[] mls = this.getLocalMethodsFromClass(c);
		for (Method method2 : mls) {
			System.out.print("    " + method2);
			if (method2.getDeclaringClass() != c)
		        System.out.println(" (inherited from "
		          + method2.getDeclaringClass().getName()
		          + ")");
		      else
		        System.out.println();
		}
	}

	public Method[] getLocalMethodsFromClass(Class c) {

		Method[] mList;

		mList = c.getDeclaredMethods();
		return mList;
	}

	public Method[] getAllMethodsFromClass(Class c) {

		Method[] mList;

		mList = c.getMethods();
		return mList;
	}

	public Constructor[] getAllConstructorsFromClass(Class c) {

		Constructor[] cList;

		cList = c.getDeclaredConstructors();
		return cList;
	}

	public Field[] getFieldsFromClass(Class c) {
		Field[] fList;

		fList = c.getDeclaredFields();
		return fList;
	}

	public Class[] getClassesFromClass(Class c) {
		Vector cList = new Vector();
		Class cTemp;
		Class[] aList;

		cTemp = c;
		while ((cTemp = cTemp.getSuperclass()) != null) {
			cList.addElement(cTemp);
		}

		aList = new Class[cList.size()];
		for (int i = 0; i < cList.size(); i++) {
			aList[i] = (Class) cList.elementAt(i);
		}
		return aList;
	}

	public Class[] getInterfacesFromClass(Class c) {

		Class[] cList;

		cList = c.getInterfaces();
		return cList;
	}
}