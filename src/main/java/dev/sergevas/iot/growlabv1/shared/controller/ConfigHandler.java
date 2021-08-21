package dev.sergevas.iot.growlabv1.shared.controller;

import java.util.Map;
import java.util.Optional;

public class ConfigHandler {

    private Map<String, String> configMap;

    public ConfigHandler configMap(Map<String, String> configMap) {
        this.configMap = configMap;
        return this;
    }

    public Integer getAsInteger(String key) {
        String strVal = this.configMap.get(key);
        return Optional.ofNullable(strVal).map(Integer::valueOf).orElse(null);
    }

    public Long getAsLong(String key) {
        String strVal = this.configMap.get(key);
        return Optional.ofNullable(strVal).map(Long::valueOf).orElse(null);
    }

    public Double getAsDouble(String key) {
        String strVal = this.configMap.get(key);
        return Optional.ofNullable(strVal).map(Double::valueOf).orElse(null);
    }

    public String getAsString(String key) {
        return this.configMap.get(key);
    }
}
