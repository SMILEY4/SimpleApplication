package de.ruegnerlukas.simpleapplication;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.BooleanFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.IntegerFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.StringFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestApplication {


	public static void main(String[] args) {
		SimpleApplication.addProviderConfiguration(new ProviderConfiguration() {
			@Override
			public void configure() {
				add(new StringFactory("application_name", "test_app"));
				add(new IntegerFactory("application_version", 125));
				add(new BooleanFactory("dev_mode", true));
			}
		});
		SimpleApplication.startApplication();
	}


}
