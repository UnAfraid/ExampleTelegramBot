package com.github.unafraid.example.telegrambot;

import java.util.HashSet;
import java.util.Set;

import com.github.unafraid.example.telegrambot.handlers.ExampleInlineMenuHandler;
import com.github.unafraid.example.telegrambot.handlers.HelpHandler;
import com.github.unafraid.example.telegrambot.handlers.StartCommandHandler;
import com.github.unafraid.example.telegrambot.handlers.TestHandler;
import com.github.unafraid.example.telegrambot.handlers.WhoAmIHandler;
import com.github.unafraid.example.telegrambot.validators.AdminIdValidator;
import com.github.unafraid.telegrambot.bots.DefaultTelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * @author UnAfraid
 */
public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws Exception {
		final String token = System.getenv("EXAMPLE_TG_BOT_TOKEN");
		final String username = System.getenv("EXAMPLE_TG_BOT_USERNAME");
		final String adminIds = System.getenv("EXAMPLE_TG_BOT_ADMIN_IDS");
		if (token == null || token.isBlank()) {
			LOGGER.warn("EXAMPLE_TG_BOT_TOKEN is not defined!");
			return;
		} else if (username == null || username.isBlank()) {
			LOGGER.warn("EXAMPLE_TG_BOT_USERNAME is not defined!");
			return;
		} else if (adminIds == null || adminIds.isBlank()) {
			LOGGER.warn("EXAMPLE_TG_BOT_ADMIN_IDS is not defined!");
			return;
		}
		
		LOGGER.info("Initializing {} ...", username);
		
		final Set<Long> adminIdsList = parseAdminIds(adminIds);
		if (adminIdsList.isEmpty()) {
			LOGGER.warn("Couldn't find admin ids");
			return;
		}
		LOGGER.info("Authorized admin ids: {}", adminIdsList);
		
		// Create new instance of TelegramBotsAPI
		final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		
		// Register the default bot with token and username
		final DefaultTelegramBot telegramBot = new DefaultTelegramBot(token, username);
		telegramBotsApi.registerBot(telegramBot);
		
		// Register access level validator
		telegramBot.setAccessLevelValidator(new AdminIdValidator(adminIdsList));
		
		// Register handlers
		telegramBot.addHandler(new ExampleInlineMenuHandler());
		telegramBot.addHandler(new HelpHandler());
		telegramBot.addHandler(new StartCommandHandler());
		telegramBot.addHandler(new WhoAmIHandler());
		telegramBot.addHandler(new TestHandler());
		LOGGER.info("Initialization done");
	}
	
	private static Set<Long> parseAdminIds(String adminIds) {
		final Set<Long> whitelistUserIds = new HashSet<>();
		for (String adminIdValue : adminIds.split(",")) {
			try {
				final long adminId = Long.parseLong(adminIdValue);
				if (adminId < 0) {
					LOGGER.warn("User ID expected, negative ids are reserved for groups!");
					continue;
				}
				whitelistUserIds.add(adminId);
			} catch (Exception e) {
				LOGGER.warn("Failed to parse admin id {}", adminIdValue, e);
			}
		}
		return whitelistUserIds;
	}
}
