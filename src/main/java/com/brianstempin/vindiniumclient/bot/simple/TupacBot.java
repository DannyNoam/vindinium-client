package com.brianstempin.vindiniumclient.bot.simple;

import com.brianstempin.vindiniumclient.bot.BotMove;
import com.brianstempin.vindiniumclient.bot.BotUtils;
import com.brianstempin.vindiniumclient.dto.GameState;

import java.util.List;

/**
 * Tupac Bot doesn't give a shit.
 */
public class TupacBot extends BotImpl {

    private static final int ACCEPTABLE_HEALTH_LEVEL = 20;
    private static final int ACCEPTABLE_MINE_COUNT = 4;
    private static final int ACCEPTABLE_HEALTH_POINTS_TO_ATTACK = 80;

    @Override
    public BotMove move(GameState gameState) {
        GameState.Board board = gameState.getGame().getBoard();
        GameState.Hero hero = gameState.getHero();
        int heroGold = gameState.getHero().getGold();
        int heroLife = gameState.getHero().getLife();

        List<Vertex> vertexes = doDijkstra(gameState.getGame().getBoard(), gameState.getHero());

        Vertex closestPub = getNearestPub(vertexes);
        Vertex closestEnemy = getNearestPlayer(vertexes);

        if(heroLife < ACCEPTABLE_HEALTH_LEVEL)  {
            return walkTowardsPub(closestPub, hero);
        }
        else if(shouldAttackPlayer(hero)) {
            return walkTowardsEnemyPlayer(closestEnemy, hero);
        } else {
            return walkInARandomDirection();
        }
    }

    private boolean shouldAttackPlayer(GameState.Hero hero) {
        return hero.getMineCount() < ACCEPTABLE_MINE_COUNT && hero.getLife() > ACCEPTABLE_HEALTH_POINTS_TO_ATTACK;
    }

    private BotMove walkTowardsPub (Vertex closestPub, GameState.Hero hero) {
        Vertex nearestPub = getPath(closestPub).get(0);
        return BotUtils.directionTowards(hero.getPos(), nearestPub.getPosition());
    }

    private BotMove walkTowardsEnemyPlayer (Vertex enemyPlayer, GameState.Hero hero) {
        Vertex nearestPlayer = getPath(enemyPlayer).get(0);
        return BotUtils.directionTowards(hero.getPos(), nearestPlayer.getPosition());
    }

    public BotMove walkInARandomDirection () {
        int randomNumber = (int)(Math.random() * 4);

        switch(randomNumber) {
            case 1:
                return BotMove.NORTH;
            case 2:
                return BotMove.SOUTH;
            case 3:
                return BotMove.EAST;
            case 4:
                return BotMove.WEST;
            default:
                return BotMove.STAY;
        }
    }
}
