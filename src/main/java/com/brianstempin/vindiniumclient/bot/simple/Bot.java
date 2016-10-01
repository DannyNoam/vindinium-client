package com.brianstempin.vindiniumclient.bot.simple;

import com.brianstempin.vindiniumclient.bot.BotMove;
import com.brianstempin.vindiniumclient.dto.GameState;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface Bot {

    default BotMove move(GameState gameState) {
        throw new NotImplementedException();
    }
};
