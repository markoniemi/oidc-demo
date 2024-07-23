package org.example.config;

import org.springframework.core.env.PropertySource;

public class FixedPropertySource extends PropertySource {
    public FixedPropertySource(String name) {
        super(name);
    }

    @Override
    public Object getProperty(String key) {
        return ("test.value".equals(key) ? "testValue" : null);
    }
}
