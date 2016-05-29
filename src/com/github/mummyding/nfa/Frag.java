package com.github.mummyding.nfa;

import java.util.ArrayList;

/**
 * Created by MummyDing on 16/5/29.
 */
public class Frag {
    State start;
    ArrayList<State> out;

    public Frag(State start, ArrayList<State> out) {
        this.start = start;
        this.out = out;
    }
}
