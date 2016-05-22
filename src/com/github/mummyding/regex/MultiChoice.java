package com.github.mummyding.regex;

/**
 * Created by MummyDing on 16/5/22.
 */
public class MultiChoice extends Regex {

    public boolean[] chars = new boolean[256];

    public MultiChoice() {
        type = RegexParse.MultiChoice;
    }

    public MultiChoice(char start,char end){
        this();
        int s = start - ' ';
        int e = end - ' ';
        for (int i= s; i<= e ;i++){
            chars[i] = true;
        }
    }

    public void and (MultiChoice other){
        for (int i=0 ; i < chars.length ; i++){
            if (this.chars[i] && !other.chars[i]){
                chars[i] = false;
            }
        }
    }


    public void or(MultiChoice other){
        for (int i=0 ; i<chars.length ;i++){
            if (other.chars[i]){
                chars[i] = true;
            }
        }
    }

    public void not(){
        for (int i=0 ; i<chars.length ;i++){
            chars[i] = !chars[i];
        }
    }
}
