package com.github.mummyding.nfa;

import com.github.mummyding.NfaList;
import com.github.mummyding.regex.*;
import com.sun.xml.internal.rngom.ast.om.ParsedPattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by MummyDing on 16/5/29.
 */
public class Pattern {

    LinkedList<Frag> stack = new LinkedList<>();
    private State start;
    private DState dStart;
    private ArrayList<State> l1 = new ArrayList<State>();
    private ArrayList<State> l2 = new ArrayList<State>();
    private HashMap<NfaList, DState> dStateMap = new HashMap<NfaList,DState>();
    Regex regex ;
    public Pattern(Regex regex) {
        this.regex = regex;

    }

    private Frag toNFA(Regex regex) {
        switch (regex.type) {
            case RegexParse.Sequence:
                Sequence sequence = (Sequence) regex;
                if (sequence.first.type == RegexParse.NULL) {
                    Frag frag = toNFA(sequence.second);
                    return frag;
                } else {
                    Frag f1 = toNFA(sequence.first);
                    Frag f2 = toNFA(sequence.second);
                    patch(f1.out, f2.start);
                    patch(f1.out,f2.start);
                    return new Frag(f1.start, f2.out,f2.out1);
                }
            case RegexParse.Choice:
                Choice choice = (Choice) regex;
                Frag f1 = toNFA(choice.first);
                Frag f2 = toNFA(choice.second);
                State s = new State(f1.start, f2.start, State.Split);
                return new Frag(s, append(f1.out, f2.out),append(f1.out,f2.out1));
            case RegexParse.PlusChar:
                PlusChar plusChar = (PlusChar) regex;
                Frag frag = toNFA(plusChar.value);
                s = new State(frag.start, frag.start, State.Split);
                patch(frag.out, s);
                patch1(frag.out1,s);
                return new Frag(frag.start, new ArrayList<State>(), toList(s));
            case RegexParse.StarChar:
                StarChar starChar = (StarChar) regex;
                frag = toNFA(starChar.value);
                s = new State(frag.start, null, State.Split);
                patch(frag.out, s);
                patch1(frag.out1,s);
                return new Frag(frag.start, new ArrayList<State>(),toList(s));
            case RegexParse.QuestionChar:
                QuestionChar questionChar = (QuestionChar) regex;
                frag = toNFA(questionChar.value);
                s = new State(frag.start, null, State.Split);
                return new Frag(s, frag.out,append(frag.out1,toList(s)));
            case RegexParse.NormalChar:
                NormalChar normalChar = (NormalChar) regex;
                s = new State(null, null, normalChar.value);
                return new Frag(s, toList(s));
            case RegexParse.AnyChar:
                s = new State(null, null, RegexParse.AnyChar);
                return new Frag(s, toList(s));
            case RegexParse.EscapeChar:
               /* EscapeChar escapeChar = (EscapeChar) regex;
                switch (escapeChar.value){
                    case 's':
                        break;
                    case
                }*/
                return null;
            case RegexParse.MultiChoice:
             /*   MultiChoice multiChoice = (MultiChoice) regex≥;
               */
                return null;
        }
        return null;
    }


    private void patch(ArrayList<State> l, State s) {
        for (int i = 0; i < l.size(); i++) {
            l.get(i).out = s;
        }
    }

    public void patch1(ArrayList<State> l, State s)
    {
        for (int i = 0; i < l.size(); i++)
        {
            l.get(i).out1 = s;
        }
    }

    private ArrayList append(ArrayList l1, ArrayList l2) {
        l1.addAll(l2);
        return l1;
    }

    private ArrayList toList(State s) {
        ArrayList list = new ArrayList();
        list.add(s);
        return list;
    }

    public boolean match( String goalStr) {
        Frag frag = toNFA(regex);
        patch(frag.out,State.MatchState);
        patch1(frag.out1,State.MatchState);

        this.start = frag.start;
        NfaList startNFA = new NfaList(startList(start, l1));
        dStart = new DState(startNFA);
        dStateMap.put(startNFA, dStart);


        ArrayList<State> clist, nlist, t;
        for (int startIndex = 0; startIndex < goalStr.length(); startIndex++)
        {
            clist = startList(start, l1);
            nlist = l2;
            for (int i = startIndex; i < goalStr.length(); i++)
            {
                char c = goalStr.charAt(i);
                step(clist, c - ' ', nlist);
                t = clist;
                clist = nlist;
                nlist = t;
                if (clist.isEmpty()) break;
                if (isMatch(clist)) return true;
            }
            if (isMatch(clist)) return true;
        }
        return false;
    }

    boolean isMatch(List l){
        for (int i=0 ; i<l.size() ; i++){
            if (l.get(i).equals(State.MatchState)){
                return true;
            }
        }
        return false;
    }

    int listid;

    private ArrayList<State> startList(State s, ArrayList<State> l)
    {
        listid++;
        l.clear();
        addState(l, s);
        return l;
    }

    void addState(List l, State s) {
        if (s == null || s.lastlist == listid) {
            return;
        }
        if (s.lastlist == State.Split) {
            addState(l, s.out);
            addState(l, s.out1);
            return;
        }
        l.add(s);
    }

    private void step(ArrayList<State> clist, int c, ArrayList<State> nlist)
    {
        int i;
        State s;
        listid++;
        nlist.clear();
        for (i = 0; i < clist.size(); i++)
        {
            s = clist.get(i);
            if (s.c == c || (s.c == RegexParse.AnyChar && c != '\n' - ' ')
                    || ( Character.isLetter(s.c))
                    || (!Character.isLetter(s.c))) addState(
                    nlist, s.out);
          /*  else if (s.c == Scale)
            {
                ScaleState ss = (ScaleState) s;
                if (ss.scale[c]) addState(nlist, ss.out);
            }*/
        }
    }

}
