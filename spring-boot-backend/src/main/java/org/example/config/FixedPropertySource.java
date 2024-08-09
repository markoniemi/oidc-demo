package org.example.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.PropertySource;
@Log4j2
public class FixedPropertySource extends PropertySource {
    public FixedPropertySource(String name) {
        super(name);
    }

    @Override
    public Object getProperty(String key) {
        if (!key.startsWith("tp_config")){
            return null;
        }
        String[] split = key.split("\\.");

        log.debug("FixedPropertySource: "+key);
        return ("tp_config.test.value".equals(key) ? "testValue" : null);
    }
}
