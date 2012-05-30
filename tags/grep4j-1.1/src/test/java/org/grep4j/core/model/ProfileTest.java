package org.grep4j.core.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.testng.annotations.Test;

@Test
public class ProfileTest {
	
	private final static String fileLocation = "fileLocation";
	private final static String fileName = "fileName";
	private final static String name = "name";
	
	public void testGettersAndSetters(){
		Profile profile = new Profile();
		profile.setFileLocation(fileLocation);
		profile.setFileName(fileName);
		profile.setName(name);
		profile.setServerDetails(null);
		assertThat(profile.getFileLocation(), is(fileLocation));
		assertThat(profile.getFileName(), is(fileName));
		assertThat(profile.getName(), is(name));
		assertThat(profile.getServerDetails(), nullValue());
	}
	
	public void testEquals(){
		Profile profile = new Profile();
		profile.setFileLocation(fileLocation);
		profile.setFileName(fileName);
		profile.setName(name);
		profile.setServerDetails(null);		
		Profile profile2 = new Profile();
		profile2.setFileLocation(fileLocation);
		profile2.setFileName(fileName);
		profile2.setName(name);
		profile2.setServerDetails(null);		
		assertThat(profile, is(profile2));
	}

}
