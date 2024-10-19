package net.ansinn.pantheon.api;

/**
 * Specifies the data which composes a godly entity.
 * @param name The name of the god.
 * @param description The description of a god; Their likes, their dislikes, their general tenants.
 * @param hiddenAgendas The hidden goals a god has.
 * @param relationships The various relations a god has.
 */
public record God(String name, String description, String[] hiddenAgendas, String[] relationships, Gender gender) {

    public void prompt(String prompt) {
        var representation = Prompter.getRepresentation();

        var builder = new StringBuilder()
                .append("You're the ").append(gender.title)
                .append(" ").append(name);

        if(!Prompter.isPromptSlimming())
            builder.append("(pronouns: ").append(gender.pronouns).append(") ");

        System.out.println(builder);

//        representation.prompt(prompt);
    }

    /**
     * Mainly flavor-text based. Simply gives the prompt data to use about a god.
     *
     * Will be chopped off if slim-down mode is active.
     */
    public enum Gender {
        Male("god", "he/him/his"),
        Female("goddess", "she/her/hers"),
        Other("deity", "they/them/theirs");

        final String title, pronouns;

        Gender(String title, String pronouns) {
            this.title = title;
            this.pronouns = pronouns;
        }
    }

}
