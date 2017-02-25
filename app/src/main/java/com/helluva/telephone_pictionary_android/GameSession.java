package com.helluva.telephone_pictionary_android;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cal on 2/25/17.
 */

public class GameSession {

    //important Firebase keys
    static String FB_SESSIONS_KEY = "ActiveSessions";

    //SessionState
    public enum SessionState {
        WaitingForPlayers, GameInProgress;
    }

    //GameSession
    String name;
    SessionState state;

    HostPlayer owner;

    List<Player> players;

    public GameSession(String name, HostPlayer owner) {
        this.state = SessionState.WaitingForPlayers;
        this.name = name;
        this.owner = owner;

        this.players = new ArrayList<>();
        this.players.add(owner);
    }

    public GameSession() {
        this.state = SessionState.WaitingForPlayers;
        this.name = null;
        this.owner = null;
        this.players = null;
    }

    public int[] getListOfAvailableTrains() {
        return new int[0];
    }

    public boolean requestTrain(int trainID) {
        return false;
    }

}
