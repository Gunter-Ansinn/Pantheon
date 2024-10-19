package net.ansinn.pantheon.api.model;

import com.google.gson.Gson;
import net.ansinn.pantheon.api.web.ModelResponse;

/**
 * Represents the absolute basics of a chat model.
 */
public interface ModelRepresentation {

    Gson GSON = new Gson();

    void initialize();

    void terminate();

    ModelResponse prompt(String input, int maxTokens);

    default ModelResponse prompt(String input) {
        return prompt(input,1024);
    }

}
