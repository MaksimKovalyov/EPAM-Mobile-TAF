package com.epam.mobile.testing.utils;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

public class TestExample {

	public TestHelperMethods helper_methods = new TestHelperMethods();
	
	/*
	@Rule
	public MethodRule beforeMethod = new TestWatchman() {
		@Override
		public void starting(FrameworkMethod method) {
			try {
				MyAnnoBeforeMethod anno = method.getAnnotation(MyAnnoBeforeMethod.class);
				String methodName = anno.methodName();
				
				Method methodToRun = helper_methods.getClass().getDeclaredMethod(methodName, null);
				methodToRun.invoke(helper_methods, null);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			super.starting(method);
		}
	};
	*/
	
	@Rule
	public MethodRule watchman = new TestWatchman() {
		@Override
		public void starting(FrameworkMethod method) {
			try {

				ExpectedModel anno = method.getAnnotation(ExpectedModel.class);
				String[] mParts = anno.modelParts();
				//String mName = anno.methodName();
				
				// generate model method, populate model
				
				Method methodOfModel = expectedModel.getClass().getDeclaredMethod("get" + mParts[0], null);
				
				methodOfModel.invoke(expectedModel, new Object(){});
				
				for (String string : mParts) {
					System.out.println("model parts in anno: " + string);
				}

				GroupTest gp = method.getAnnotation(GroupTest.class);
				String[] tcs = gp.TCs();
				//String mName = anno.methodName();
				
				// generate model method, populate model			
				for (String string : tcs) {
					System.out.println("tcs in group test: " + string);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			super.starting(method);			
		}
	};
	 
	
	public TestAppModel expectedModel = new TestAppModel("Expected");
	public String currentTest = "";
	
	@Before
	public void beforeEachTest(){
	}
	
	//@Ignore
	@Test
	@ExpectedModel(modelParts={"ModelPart"})
	@GroupTest(TCs={"TC1", "TC2", "TC3", "TC4"})
	public void testAnnotation() throws Exception {
		System.out.println("expectedModel: " + expectedModel.ModelPart[0] + " part2: " + expectedModel.ModelPart[1]);
	}
    
	
	/*
	public void doSomethingEarlier() {
		System.out.println("Do something earlier.");
	}

	
	@Test
	@MyAnnoBeforeMethod(methodName="doSomethingEarlier")
	public void doSomething() {
		System.out.println("Do something.");
	}*/
	
}
