package com.icecreamhead.pokerbot.model;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.icecreamhead.pokerbot.guice.Config;

import static com.icecreamhead.pokerbot.guice.Property.*;

@Singleton
public class BotConfig {

    private final String botId;
    private final String botPassword;
    private final int gameStyle;
    private final int maxWaitTime;
    private final boolean dontPlaySelfUser;
    private final boolean dontPlaySelfBot;
    private final String oppenentId;

    @Inject
    public BotConfig(
            @Config(BOT_ID) String botId,
            @Config(BOT_PASSWORD) String botPassword,
            @Config(GAME_STYLE) int gameStyle,
            @Config(MAXIMUM_WAIT_TIME) int maxWaitTime,
            @Config(DONT_PLAY_SELF_USER) boolean dontPlaySelfUser,
            @Config(DONT_PLAY_SELF_BOT) boolean dontPlaySelfBot,
            @Config(OPPONENT_ID) String oppenentId) {
        this.botId = botId;
        this.botPassword = botPassword;
        this.gameStyle = gameStyle;
        this.maxWaitTime = maxWaitTime;
        this.dontPlaySelfUser = dontPlaySelfUser;
        this.dontPlaySelfBot = dontPlaySelfBot;
        this.oppenentId = oppenentId;
    }

    public String getBotId() {
        return botId;
    }

    public String getBotPassword() {
        return botPassword;
    }

    public int getGameStyle() {
        return gameStyle;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public boolean isDontPlaySelfUser() {
        return dontPlaySelfUser;
    }

    public boolean isDontPlaySelfBot() {
        return dontPlaySelfBot;
    }

    public String getOppenentId() {
        return oppenentId;
    }
}
