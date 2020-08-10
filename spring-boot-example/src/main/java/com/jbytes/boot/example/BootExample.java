package com.jbytes.boot.example;

import com.jbytes.spy.example.model.User;
import com.jbytes.spy.example.service.UserService;
import com.jvmbytes.spy.plugin.PluginLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wongoo
 */
@SpringBootApplication
@Controller
public class BootExample {

    private static final Logger logger = LoggerFactory.getLogger(BootExample.class);

    UserService userService = new UserService();

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BootExample.class, args);
    }

    @RequestMapping("/user")
    @ResponseBody
    public User getUser(@RequestParam("name") String name) throws Exception {
        User user = userService.getUser("wongoo");
        logger.info("user: {}", user);
        return user;
    }

    @RequestMapping("/res")
    @ResponseBody
    public Object res(@RequestParam("path") String path) throws Exception {
        return BootExample.class.getResource(path);
    }

    @RequestMapping("/load")
    @ResponseBody
    public String load(@RequestParam("groupId") String groupId, @RequestParam("artifactId") String artifactId) throws Exception {
        PluginLoader.load(groupId, artifactId);
        return groupId + ":" + artifactId + " loaded";
    }

    @RequestMapping("/unload")
    @ResponseBody
    public String unload(@RequestParam("groupId") String groupId, @RequestParam("artifactId") String artifactId) throws Exception {
        PluginLoader.unload(groupId, artifactId);
        return groupId + ":" + artifactId + " unloaded";
    }

    @RequestMapping("/load_example")
    @ResponseBody
    public String loadExample() throws Exception {
        return load("com.jvmbytes.spy.plugin", "spy-plugin-example");
    }

    @RequestMapping("/unload_example")
    @ResponseBody
    public String unloadExample() throws Exception {
        return unload("com.jvmbytes.spy.plugin", "spy-plugin-example");
    }

}
