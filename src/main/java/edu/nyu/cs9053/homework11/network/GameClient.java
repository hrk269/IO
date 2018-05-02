package edu.nyu.cs9053.homework11.network;

import edu.nyu.cs9053.homework11.game.Difficulty;
import edu.nyu.cs9053.homework11.game.GameProvider;
import edu.nyu.cs9053.homework11.network.GameServer;
import edu.nyu.cs9053.homework11.game.screen.InputMove;

import java.io.*;
import java.net.Socket;
import java.util.Random;

/**
 * User: blangel
 *
 * A blocking IO implementation of a client which requests moves from a remote server implementing the
 * {@linkplain edu.nyu.cs9053.homework11.network.NetworkGameProvider}
 */
public class GameClient implements GameProvider {

    private final Difficulty difficulty;

    private static InputStream serverInput;

    private static OutputStream serverOutput;

    public static GameClient construct(Difficulty difficulty) {
        Socket serverConnection = null;
        try {
            serverConnection = new Socket(GameServer.SERVER_HOST, GameServer.SERVER_PORT);
            return new GameClient(difficulty, serverConnection.getInputStream(), serverConnection.getOutputStream());
        } catch (IOException ioe) {
            System.out.printf(ioe.getMessage());
        }
        finally {
            try{
                serverConnection.close();
            }
            catch (IOException ioe) {
                System.out.printf(ioe.getMessage());
            }
        }
        return null;
    }


    public GameClient(Difficulty difficulty, InputStream serverInput, OutputStream serverOutput) {
        this.difficulty = difficulty;
        this.serverInput = serverInput;
        this.serverOutput = serverOutput;
    }

    @Override
    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    @Override
    public int getRandomNumberOfNextFoes() {
        Random random = new Random();
        return random.nextInt(this.difficulty.getLevel());
    }

    @Override
    public InputMove getRandomNextMove() {
        Random random = new Random();
        if (random.nextInt(100) > 50) {
            if (random.nextInt(100) > 50){
                return InputMove.Up;
            }
            else {
                return InputMove.Down;
            }
        }
        else {
            if (random.nextInt(100) > 5) {
                return InputMove.Left;
            }
            else {
                return  InputMove.Right;
            }
        }
    }
}