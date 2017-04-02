package com.icecreamhead.pokerbot.guice;

import org.bitbucket.strangerintheq.guiceconfig.BindingAnnotationBase;

class BindingAnnotation extends BindingAnnotationBase<Property> implements Config {

    BindingAnnotation( Property bindingKey ) {
        super( bindingKey, Config.class );
    }
}
