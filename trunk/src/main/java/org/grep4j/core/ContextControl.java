package org.grep4j.core;

/**
 * Context Line Control 	 
 * @author Marco Castigliego
 *
 */
public enum ContextControl {
	/**
	 * -A NUM, --after-context=NUM
	 *        Print  NUM  lines  of  trailing  context  after  matching lines.
	 *        Places  a  line  containing  a  group  separator  (--)   between
	 *        contiguous  groups  of  matches.  With the -o or --only-matching
	 *        option, this has no effect and a warning is given.
	 */
	after("-A"),
	/**
	 * -B NUM, --before-context=NUM
	 *        Print NUM  lines  of  leading  context  before  matching  lines.
	 *        Places   a  line  containing  a  group  separator  (--)  between
	 *        contiguous groups of matches.  With the  -o  or  --only-matching
	 *        option, this has no effect and a warning is given.
	 */
	before("-B");

	private final String contextControl;

	ContextControl(String contextControl) {
		this.contextControl = contextControl;
	}

	public String getContextControl() {
		return this.contextControl;
	}

	/**
	 * This method is used to verify if the string passed is of type context control.	
	 * @param item
	 * @return true if the item is a ContextControl
	 */
	public static boolean isAContextControl(String item) {
		for (ContextControl server : ContextControl.values()) {
			if (item.startsWith(server.getContextControl())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This is used to return the {@link ContextControl} based on the string passed.
	 * Example : getContextControlFromFullValue("-A") return ContextControl.after
	 * @param item
	 * @return {@link ContextControl}
	 */
	public static ContextControl getContextControlFromFullValue(String item) {
		for (ContextControl server : ContextControl.values()) {
			if (item.startsWith(server.getContextControl())) {
				return server;
			}
		}
		return null;
	}

}
