package io.tver.telegram.rebrandly;

import io.tver.telegram.rebrandly.model.LinkRequest;
import io.tver.telegram.rebrandly.model.LinkResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Links management client
 * https://developers.rebrandly.com/reference#links-list-endpoint
 */
@FeignClient(value = "rebrandly", url = "${application.rebrandly-api-url}", fallbackFactory = RebrandlyClientFallbackFactory.class)
public interface RebrandlyClient {
    /**
     * Create a new link endpoint
     * https://developers.rebrandly.com/reference#create-link-endpoint
     *
     * POST https://api.rebrandly.com/v1/links
     * Content-Type: application/json
     * apikey: YOUR_API_KEY
     * workspace: YOUR_WORKSPACE_ID
     *
     * E.g. {"destination": "https://www.youtube.com/channel/UCHK4HD0ltu1-I212icLPt3g", "domain": { "fullName": "rebrand.ly"}}
     */
    @RequestMapping(method = RequestMethod.POST, value = "/links", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    LinkResponse links(@RequestBody LinkRequest request, @RequestHeader Map<String, String> headers);
}
