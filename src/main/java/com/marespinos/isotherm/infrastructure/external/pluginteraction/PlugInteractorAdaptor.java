package com.marespinos.isotherm.infrastructure.external.pluginteraction;

import com.marespinos.isotherm.domain.services.pluginteractor.PlugInteractor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PlugInteractorAdaptor implements PlugInteractor {
    Logger logger = LoggerFactory.getLogger(PlugInteractorAdaptor.class);

    private static final String API_STATES_ENDPOINT = "/api/states";
    private static final String SWITCH_ON_ENDPOINT = "/api/services/switch/turn_on";
    private static final String SWITCH_OFF_ENDPOINT = "/api/services/switch/turn_off";
    private static final String SWITCHER_BODY = "{\"entity_id\": \"%s\"}";

    @Value("${homeassistant.bearer}")
    private String bearerToken;

    @Value("${homeassistant.host}")
    private String homeAssistantHost;

    @Value("${homeassistant.port}")
    private String homeAssistantPort;

    @Value("${homeassistant.switch.name}")
    private String switchName;

    @Override
    public void turnSwitchOn() {
        logger.debug("Turning switch on");

        WebClient.RequestHeadersSpec<?> request = WebClient.create("http://" + homeAssistantHost + ":" + homeAssistantPort)
                .post()
                .uri(SWITCH_ON_ENDPOINT)
                .body(BodyInserters.fromValue(String.format(SWITCHER_BODY, switchName)))
                .header("Authorization", "Bearer " + bearerToken);

        if (!getStatus(request))
            // TODO: use proper exception
            throw new RuntimeException();
    }

    @Override
    public void turnSwitchOff() {
        logger.debug("Turning switch off");

        WebClient.RequestHeadersSpec<?> request = WebClient.create("http://" + homeAssistantHost + ":" + homeAssistantPort)
                .post()
                .uri(SWITCH_OFF_ENDPOINT)
                .body(BodyInserters.fromValue(String.format(SWITCHER_BODY, switchName)))
                .header("Authorization", "Bearer " + bearerToken);

        if (getStatus(request))
            // TODO: use proper exception
            throw new RuntimeException();
    }

    @Override
    public Boolean retrieveSwitchStatus() {
        logger.trace("Retrieving switch status");

        WebClient.RequestHeadersSpec<?> statusRequest = WebClient.create("http://" + homeAssistantHost + ":" + homeAssistantPort)
                .get()
                .uri(API_STATES_ENDPOINT)
                .header("Authorization", "Bearer " + bearerToken);

        return getStatus(statusRequest);
    }

    private Boolean getStatus(WebClient.RequestHeadersSpec<?> statusRequest) {
        ClientResponse response = statusRequest.exchange().block();
        if (response == null)
            // TODO: use proper exception
            throw new RuntimeException();


        String result = response.bodyToMono(String.class).block();

        JSONArray ob = new JSONArray(result);

        for (Object o : ob) {
            JSONObject item = (JSONObject) o;
            String en = (String) item.get("entity_id");
            if (en.equals(switchName)) {
                return item.get("state").equals("on");
            }
        }
        // TODO: use proper exception
        throw new RuntimeException();
    }
}
