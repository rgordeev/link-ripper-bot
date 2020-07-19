package io.tver.telegram.rebrandly;

import feign.FeignException;
import feign.hystrix.FallbackFactory;
import io.tver.telegram.rebrandly.model.LinkResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RebrandlyClientFallbackFactory implements FallbackFactory<RebrandlyClient> {
    @Override
    public RebrandlyClient create(Throwable cause) {

        String httpStatus = cause instanceof FeignException ? Integer.toString(((FeignException) cause).status()) : "";

        return (request, apikey) -> {
            log.error("Rebrandly return http status {}", httpStatus);
            return new LinkResponse();
        };
    }
}