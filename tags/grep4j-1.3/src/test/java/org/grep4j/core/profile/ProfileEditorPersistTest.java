package org.grep4j.core.profile;

import static org.grep4j.core.profile.ProfileConfiguration.profileConfiguration;
import static org.grep4j.core.profile.builder.ProfileFixture.aSimpleProfile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.xml.bind.JAXB;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.custommonkey.xmlunit.XMLAssert;
import org.grep4j.core.profile.ProfileEditor;
import org.grep4j.core.profile.model.Profiles;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

@Test
public class ProfileEditorPersistTest {

	private static String PROFILE_CONFIGURATION_TEMP_TEST_FILE = "profiles-editing-test.xml";

	@BeforeMethod
	public void createSimpleProfile() {

		System.setProperty("org.grep4j.profiles",
				PROFILE_CONFIGURATION_TEMP_TEST_FILE);

		if (System.getProperty(ProfileEditor.SKIP_PERSIST_PROPERTY) != null) {
			System.getProperties().remove(ProfileEditor.SKIP_PERSIST_PROPERTY);
		}

		ProfileConfigurationFacility.resetProfileConfiguration();

	}

	public void persistProfileConfigurationTest() throws Exception {

		ProfileEditor.removeAll();
		ProfileEditor.add(aSimpleProfile());
		ProfileEditor.persistProfileConfiguration();

		Profiles profiles = new Profiles(profileConfiguration()
				.getProfiles());

		ByteArrayOutputStream xml = new ByteArrayOutputStream();
		JAXB.marshal(profiles, xml);
		xml.flush();
		xml.close();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document actual = builder.parse(new ByteArrayInputStream(xml
				.toByteArray()));

		FileInputStream foo = new FileInputStream(
				PROFILE_CONFIGURATION_TEMP_TEST_FILE);

		Document expected = builder.parse(foo);

		XMLAssert.assertXMLEqual(actual, expected);

	}

	@AfterMethod
	public void finaliseTest() {
		File fileToDelete = new File(PROFILE_CONFIGURATION_TEMP_TEST_FILE);
		fileToDelete.delete();
	}
}
