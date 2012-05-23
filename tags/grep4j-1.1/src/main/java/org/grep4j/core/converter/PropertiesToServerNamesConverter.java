package org.grep4j.core.converter;

import ch.lambdaj.function.convert.PropertyExtractor;

public class PropertiesToServerNamesConverter extends PropertyExtractor<String, String> {

	public PropertiesToServerNamesConverter(String propertyName) {
		super(propertyName);
	}

	@Override
	public String convert(String string) {
		return string.substring(0, string.indexOf('.'));
	}
}