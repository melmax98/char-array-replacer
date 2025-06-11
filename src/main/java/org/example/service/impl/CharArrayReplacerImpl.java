package org.example.service.impl;

import org.example.service.CharArrayReplacer;

public class CharArrayReplacerImpl implements CharArrayReplacer {

    public char[] replaceChars(char[] originalArray, char[] searchArray, char[] replacementArray) {
        if (originalArray == null || replacementArray == null) {
            throw new IllegalArgumentException("Original array or replacement array must not be null");
        }

        if (searchArray == null || searchArray.length == 0) {
            return copyArray(originalArray);
        }

        char[] result = getEmptyResultArrayWithCalculatedSize(originalArray, searchArray, replacementArray);

        int resultArrayIndex = 0;
        for (int originalArrayIndex = 0; originalArrayIndex < originalArray.length; ) {
            boolean match = isMatch(originalArray, searchArray, originalArrayIndex);
            if (match) {
                //copy replacementArray into resultArray
                for (char replacement : replacementArray) {
                    result[resultArrayIndex++] = replacement;
                }
                originalArrayIndex += searchArray.length;
            } else {
                //copy current symbol
                result[resultArrayIndex++] = originalArray[originalArrayIndex++];
            }
        }

        return result;
    }

    private char[] getEmptyResultArrayWithCalculatedSize(
            char[] originalArray,
            char[] searchArray,
            char[] replacementArray
    ) {
        int count = 0;
        for (int originalArrayIndex = 0; originalArrayIndex <= originalArray.length - searchArray.length; originalArrayIndex++) {
            boolean match = true;
            for (int j = 0; j < searchArray.length; j++) {
                if (originalArray[originalArrayIndex + j] != searchArray[j]) {
                    match = false;
                    break;
                }
            }
            if (match) {
                count++;
                originalArrayIndex += searchArray.length - 1; // skip matched chars
            }
        }

        int newLength = originalArray.length + count * (replacementArray.length - searchArray.length);

        return new char[newLength];
    }

    private boolean isMatch(char[] originalArray, char[] searchArray, int originalArrayIndex) {
        boolean match = false;
        if (originalArrayIndex <= originalArray.length - searchArray.length) {
            match = true;
            for (int j = 0; j < searchArray.length; j++) {
                if (originalArray[originalArrayIndex + j] != searchArray[j]) {
                    match = false;
                    break;
                }
            }
        }
        return match;
    }

    private char[] copyArray(char[] originalArray) {
        char[] copy = new char[originalArray.length];
        for (int i = 0; i < originalArray.length; i++) {
            copy[i] = originalArray[i];
        }
        return copy;
    }

}
