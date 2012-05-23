package org.grep4j.core;

public enum ContextControl {

	after("-A"),
	before("-B");

	private final String contextControl;

	ContextControl(String contextControl) {
		this.contextControl = contextControl;
	}

	public String getContextControl() {
		return this.contextControl;
	}

	public static boolean isAContextControl(String item) {
		for (ContextControl server : ContextControl.values()) {
			if (item.startsWith(server.getContextControl())) {
				return true;
			}
		}
		return false;
	}

	public static ContextControl getContextControlFromFullValue(String item) {
		for (ContextControl server : ContextControl.values()) {
			if (item.startsWith(server.getContextControl())) {
				return server;
			}
		}
		return null;
	}

}
