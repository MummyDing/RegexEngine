package com.github.mummyding.nfa;

import java.util.ArrayList;

/**
 * Created by MummyDing on 16/5/29.
 */
public class Frag {
    State start;
    ArrayList<State> out;
    ArrayList<State> out1;

    public Frag(State s, ArrayList<State> o)
    {
        start = s;
        out = o;
        out1 = new ArrayList<State>();
    }

    public Frag(State start, ArrayList<State> out, ArrayList<State> out1) {
        this.start = start;
        this.out = out;
        this.out1 = out1;
    }
}
