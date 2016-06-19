package com.github.mummyding.nfa;

/**
 * Created by MummyDing on 16/5/29.
 */
public class State implements Comparable<State>{

    public final static int Split = 256;
    private static int count = 1;
    public final static State MatchState = new State(null,null,257);
    int id;
    int c;
    State out;
    State out1;
    int lastlist;

    public State() {
        id = count++;
    }

    public State(int c) {
        id = count++;
        this.c = c;
    }

    public State(char c)
    {
        id = count++;
        this.c = c - ' ';
    }


    public State(State out, State out1, int c) {
        this.out = out;
        this.out1 = out1;
        id = count++;
        this.c = c;
    }

    public State(State out, State out1, char c) {
        this.out = out;
        this.out1 = out1;
        id = count++;
        this.c = c - ' ';
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof State)) return false;
        return this.id == ((State) o).id;
    }

    @Override
    public int compareTo(State other)
    {
        if (this.id > other.id) return 1;
        else if (this.id < other.id) return -1;
        return 0;
    }
}
