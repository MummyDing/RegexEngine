package com.github.mummyding.nfa;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by MummyDing on 16/6/19.
 */
public class NfaList {
    State[] ss;

    public NfaList(ArrayList<State> states) {
        ss=states.toArray(new State[0]);
        Arrays.sort(ss);
    }

    @Override
    public int hashCode() {
        int ans = 0;
        for (int i = 0; i < ss.length; i++) {
            ans += ss.hashCode();
        }
        return ans;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NfaList))
            return false;
        NfaList l = (NfaList) obj;
        if (ss.length != l.ss.length)
            return false;
        for (int i = 0; i < ss.length; i++) {
            if (!ss[i].equals(l.ss[i]))
                return false;
        }
        return true;
    }
}
