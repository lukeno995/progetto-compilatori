package util;

import java.util.Stack;

public class StackCode extends Stack<String> {

    //Mantiene lo stato dello stack prendendo l'ultima stringa inserita, modificandola e reinserendola nello stack;
    public void insertInLast(String s) {
        String temp = this.pop();
        temp += s;
        this.push(temp);
    }
}