package org.example.service.impl;

import org.example.service.CharArrayReplacer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CharArrayReplacerImplTest {

    private final CharArrayReplacer charArrayReplacer = new CharArrayReplacerImpl();

    @Test
    @DisplayName("Null searchArray returns a copy of the original")
    void replaceChars_nullSearchArray() {
        char[] originalArray = {'a', 'b', 'c'};

        char[] result = charArrayReplacer.replaceChars(
                originalArray,
                null,
                new char[]{'x'}
        );

        assertThat(originalArray).isEqualTo(result);
        assertThat(result).isNotSameAs(originalArray);
    }

    @Test
    @DisplayName("Empty searchArray returns a copy of the original")
    void replaceChars_emptySearchArray() {
        char[] originalArray = {'a', 'b', 'c'};

        char[] result = charArrayReplacer.replaceChars(
                originalArray,
                new char[]{},
                new char[]{'x'}
        );

        assertThat(originalArray).isEqualTo(result);
        assertThat(result).isNotSameAs(originalArray);
    }

    @Test
    @DisplayName("Empty originalArray returns empty array")
    void replaceChars_emptyOriginalArray() {
        char[] originalArray = {};

        char[] result = charArrayReplacer.replaceChars(
                originalArray,
                new char[]{'a'},
                new char[]{'b'}
        );

        assertThat(originalArray).isEqualTo(result);
        assertThat(result).isNotSameAs(originalArray);
    }

    @Test
    @DisplayName("No occurrences returns a copy identical to original")
    void replaceChars_noOccurrences() {
        char[] originalArray = {'1', '2', '3'};

        char[] result = charArrayReplacer.replaceChars(
                originalArray,
                new char[]{'x'},
                new char[]{'y'}
        );

        assertThat(originalArray).isEqualTo(result);
        assertThat(result).isNotSameAs(originalArray);
    }

    @Test
    @DisplayName("Single occurrence at start")
    void replaceChars_singleAtStart() {
        char[] originalArray = {'1', '2', '3', '4', '5'};

        char[] result = charArrayReplacer.replaceChars(
                originalArray,
                new char[]{'1', '2'},
                new char[]{'a', 'b', 'c'}
        );

        char[] expected = {'a', 'b', 'c', '3', '4', '5'};
        assertThat(result)
                .isEqualTo(expected)
                .isNotEqualTo(originalArray)
                .isNotSameAs(originalArray);
    }

    @Test
    @DisplayName("Single occurrence in middle")
    void replaceChars_singleInMiddle() {
        char[] originalArray = {'x', '1', '2', 'y'};

        char[] result = charArrayReplacer.replaceChars(
                originalArray,
                new char[]{'1', '2'},
                new char[]{'A'}
        );

        char[] expected = {'x', 'A', 'y'};
        assertThat(result)
                .isEqualTo(expected)
                .isNotEqualTo(originalArray)
                .isNotSameAs(originalArray);
    }

    @Test
    @DisplayName("Single occurrence at end")
    void replaceChars_singleAtEnd() {
        char[] originalArray = {'a', 'b', '1', '2'};

        char[] result = charArrayReplacer.replaceChars(
                originalArray,
                new char[]{'1', '2'},
                new char[]{'Z', 'Z'}
        );

        char[] expected = {'a', 'b', 'Z', 'Z'};
        assertThat(result)
                .isEqualTo(expected)
                .isNotEqualTo(originalArray)
                .isNotSameAs(originalArray);
    }

    @Test
    @DisplayName("Multiple non‑overlapping occurrences")
    void replaceChars_multipleOccurrences() {
        char[] originalArray = {'a', 'a', 'b', 'a', 'a', 'c'};

        char[] result = charArrayReplacer.replaceChars(
                originalArray,
                new char[]{'a', 'a'},
                new char[]{'x'}
        );

        // "a a b a a c" -> "x b x c"
        char[] expected = {'x', 'b', 'x', 'c'};
        assertThat(result)
                .isEqualTo(expected)
                .isNotEqualTo(originalArray)
                .isNotSameAs(originalArray);
    }

    @Test
    @DisplayName("Overlapping pattern sanity (non‑overlapping replace)")
    void replaceChars_overlappingPattern() {
        char[] originalArray = {'a', 'a', 'a'};

        char[] result = charArrayReplacer.replaceChars(
                originalArray,
                new char[]{'a', 'a'},
                new char[]{'B'}
        );

        //Only the first "aa" is replaced, leaving the last 'a'
        char[] expected = {'B', 'a'};
        assertThat(result)
                .isEqualTo(expected)
                .isNotEqualTo(originalArray)
                .isNotSameAs(originalArray);
    }

    @Test
    @DisplayName("Search same as entire array")
    void replaceChars_entireArrayMatch() {
        char[] originalArray = {'1', '2', '3'};

        char[] result = charArrayReplacer.replaceChars(
                originalArray,
                new char[]{'1', '2', '3'},
                new char[]{'X', 'Y'}
        );

        char[] expected = {'X', 'Y'};
        assertThat(result)
                .isEqualTo(expected)
                .isNotEqualTo(originalArray)
                .isNotSameAs(originalArray);
    }

    @Test
    @DisplayName("Empty replacementArray removes search occurrences")
    void replaceChars_emptyReplacementArray() {
        char[] originalArray = {'a', 'b', 'a'};

        char[] result = charArrayReplacer.replaceChars(
                originalArray,
                new char[]{'a'},
                new char[]{}
        );

        char[] expected = {'b'};
        assertThat(result)
                .isEqualTo(expected)
                .isNotEqualTo(originalArray)
                .isNotSameAs(originalArray);
    }

    @Test
    @DisplayName("Null originalArray expect IllegalArgumentException")
    void replaceChars_nullOriginalArray() {
        assertThatThrownBy(() -> charArrayReplacer.replaceChars(
                null,
                new char[]{'a'},
                new char[]{'b'}
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Original array or replacement array must not be null");
    }

    @Test
    @DisplayName("Null replacementArray expect IllegalArgumentException")
    void replaceChars_nullReplacementArray() {
        assertThatThrownBy(() -> charArrayReplacer.replaceChars(
                new char[]{'a'},
                new char[]{'a'},
                null
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Original array or replacement array must not be null");
    }
}