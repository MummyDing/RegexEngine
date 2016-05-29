package com.github.mummyding.regex;

/**
 * Created by MummyDing on 16/5/22.
 */
public class RegexParse {

    private int currentPos = 0;
    private String regexStr;

    public RegexParse(String regexStr) {
        this.regexStr = regexStr;
    }


    private char top(){
        return regexStr.charAt(currentPos);
    }

    private char pop(){
        return regexStr.charAt(currentPos++);
    }

    private boolean pass(char ch){
        if (regexStr.charAt(currentPos) == ch){
            currentPos++;
            return true;
        }
        return false;
    }

    private boolean hasMore(){
        return currentPos < regexStr.length();
    }



    public Regex load(){
        if (regexStr.length() > 0 && regexStr.charAt(0) == '^'){
            regexStr = regexStr.substring(1);
        }
        if (regexStr.length()>0 && regexStr.charAt(regexStr.length()-1) == '$'){
            regexStr = regexStr.substring(0,regexStr.length()-1);
        }
        return split();
    }

    private Regex split(){
        Regex regex = sequence();
        if (hasMore() && top() == '|'){
            pass('|');
            Regex tmp = split();
            return new Choice(regex,tmp);
        }else {
            return regex;
        }
    }

    private Regex sequence(){
        Regex regex = new Regex();
        regex.type = RegexParse.NULL;

        while (hasMore() && top() != '|' && top()!= ')'){
            Regex tmp = parse();
            regex = new Sequence(regex,tmp);
        }
        return regex;
    }



    private Regex parse(){
        Regex regex = getPrefix();
        while (hasMore() && (top() == '*' || top() == '+' || top() == '?')){
            char topChar = top();
            switch (topChar){
                case '*':
                    pass('*');
                    regex = new StarChar(regex);
                    break;
                case '+':
                    pass('+');
                    regex = new PlusChar(regex);
                    break;
                case '?':
                    pass('?');
                    regex = new QuestionChar(regex);
                    break;
            }
        }
        return regex;
    }

    private Regex getPrefix(){
        switch (top()){
            case '(':
                pass('(');
                Regex regex = split();
                pass(')');
                return regex;
            case '\\':
                pass('\\');
                return new EscapeChar(pop());
            case '.':
                pass('.');
                return new AnyChar();
            case '[':
                pass('[');
                regex = multiChoice();
                pass(']');
                return regex;
            default:
                return new NormalChar(pop());
        }
    }


    private Regex singleChoice(){
        if (top() == '['){
            pass('[');
            Regex regex = multiChoice();
            pass(']');
            return regex;
        }
        char start = pop();
        if (top() == '-'){
            pass('-');
            char end = pop();
            return new MultiChoice(start,end);
        }
        return new MultiChoice(start,start);
    }

    private Regex multiChoice(){
        boolean not = false;
        if (top() == '^'){
            not = true;
            pass('^');
        }

        MultiChoice multiChoice = new MultiChoice();
        while (hasMore() && top() != ']'){
            // 先解析一部分
            MultiChoice segment = (MultiChoice) singleChoice();
            //添加进去
            multiChoice.or(segment);
            while (top() == '&'){
                // 处理连接
                pass('&');
                pass('&');
                MultiChoice left = (MultiChoice) linkChoice();
                multiChoice.and(left);
            }
        }
        // 非操作
        if (not) multiChoice.not();
        return multiChoice;
    }



    private Regex linkChoice(){
        MultiChoice multiChoice = new MultiChoice();
        while (hasMore() && top() !=']' && top() != '&'){
            MultiChoice segment = (MultiChoice) singleChoice( );
            multiChoice.or(segment);
        }
        return multiChoice;
    }


    public static int NULL = 0;
    public static int Sequence = 1;
    public static int Choice = 2;
    public static int MultiChoice = 3;
    public static int AnyChar = 4;
    public static int EscapeChar = 5;
    public static int PlusChar = 6;
    public static int StarChar = 7;
    public static int QuestionChar = 8;
    public static int NormalChar = 9;


}
