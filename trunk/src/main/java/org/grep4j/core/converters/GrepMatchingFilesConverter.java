package org.grep4j.core.converters;

import java.util.Arrays;
import java.util.List;

import ch.lambdaj.function.convert.Converter;

public class GrepMatchingFilesConverter implements
		Converter<String, List<String>> {

	@Override
	public List<String> convert(String matchingFiles) {
		return Arrays.asList(matchingFiles.split("\n"));
	}
	
}