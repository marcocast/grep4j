package org.grep4j.core.command;

public class OperativeSystemDetector {
	
	private final static String os = System.getProperty("os.name").toLowerCase();

	private OperativeSystemDetector(){}
	
	public static boolean isWindows() {
		return (os.indexOf("win") >= 0);
	}
 
	public static boolean isMac() { 
		return (os.indexOf("mac") >= 0);
	}
 
	public static boolean isUnix() {
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
	}
 
	public static boolean isSolaris() {
		return (os.indexOf("sunos") >= 0);
	}
}
