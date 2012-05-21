package org.grep4j.core.task;

public class GrepResult {

	private final String profileName;

	private final String fileName;

	private final String text;

	public GrepResult(String profileName, String fileName, String text) {
		super();
		this.profileName = profileName;
		this.fileName = fileName;
		this.text = text;
	}

	public String getProfileName() {
		return profileName;
	}

	public String getFileName() {
		return fileName;
	}

	public String getText() {
		return text;
	}

}
