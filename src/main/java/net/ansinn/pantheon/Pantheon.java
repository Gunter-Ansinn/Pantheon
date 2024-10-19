package net.ansinn.pantheon;

import net.ansinn.pantheon.api.God;
import net.ansinn.pantheon.api.Prompter;
import net.ansinn.pantheon.api.model.LocalModel;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Pantheon implements ModInitializer {
	public static final String MOD_ID = "pantheon";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ServerLifecycleEvents.SERVER_STARTED.register(this::serverStart);
		ServerLifecycleEvents.SERVER_STOPPED.register(this::serverStop);

		ServerMessageEvents.CHAT_MESSAGE.register(this::onSendMessage);

		LOGGER.info("Hello Fabric world!");
	}

	private void serverStart(MinecraftServer server) {
		Prompter.initializeLocalModel();
		LOGGER.info("Model now loaded up!");
	}

	private void serverStop(MinecraftServer server) {
		Prompter.terminate();
		LOGGER.info("model stopped.");
	}

	private void onSendMessage(SignedMessage signedMessage, ServerPlayerEntity serverPlayerEntity, MessageType.Parameters parameters) {
		var input = signedMessage.getSignedContent();
		if (input.length() < 24)
			return;

		Thread.ofVirtual().start(() -> {
			Prompter.getRepresentation().ifPresent(model -> {
				var result = model.prompt(input);
				var server = Objects.requireNonNull(serverPlayerEntity.server);

				server.sendMessage(Text.literal("LLama: ").append(result.toParsedText()));
			});
		});

	}


}