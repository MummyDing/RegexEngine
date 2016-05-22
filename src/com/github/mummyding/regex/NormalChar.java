package com.github.mummyding.regex;

/**
 * Created by MummyDing on 16/5/22.
 */
public class NormalChar extends Regex{

    public char value;

    public NormalChar(char value) {
        this.value = value;
        type = RegexParse.NormalChar;
    }
}
