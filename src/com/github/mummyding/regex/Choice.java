package com.github.mummyding.regex;

/**
 * Created by MummyDing on 16/5/22.
 */
public class Choice extends Regex {

    public Regex first;
    public Regex second;

    public Choice(Regex first, Regex second) {
        this.first = first;
        this.second = second;
        type = RegexParse.Choice;
    }
}
