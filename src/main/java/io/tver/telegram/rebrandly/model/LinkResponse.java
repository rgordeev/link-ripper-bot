package io.tver.telegram.rebrandly.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * Create a new link response
 * https://developers.rebrandly.com/docs/create-a-new-link
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LinkResponse {
    private String id;
    private String title;
    private String slashtag;
    private String destination;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private String shortUrl;
    private Domain domain;

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Domain {
        private String id;
        private String fullName;
    }
}
