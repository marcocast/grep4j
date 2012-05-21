package org.grep4j.core.profile.model;

import static org.grep4j.core.profile.builder.ProfileFixture.aSimpleProfile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.bind.JAXB;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.custommonkey.xmlunit.XMLAssert;
import org.grep4j.core.profile.model.Profile;
import org.grep4j.core.profile.model.Profiles;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

@Test
public class ProfilesTest {

	public void profileConfigurationTest() throws Exception {

		Profiles profiles = new Profiles();

		Profile profile = aSimpleProfile();

		profiles.getProfiles().add(profile);

		ByteArrayOutputStream xml = new ByteArrayOutputStream();

		JAXB.marshal(profiles, xml);

		xml.flush();
		xml.close();
		
		System.out.println(xml);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document actual = builder.parse(new ByteArrayInputStream(xml
				.toByteArray()));

		InputStream foo = getClass().getResourceAsStream("/profiles-parse-test.xml");

		Document expected = builder.parse(foo);

		XMLAssert.assertXMLEqual(actual, expected);
	}

}
