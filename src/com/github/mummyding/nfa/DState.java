package com.github.mummyding.nfa;

import com.github.mummyding.NfaList;

/**
 * Created by MummyDing on 16/6/19.
 */
public class DState {
    NfaList nList;
    DState[] next = new DState[256];
    DState first;
    DState second;

    public DState(NfaList nList) {
        this.nList = nList;
    }

    public DState() {
    }
}
