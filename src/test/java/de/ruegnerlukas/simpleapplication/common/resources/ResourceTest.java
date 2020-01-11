package de.ruegnerlukas.simpleapplication.common.resources;

import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;


public class ResourceTest {


//	@Test
//	public void testExternal() {
//		final String rootDir = new File("").getAbsolutePath();
//		final Resource resource = Resource.external(rootDir + "\\src\\test\\resources\\testResources\\testFile.txt");
//		assertThat(resource).isNotNull();
//		assertThat(resource.asURL()).isNotNull();
//		assertThat(resource.asFile()).isNotNull();
//		assertThat(resource.asFile()).exists();
//	}




	@Test
	public void testInternal() {
		final Resource resource = Resource.internal("testResources/testFile.txt");
		assertThat(resource).isNotNull();
		assertThat(resource.asURL()).isNotNull();
		assertThat(resource.asInputStream()).isNotNull();
	}

}
