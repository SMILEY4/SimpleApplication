package de.ruegnerlukas.simpleapplication.common.resources;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public final class Resource {


	/**
	 * The separator for file paths.
	 */
	private static final String PATH_SEPARATOR_TOKEN = "/";

	/**
	 * The path to the resource.
	 */
	private final String path;


	/**
	 * Whether the path is internal or absolute.
	 */
	private final boolean internal;




	/**
	 * Creates a new {@link Resource} with the given path relative to the project.
	 *
	 * @param path the path to the file
	 * @return the create resource
	 */
	public static Resource internal(final String path) {
		return new Resource(path, true);
	}




	/**
	 * Creates a new {@link Resource} with the given absolute path.
	 *
	 * @param path the path to the file
	 * @return the create resource
	 */
	public static Resource external(final String path) {
		return new Resource(path, false);
	}




	/**
	 * Creates a new {@link Resource} with the given path relative to the root directory.
	 *
	 * @param path the relative path to the file
	 * @return the create resource
	 */
	public static Resource externalRelative(final String path) {
		final String rootDirectory = getRootDirectory().getPath().replaceAll("\\\\", PATH_SEPARATOR_TOKEN);
		return Resource.external(rootDirectory + PATH_SEPARATOR_TOKEN + path);
	}




	/**
	 * @return the current root directory
	 */
	public static Resource getRootDirectory() {
		return Resource.external(new File("").getAbsolutePath());
	}




	/**
	 * @param path     the path
	 * @param internal whether the path is internal or absolute
	 */
	private Resource(final String path, final boolean internal) {
		Validations.INPUT.notBlank(path).exception("The path must not be null or empty.");
		this.path = path.replace("\\", PATH_SEPARATOR_TOKEN);
		this.internal = internal;
	}




	/**
	 * @return whether the path of this {@link Resource} is internal or absolute
	 */
	public boolean isInternal() {
		return this.internal;
	}




	/**
	 * @return the path of this {@link Resource}
	 */
	public String getPath() {
		return this.path;
	}




	/**
	 * @return this resource as a {@link URL}
	 */
	public URL asURL() {
		URL url = null;
		if (isInternal()) {
			url = this.getClass().getClassLoader().getResource(getPath());
		} else {
			try {
				url = asFile().toURI().toURL();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return url;
	}




	/**
	 * @return this resource as a {@link File}
	 */
	public File asFile() {
		Validations.STATE.isFalse(isInternal()).exception("The resource '{}' must be an external file.", getPath());
		return new File(getPath());
	}




	/**
	 * @return this resource as an {@link InputStream}
	 */
	public InputStream asInputStream() {
		Validations.STATE.isTrue(isInternal()).exception("The resource '{}' must be an internal file.", getPath());
		return this.getClass().getClassLoader().getResourceAsStream(getPath());
	}




	/**
	 * Check if this resource is equal to the given one
	 *
	 * @param resource the other resource to compare to this one
	 * @return whether the two resources are equal
	 */
	public boolean isEqual(final Resource resource) {
		return resource != null
				&& this.isInternal() == resource.isInternal()
				&& this.getPath().equals(resource.getPath());
	}


}
