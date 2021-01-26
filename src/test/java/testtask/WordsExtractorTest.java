package testtask;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.emptyArray;

class WordsExtractorTest {

    @Test
    @DisplayName("if null string is given empty array must be returned")
    void testExtractNull() {
        //GIVEN
        final WordsExtractor wordsExtractor = new WordsExtractor();
        //WHEN
        final @NotNull String[] result = wordsExtractor.extractWords(null);
        //THEN
        assertThat(result, emptyArray());
    }

    @Test
    @DisplayName("with default settings must extract alphanumeric words with preserved order")
    void testDefaultSettings() {
        //GIVEN
        final WordsExtractor wordsExtractor = new WordsExtractor();
        //WHEN
        final @NotNull String[] result = wordsExtractor.extractWords("alPha beta gamma1 - delta");
        //THEN
        assertThat(result, arrayContaining("alPha", "beta", "gamma1", "delta"));
    }

    @Test
    @DisplayName("with updated settings can extract alpha only words started with capital letter")
    void testExtractAlphaWordsWithFirstCapitalLetter() {
        //GIVEN
        final WordsExtractor wordsExtractor = new WordsExtractor(Pattern.compile("(^|(?<=[^A-Za-z]))[A-Z][A-Za-z]*"));
        //WHEN
        final @NotNull String[] result = wordsExtractor.extractWords("Hello_alPha beta gamma1 - delta+Word");
        //THEN
        assertThat(result, arrayContaining("Hello", "Word"));
    }

    @Test
    @DisplayName("if unique flag set to true must return only unique words")
    void testUniqueFlag() {
        //GIVEN
        final WordsExtractor wordsExtractor = new WordsExtractor();
        //WHEN
        final @NotNull String[] result = wordsExtractor.extractWords("alPha beta gamma1 alPha alPha- delta delta delta", true);
        //THEN
        assertThat(result, arrayContaining("alPha", "beta", "gamma1", "delta"));
    }

    @Test
    @DisplayName("must be able to extract only russian words from text")
    void testExtractRussianWords() {
        //GIVEN
        final WordsExtractor wordsExtractor = new WordsExtractor(Pattern.compile("[а-яА-Я]+"));
        //WHEN
        final @NotNull String[] result = wordsExtractor.extractWords("alPha один gamma1 два alPha- три delta delta");
        //THEN
        assertThat(result, arrayContaining("один", "два", "три"));
    }

}