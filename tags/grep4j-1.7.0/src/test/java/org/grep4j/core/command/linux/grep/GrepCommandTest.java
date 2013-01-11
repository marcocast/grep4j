package org.grep4j.core.command.linux.grep;

import org.testng.annotations.DataProvider;

public abstract class GrepCommandTest {

	public final static String SINGLE_QUOTE = "\'";

	@DataProvider(name = "expressionsAndFile")
	public Object[][] expressionsAndFile() {
		return new Object[][] {
				{ "INFO", "/opt/jboss/server/default/log/server.log" },
				{ "ERROR", "/opt/jboss/server/default/log/server.log" },
				{ "String to search", "server.log" },
				{ "//uh//iij", "any.txt" },
				{ "id:\"23456\"", "/opt/jboss/server/default/log/server.log" },
		};
	}
}
