package com.github.mummyding.regex;

/**
 * Created by MummyDing on 16/5/22.
 */
public class EscapeChar extends Regex{
    public char value;

    public EscapeChar(char value) {
        this.value = value;
        type = RegexParse.EscapeChar;
    }
}
