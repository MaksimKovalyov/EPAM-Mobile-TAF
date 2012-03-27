package com.epam.mobile.testing.utils;

import java.io.IOException;

public class ScriptExecutor {
	private static ScriptExecutor instance;

	private ScriptExecutor() throws Exception {
	}

	public static ScriptExecutor getInstance() {
		return instance;
	}

	static {
		try {
			instance = new ScriptExecutor();
		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(-1);
		}
	}

	public void run(String script){
		String executable_process_line = getPrefix() + script;
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(executable_process_line);
			
			System.out.println("process: " + pr.toString() + 
					" script: " + executable_process_line + " is running...");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		

		//initStreams(script);
	}
	
	public void runServer(String path) {
		initStreamsAndWait(path);
	}

	public void runPerlScript(String path) {
		initStreams(path);

	}

	public String getPrefix() {
		String prefix = "";
		
		String OSname = System.getProperty("os.name");
		//System.out.println("System OS is " + strOS);
		
		OSname = OSname.replace(" ", "_").toUpperCase();

		switch (OS.valueOf(OSname)) {
/*		case WINDOWS_9:
			System.out.println("System OS 9 is " + strOS);			
			break;
*/
		case WINDOWS_NT:
		case WINDOWS_2000:
		case WINDOWS_XP:
			System.out.println("OUTPUT: System is detected: " + OSname);
			prefix = "bash "; 
			break;
		case LINUX:
		case MAC_OS_X:
			System.out.println("OUTPUT: System is detected: " + OSname);
			prefix = "";
			break;
		case UBUNTU:
			System.out.println("OUTPUT: System is detected: " + OSname);
			prefix = "";
			break;
		default:
			System.out.println("OUTPUT: System is detected: " + OSname);
		}

		return prefix;
	}

	public void initStreamsAndWait(String path) {
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(getPrefix() + path);

			StreamGobbler input = new StreamGobbler(pr.getInputStream(),
					"OUTPUT");
			StreamGobbler errorGobbler = new StreamGobbler(pr.getErrorStream(),
					"ERROR");
			//OutputGobbler stdinGobbler = new OutputGobbler(p.getOutputStream(),
			//		"STDIN");

			input.start();
			errorGobbler.start();
			//stdinGobbler.start();

			// wait for Apache Geronimo start
			Thread.sleep(10000);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void initStreams(String path) {
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(getPrefix() + path);

			StreamGobbler input = new StreamGobbler(pr.getInputStream(),
					"OUTPUT");
			StreamGobbler errorGobbler = new StreamGobbler(pr.getErrorStream(),
					"ERROR");
			//OutputGobbler stdinGobbler = new OutputGobbler(pr.getOutputStream(),
			//		"STDIN");

			input.start();
			errorGobbler.start();
			//stdinGobbler.start();

			int exitVal = pr.waitFor();
			System.out.println("Process exitValue: " + exitVal);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("Prefix is '" + ScriptExecutor.getInstance().getPrefix() + "'.");
	}
	
}
