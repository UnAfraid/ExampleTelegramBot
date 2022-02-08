package com.github.unafraid.example.telegrambot.validators;

import java.util.Objects;
import java.util.Set;

import com.github.unafraid.telegrambot.handlers.IAccessLevelValidator;
import com.github.unafraid.telegrambot.handlers.ITelegramHandler;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * @author UnAfraid
 */
public class AdminIdValidator implements IAccessLevelValidator {
	private final Set<Long> adminIds;
	
	public AdminIdValidator(Set<Long> adminIds) {
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
