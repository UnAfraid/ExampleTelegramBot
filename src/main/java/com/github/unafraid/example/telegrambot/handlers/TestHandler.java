package com.github.unafraid.example.telegrambot.handlers;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import com.github.unafraid.telegrambot.bots.AbstractTelegramBot;
import com.github.unafraid.telegrambot.handlers.ICommandHandler;
import com.github.unafraid.telegrambot.util.BotUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author UnAfraid
 */
public final class TestHandler implements ICommandHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestHandler.class);
	private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	@Override
	public String getCommand() {
		return "/test";
	}
	
	@Override
	public String getUsage() {
		return "/test <messages count> <sync|async>";
	}
	
	@Override
	public String getDescription() {
		return "Tests how long execution takes in parallel";
	}
	
	@Override
	public String getCategory() {
		return "Utilities";
	}
	
	static class SendMessageTask implements Callable<Message> {
		private final AbstractTelegramBot bot;
		private final Message message;
		private final LongSummaryStatistics statistics;
		private final int order;
		
		public SendMessageTask(AbstractTelegramBot bot, Message message, LongSummaryStatistics statistics, int order) {
			this.bot = bot;
			this.message = message;
			this.statistics = statistics;
			this.order = order;
		}
		
		@Override
		public Message call() throws Exception {
			final Instant start = Instant.now();
			final Message response = BotUtil.sendMessage(bot, message, "Hi " + order, true, true, null);
			final Instant finish = Instant.now();
			final long duration = Duration.between(start, finish).toMillis();
			statistics.accept(duration);
			return response;
		}
	}
	
	static class ThreadSafeLongSummaryStatistics extends LongSummaryStatistics {
		private final ReentrantLock lock = new ReentrantLock();
		
		@Override
		public void accept(int value) {
			lock.lock();
			try {
				super.accept(value);
			} finally {
				lock.unlock();
			}
		}
		
		@Override
		public void accept(long value) {
			lock.lock();
			try {
				super.accept(value);
			} finally {
				lock.unlock();
			}
		}
		
		@Override
		public void combine(LongSummaryStatistics other) {
			lock.lock();
			try {
				super.combine(other);
			} finally {
				lock.unlock();
			}
		}
	}
	
	@Override
	public void onCommandMessage(AbstractTelegramBot bot, Update update, Message message, List<String> args) throws TelegramApiException {
		if (args.size() < 2) {
			BotUtil.sendUsage(bot, message, this);
			return;
		}
		
		final int messageCount;
		try {
			messageCount = Integer.parseInt(args.get(0));
		} catch (Exception ex) {
			BotUtil.sendUsage(bot, message, this);
			return;
		}
		
		if (messageCount < 1) {
			BotUtil.sendMessage(bot, message, "Min message count is *1*", true, true, null);
			return;
		}
		
		if (messageCount > 200) {
			BotUtil.sendMessage(bot, message, "Max message count is *200*", true, true, null);
			return;
		}
		
		final LongSummaryStatistics statistics = new ThreadSafeLongSummaryStatistics();
		final Collection<SendMessageTask> sendMessageTasks = new ArrayList<>();
		for (int i = 0; i < messageCount; i++) {
			sendMessageTasks.add(new SendMessageTask(bot, message, statistics, i + 1));
		}
		
		final Instant start = Instant.now();
		
		final String method = args.get(1).toLowerCase();
		int threads = 1;
		switch (method) {
			case "async": {
				threads = Runtime.getRuntime().availableProcessors();
				try {
					EXECUTOR_SERVICE.invokeAll(sendMessageTasks);
				} catch (InterruptedException ex) {
					LOGGER.warn("Failed to execute tasks", ex);
				}
				break;
			}
			case "sync": {
				for (SendMessageTask sendMessageTask : sendMessageTasks) {
					try {
						sendMessageTask.call();
					} catch (Exception ignored) {
					}
				}
				break;
			}
			default: {
				BotUtil.sendUsage(bot, message, this);
			}
		}
		
		final Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();
		BotUtil.sendMessage(
				bot,
				message,
				String.format(
						"*%s* sent *%d* messages using *%d* threads, time elapsed: `%03d ms`\nStatistics:\n - *avg*: `%,03.03f ms`\n - *min*: `%03d ms`\n - *max*: `%03d ms`\n - *total*: `%03d ms`",
						method,
						sendMessageTasks.size(),
						threads,
						timeElapsed,
						statistics.getAverage(),
						statistics.getMin(),
						statistics.getMax(),
						statistics.getSum()),
				true,
				true,
				null
		);
	}
}
