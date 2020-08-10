package com.jvmbytes.plugin.example;

import com.jvmbytes.filter.Filter;
import com.jvmbytes.filter.builder.FilterBuilder;
import com.jvmbytes.filter.matcher.FilterMatcher;
import com.jvmbytes.filter.matcher.Matcher;
import com.jvmbytes.spy.event.BeforeEvent;
import com.jvmbytes.spy.event.Event;
import com.jvmbytes.spy.event.EventType;
import com.jvmbytes.spy.listener.EventListener;
import com.jvmbytes.spy.plugin.SpyPlugin;
import org.kohsuke.MetaInfServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author wongoo
 */
@MetaInfServices(SpyPlugin.class)
public class SpyExamplePlugin implements SpyPlugin {
    private static final Logger logger = LoggerFactory.getLogger(SpyExamplePlugin.class);

    public String getName() {
        return "spy-example-plugin";
    }

    public String getNamespace() {
        return "default";
    }

    public Matcher getMatcher() {
        List<Filter> serviceFilter = new FilterBuilder()
                .onClass("com.jbytes.spy.example.service.*")
                .onAnyBehavior()
                .build();
        Matcher matcher = FilterMatcher.toAndGroupMatcher(serviceFilter);
        return matcher;
    }

    public EventType[] getEventTypes() {
        EventType[] events = new EventType[]{EventType.BEFORE};
        return events;
    }

    public EventListener getEventListener() {
        EventListener eventListener = new EventListener() {
            public void onEvent(Event event) throws Throwable {
                BeforeEvent beforeEvent = (BeforeEvent) event;
                System.out.println("---> plugin log calling " + beforeEvent.javaClassName + "." + beforeEvent.javaMethodName);
                logger.info("---> calling {}.{}", beforeEvent.javaClassName, beforeEvent.javaMethodName);
            }
        };
        return eventListener;
    }
}
