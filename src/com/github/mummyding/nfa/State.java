package com.github.mummyding.nfa;

/**
 * Created by MummyDing on 16/5/29.
 */
public class State {

    public final static int Split = 256;
    public final static State MatchState = new State(null,null,257);
    State out;
    State out1;
    int lastlist;

    public State(State out, State out1, int lastlist) {
        this.out = out;
        this.out1 = out1;
        this.lastlist = lastlist;
    }
}
