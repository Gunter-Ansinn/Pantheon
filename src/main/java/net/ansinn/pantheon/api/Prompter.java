package net.ansinn.pantheon.api;

import net.ansinn.pantheon.api.model.LocalModel;
import net.ansinn.pantheon.api.model.ModelRepresentation;

import java.util.Optional;

public final class Prompter {

    private static ModelRepresentation representation;
    private static boolean promptSlimming;

    public static void initializeWebModel() {

    }

    public static void initializeLocalModel() {
        representation = new LocalModel();
        representation.initialize();
    }

    public static void terminate() {
        representation.terminate();
    }


    /**
     * Gives current instance of model representation.
     * @return singleton model representation instance.
     */
    public static Optional<ModelRepresentation> getRepresentation() {
        return Optional.ofNullable(representation);
    }

    /**
     * Whether useless prompt info is to be foregone.
     * @return prompt slimming value
     */
    public static boolean isPromptSlimming() {
        return promptSlimming;
    }
}
