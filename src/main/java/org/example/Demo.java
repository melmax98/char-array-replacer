package org.example;

import org.example.service.CharArrayReplacer;
import org.example.service.impl.CharArrayReplacerImpl;

import java.util.Arrays;

public class Demo {
    public static void main(String[] args) {
        char[] orig = {'1', '2', '3', '4', '5'};
        char[] toSearch = {'1', '2'};
        char[] toReplace = {'a', 'b', 'c'};

        CharArrayReplacer charArrayReplacer = new CharArrayReplacerImpl();
        char[] out = charArrayReplacer.replaceChars(orig, toSearch, toReplace);
        System.out.println(Arrays.toString(out));
    }
}
