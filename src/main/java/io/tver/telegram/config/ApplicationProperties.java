package io.tver.telegram.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private String botToken;
    private String botUserName;
    private String rebrandlyApiUrl;
    private String rebrandlyApiKey;
    private String rebrandlyWorkspace;
    private String rebrandlyDomain;

    public Map<String, String> redrandlyHeaders() {
        final Map<String, String> headers = new HashMap<>();
        if (!StringUtils.isEmpty(rebrandlyApiKey)) headers.put("apikey", rebrandlyApiKey);
        if (!StringUtils.isEmpty(rebrandlyWorkspace)) headers.put("workspace", rebrandlyWorkspace);
        return headers;
    }
}
