package net.ansinn.pantheon.api.model;

import net.ansinn.pantheon.api.web.ModelResponse;

public class WebModel implements ModelRepresentation {

    @Override
    public void initialize() {

    }

    @Override
    public void terminate() {

    }

    @Override
    public ModelResponse prompt(String input, int maxTokens) {
        return ModelResponse.EMPTY_RESPONSE;
    }

    @Override
    public ModelResponse prompt(String input) {
        return ModelResponse.EMPTY_RESPONSE;
    }
}
