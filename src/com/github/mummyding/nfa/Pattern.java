package com.github.mummyding.nfa;

import com.github.mummyding.regex.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by MummyDing on 16/5/29.
 */
public class Pattern {

    LinkedList<Frag> stack = new LinkedList<>();
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
                    return new Frag(f1.start, f2.out);
                }
            case RegexParse.Choice:
                Choice choice = (Choice) regex;
                Frag f1 = toNFA(choice.first);
                Frag f2 = toNFA(choice.second);
                State s = new State(f1.start, f2.start, State.Split);
                return new Frag(s, append(f1.out, f2.out));
            case RegexParse.PlusChar:
                PlusChar plusChar = (PlusChar) regex;
                Frag frag = toNFA(plusChar.value);
                s = new State(frag.start, null, State.Split);
                patch(frag.out, s);
                return new Frag(frag.start, toList(s));
            case RegexParse.StarChar:
                StarChar starChar = (StarChar) regex;
                frag = toNFA(starChar);
                s = new State(frag.start, null, State.Split);
                patch(frag.out, s);
                return new Frag(s, null);
            case RegexParse.QuestionChar:
                QuestionChar questionChar = (QuestionChar) regex;
                frag = toNFA(questionChar.value);
                s = new State(frag.start, null, State.Split);
                return new Frag(s, frag.out);
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

    private ArrayList append(ArrayList l1, ArrayList l2) {
        l1.addAll(l2);
        return l1;
    }

    private ArrayList toList(State s) {
        ArrayList list = new ArrayList();
        list.add(s);
        return list;
    }

    List L1 = new ArrayList(),L2 = new ArrayList();
    public boolean match( String goalStr) {
        Frag frag = toNFA(regex);
        patch(frag.out,State.MatchState);
        List cList = startList(frag.start);
        List nList = L2;
        for (int i=0 ; i<goalStr.length() ;i++){
            step(cList,goalStr.charAt(i));
            List t = cList;
            cList = nList;
            nList = t;
        }
        return isMatch(cList);
    }

    boolean isMatch(List l){
        for (int i=0 ; i<l.size() ; i++){
            if (l.get(i) == State.MatchState){
                return true;
            }
        }
        return false;
    }

    int listid;

    List startList(State s) {
        listid++;
        L1= new ArrayList<>();
        addState(L1, s);
        return L1;
    }

    void addState(List l, State s) {
        if (s == null || s.lastlist == listid) {
            return;
        }
        s.lastlist = listid;
        if (s.lastlist == State.Split) {
            addState(l, s.out);
            addState(l, s.out1);
            return;
        }
        l.add(s);
    }

    void step(List cList,int c){
        State s;
        listid++;
        List nList = new ArrayList();
        for (int i=0 ; i<cList.size() ;i++){
            s = (State) cList.get(i);
            if (s.lastlist == c){
                addState(nList,s.out);
            }
        }
    }

}
