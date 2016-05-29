package com.github.mummyding;

import com.github.mummyding.regex.Regex;
import com.github.mummyding.regex.RegexParse;

public class Main {

    public static void main(String[] args) {
	// write your code here
        RegexParse parse = new RegexParse("4*j[3-8]");
        Regex regex = parse.load();


    }
}
