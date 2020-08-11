package com.jbytes.spy.example;

import com.jbytes.spy.example.service.UserService;
import com.jbytes.spy.example.transformer.Log2Transformer;
import com.jbytes.spy.example.transformer.LogTransformer;
import com.jvmbytes.agent.inst.InstLoader;
import com.jvmbytes.filter.Filter;
import com.jvmbytes.filter.builder.FilterBuilder;
import com.jvmbytes.filter.matcher.FilterMatcher;
import com.jvmbytes.filter.matcher.Matcher;
import com.jvmbytes.spy.event.BeforeEvent;
import com.jvmbytes.spy.event.Event;
import com.jvmbytes.spy.event.EventType;
import com.jvmbytes.spy.inject.SpyInjector;
import com.jvmbytes.spy.listener.EventListener;
import com.jvmbytes.spy.plugin.PluginLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author wongoo
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static final String PROJECT_CLASS_PREFIX = "com/jbytes/";

    public static void main(String[] args) throws Exception {
        injectDirectly();
        Thread.sleep(10 * 1000);
        pluginLoad();
        Thread.sleep(10 * 1000);
    }

    static UserService userService = new UserService();

    private static void pluginLoad() throws Exception {
        logger.info("start load plugin");

        PluginLoader.load("com.jvmbytes.spy.plugin", "spy-plugin-example");

        logger.info("user: {}", userService.getUser("wongoo"));

        Thread.sleep(10 * 1000);

        logger.info("start unload plugin");
        PluginLoader.unload("com.jvmbytes.spy.plugin", "spy-plugin-example");

        logger.info("user: {}", userService.getUser("wongoo"));
    }

    private static void injectDirectly() {
        LogTransformer logTransformer = new LogTransformer();
        InstLoader.loadInst().addTransformer(logTransformer, true);

        EventListener eventListener = new EventListener() {
            @Override
            public void onEvent(Event event) throws Throwable {
                BeforeEvent beforeEvent = (BeforeEvent) event;
                logger.info("---> calling " + beforeEvent.javaClassName + "." + beforeEvent.javaMethodName);
            }
        };

        List<Filter> serviceFilter =
                new FilterBuilder().onClass("com.jbytes.spy.example.service.*").onAnyBehavior().build();
        Matcher matcher = FilterMatcher.toAndGroupMatcher(serviceFilter);
        EventType[] events = new EventType[]{EventType.BEFORE};

        logger.info("--------------------------");
        logger.info("start inject");
        int injectId = SpyInjector.inject(matcher, events, eventListener);

        logger.info("--------------------------");
        logger.info("call service method");

        logger.info("user: {}", userService.getUser("wongoo"));

        logger.info("--------------------------");
        logger.info("remove inject");
        InstLoader.loadInst().removeTransformer(logTransformer);
        InstLoader.loadInst().addTransformer(new Log2Transformer(), true);
        SpyInjector.remove(injectId);

        logger.info("--------------------------");
        logger.info("call service method");
        logger.info("user: {}", userService.getUser("wongoo"));
    }
}
