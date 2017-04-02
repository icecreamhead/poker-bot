package com.icecreamhead.pokerbot.guice;

import org.bitbucket.strangerintheq.guiceconfig.GuiceConfigModule;

import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Properties;

public class ConfigModule extends GuiceConfigModule<Property> {

    public ConfigModule() throws IOException {
        super(getProps(), Property.values());
    }

    @Override
    protected Annotation createBindingAnnotation(Property prop) {
        return new BindingAnnotation(prop);
    }

    private static Properties getProps() throws IOException {
        Properties props = new Properties();
        props.load(new FileReader("resources/pokerbot.properties"));
        return props;
    }
}
