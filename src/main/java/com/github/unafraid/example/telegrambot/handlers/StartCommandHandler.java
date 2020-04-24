package com.github.unafraid.example.telegrambot.handlers;

import com.github.unafraid.telegrambot.bots.AbstractTelegramBot;
import com.github.unafraid.telegrambot.handlers.ICommandHandler;
import com.github.unafraid.telegrambot.util.BotUtil;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

/**
 * Very basic command handler accepts command starting with /start
 */
public class StartCommandHandler implements ICommandHandler {
	@Override
	public String getCommand() {
		return "/start";
	}

	@Override
	public String getUsage() {
		return "/start";
	}

	@Override
	public String getDescription() {
		return "The initial command that you send when you start talking to a bot";
	}

	@Override
	public int getRequiredAccessLevel() {
		return 0;
	}

	@Override
	public void onCommandMessage(AbstractTelegramBot bot, Update update, Message message, List<String> args) throws TelegramApiException {
		final StringBuilder sb = new StringBuilder();
		if (message.getFrom().getUserName() == null || message.getFrom().getUserName().isEmpty()) {
			sb.append("Hello ").append(message.getFrom().getFirstName()).append(", how are ya doin'?").append(System.lineSeparator());
			sb.append("You may want to set an UserName in order to access /menu command").append(System.lineSeparator());
		} else {
			sb.append("Hello @").append(message.getFrom().getUserName()).append(", how are ya doin'?").append(System.lineSeparator());
			sb.append("Type in /menu to see my cool inline menus!");
		}
		BotUtil.sendMessage(bot, message, sb.toString(), true, false, null);
	}
}