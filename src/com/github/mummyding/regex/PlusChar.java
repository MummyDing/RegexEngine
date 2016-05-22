package com.github.mummyding.regex;

/**
 * Created by MummyDing on 16/5/22.
 */
public class PlusChar extends Regex{

    public Regex value;

    public PlusChar(Regex value) {
        this.value = value;
        type = RegexParse.PlusChar;
    }
}
