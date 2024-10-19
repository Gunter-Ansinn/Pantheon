package net.ansinn.pantheon.api.web;

import com.google.gson.Gson;
import net.minecraft.text.Text;

import java.util.Arrays;

public record ModelResponse(String output, String prompter) {

    public static final ModelResponse EMPTY_RESPONSE = new ModelResponse("NaN", "NaN");

    public Text toParsedText() {

        return Text.of(output);
    }

}
