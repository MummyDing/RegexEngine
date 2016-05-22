package com.github.mummyding.regex;

/**
 * Created by MummyDing on 16/5/22.
 */
public class StarChar extends Regex{
    public Regex value;

    public StarChar(Regex value) {
        this.value = value;
        type = RegexParse.StarChar;
    }
}
