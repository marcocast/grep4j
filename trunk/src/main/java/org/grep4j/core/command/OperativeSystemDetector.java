package org.grep4j.core.command;

public class OperativeSystemDetector {

	private final static String os = System.getProperty("os.name").toLowerCase();

	private OperativeSystemDetector() {
	}

	/** 
	 * @return true if the word "win" is in the system property "os.name"
	 */
	public static boolean isWindows() {
		return (os.indexOf("win") >= 0);
	}

	/** 
	 * @return true if the word "mac" is in the system property "os.name"
	 */
	public static boolean isMac() {
		return (os.indexOf("mac") >= 0);
	}

	/** 
	 * @return true if the word "nix" or "nux" is in the system property "os.name"
	 */
	public static boolean isUnix() {
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
	}

	/** 
	 * @return true if the word "sunos" is in the system property "os.name"
	 */
	public static boolean isSolaris() {
		return (os.indexOf("sunos") >= 0);
	}
}
