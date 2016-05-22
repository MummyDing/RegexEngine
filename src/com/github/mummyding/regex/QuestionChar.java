package com.github.mummyding.regex;

/**
 * Created by MummyDing on 16/5/22.
 */
public class QuestionChar extends Regex {

    public Regex value;

    public QuestionChar(Regex value) {
        this.value = value;
        type = RegexParse.QuestionChar;
    }
}
