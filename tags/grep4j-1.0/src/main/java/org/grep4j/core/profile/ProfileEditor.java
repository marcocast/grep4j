package org.grep4j.core.profile;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.selectFirst;
import static ch.lambdaj.Lambda.selectMax;
import static org.grep4j.core.profile.ProfileConfiguration.profileConfiguration;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.xml.bind.JAXB;

import org.grep4j.core.profile.model.Profile;
import org.grep4j.core.profile.model.Profiles;

/**
 * This class consists exclusively of static methods used to add modify delete and retrieve @see Profile.
 * 
 * @author Marco Castigliego
 * @author Giovanni Gargiulo
 */
public class ProfileEditor {

	private ProfileEditor() {
	}

	public static final String SKIP_PERSIST_PROPERTY = "org.grep4j.skip.persist";

	/**
	 * @param profile
	 */
	public static void add(Profile profile) {
		profile.validate();
		if (profile.getId() == null) {
			if (profiles().size() == 0) {
				profile.setId(1);
			} else {
				Integer maxIndex = ((Profile) selectMax(profiles(),
						on(Profile.class).getId())).getId();
				profile.setId(maxIndex + 1);
			}
		}
		profiles().add(profile);
		persistProfileConfiguration();
	}

	/**
	 * @param profile
	 */
	public static void remove(Profile profile) {
		profiles().remove(getProfile(profile));
		persistProfileConfiguration();
	}

	/**
	 * 
	 */
	public static void removeAll() {
		profiles().clear();
		persistProfileConfiguration();
	}

	/**
	 * @param profileToUpdate
	 */
	public static void update(Profile profileToUpdate) {
		profileToUpdate.validate();
		Profile profile = getProfile(profileToUpdate);
		int index = profiles().indexOf(profile);
		remove(profile);
		profiles().add(index, profileToUpdate);
		persistProfileConfiguration();

	}

	/**
	 * @param profile
	 * @return
	 */
	public static Profile getProfile(Profile profile) {
		Profile result = null;
		if (profile.getId() != null) {
			result = selectFirst(
					profiles(),
					having(on(Profile.class).getId(),
							equalTo(profile.getId())));
		} else if (profile.getName() != null) {
			result = selectFirst(
					profiles(),
					having(on(Profile.class).getName(),
							equalTo(profile.getName())));
		}

		return result;
	}

	/**
	 * @return
	 */
	public static List<Profile> profiles() {
		return profileConfiguration().getProfiles();
	}

	protected static void persistProfileConfiguration() {

		if (System.getProperty(ProfileEditor.SKIP_PERSIST_PROPERTY) != null) {
			return;
		}

		try {

			File configuration = new File(profileConfiguration()
					.getProfileFilename());

			if (configuration.exists()) {
				configuration.delete();
			}

			configuration.createNewFile();

			FileOutputStream fileOutputStream = new FileOutputStream(
					configuration);

			JAXB.marshal(new Profiles(profileConfiguration()
					.getProfiles()), fileOutputStream);

			fileOutputStream.flush();
			fileOutputStream.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
