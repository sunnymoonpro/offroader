package com.offroader.ioc.annotation.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


import android.view.View;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OREventBase(listenerType = View.OnLongClickListener.class, listenerSetter = "setOnLongClickListener", methodName = "onLongClick")
public @interface OROnLongClick {
	int[] value();
}
