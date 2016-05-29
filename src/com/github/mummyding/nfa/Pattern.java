package com.github.mummyding.nfa;

import com.github.mummyding.regex.*;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by MummyDing on 16/5/29.
 */
public class Pattern {

    LinkedList<Frag> stack = new LinkedList<>();

    public Pattern(Regex regex) {

    }

    private Frag toNFA(Regex regex){
        switch (regex.type){
            case RegexParse.Sequence:
                Sequence sequence = (Sequence) regex;
                if (sequence.first.type == RegexParse.NULL){
                    Frag frag = toNFA(sequence.second);
                    return frag;
                }else {
                    Frag f1 = toNFA(sequence.first);
                    Frag f2 = toNFA(sequence.second);
                    patch(f1.out,f2.start);
                    return new Frag(f1.start,f2.out);
                }
                break;
            case RegexParse.Choice:
                Choice choice = (Choice) regex;
                Frag f1 = toNFA(choice.first);
                Frag f2 = toNFA(choice.second);
                State s = new State(f1.start,f2.start,State.Split);
                return new Frag(s,append(f1.out,f2.out));
                break;
            case RegexParse.PlusChar:
                PlusChar plusChar = (PlusChar) regex;
                Frag frag = toNFA(plusChar.value);
                s= new State(frag.start,null,State.Split);
                break;
            case RegexParse.StarChar:
                StarChar starChar = (StarChar) regex;
                frag = toNFA(starChar);
                s = new State(frag.start,null,State.Split);
                patch(frag.out,s);
                return new Frag(s,null);
                break;
            case RegexParse.QuestionChar:
                QuestionChar questionChar = (QuestionChar) regex;
                frag = toNFA(questionChar.value);
                s = new State(frag.start,null,State.Split);
                return new Frag(s,frag.out);
                break;
            case RegexParse.NormalChar:
                NormalChar normalChar = (NormalChar) regex;
                s = new State(null,null,normalChar.value);
                return new Frag(s,toList(s));
                break;
            case RegexParse.AnyChar:
                s = new State(null,null,RegexParse.AnyChar);
                return new Frag(s,toList(s));
                break;
            case RegexParse.EscapeChar:
               /* EscapeChar escapeChar = (EscapeChar) regex;
                switch (escapeChar.value){
                    case 's':
                        break;
                    case
                }*/
                break;
            case RegexParse.MultiChoice:
             /*   MultiChoice multiChoice = (MultiChoice) regex≥;
               */
                break;
        }
    }




    private void patch(ArrayList<State> l,State s){
        for (int i=0 ; i<l.size() ; i++){
            l.get(i).out = s;
        }
    }

    private ArrayList append(ArrayList l1,ArrayList l2){
        l1.addAll(l2);
        return l1;
    }

    private ArrayList toList(State s){
        ArrayList list = new ArrayList();
        list.add(s);
        return list;
    }

}
