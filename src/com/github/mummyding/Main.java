package com.github.mummyding;

import com.github.mummyding.nfa.Frag;
import com.github.mummyding.nfa.Pattern;
import com.github.mummyding.nfa.State;
import com.github.mummyding.regex.Regex;
import com.github.mummyding.regex.RegexParse;

public class Main {

    public static void main(String[] args) {
        RegexParse parse = new RegexParse("[a-c]");
        Regex regex = parse.load();
        Pattern pattern = new Pattern(regex);
        System.out.print(pattern.match("b"));
    }
}
