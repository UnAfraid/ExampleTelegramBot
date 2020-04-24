package com.github.unafraid.example.telegrambot.validators;

import com.github.unafraid.telegrambot.handlers.IAccessLevelValidator;
import com.github.unafraid.telegrambot.handlers.ITelegramHandler;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;
import java.util.Objects;

/**
 * @author UnAfraid
 */
public class AdminIdValidator implements IAccessLevelValidator {
	private final List<Integer> adminIds;

	public AdminIdValidator(List<Integer> adminIds) {
		this.adminIds = Objects.requireNonNull(adminIds);
	}

	@Override
	public boolean validate(ITelegramHandler handler, User user) {
		if (handler.getRequiredAccessLevel() == 0) {
			return true;
		}

		return adminIds.contains(user.getId());
	}
}
