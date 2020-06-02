package com.jbytes.spy.example.transformer;

import com.jbytes.spy.example.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author wongoo
 */
public class Log2Transformer implements ClassFileTransformer {

    private static final Logger logger = LoggerFactory.getLogger(Log2Transformer.class);

    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className.startsWith(App.PROJECT_CLASS_PREFIX)) {
            logger.info("trans class: " + className);
        }
        return null;
    }
}
