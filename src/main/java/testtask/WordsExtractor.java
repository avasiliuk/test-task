package testtask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordsExtractor {
    private final Pattern wordPattern;

    public WordsExtractor() {
        this(Pattern.compile("[A-Za-z0-9]+"));
    }

    public WordsExtractor(final Pattern wordPattern) {
        Objects.requireNonNull(wordPattern, "wordPattern must not be null");
        this.wordPattern = wordPattern;
    }

    @NotNull
    public String[] extractWords(String s, boolean uniqueOnly) {
        if (s == null) {
            return new String[]{};
        }
        final Matcher matcher = wordPattern.matcher(s);
        if (uniqueOnly) {
            final LinkedHashSet<String> uniqueWords = new LinkedHashSet<>();
            while (matcher.find()) {
                uniqueWords.add(matcher.group());
            }
            return uniqueWords.toArray(new String[0]);
        } else {
            ArrayList<String> words = new ArrayList<>();
            while (matcher.find()) {
                words.add(matcher.group());
            }
            return words.toArray(new String[0]);
        }
    }

    @NotNull
    public String[] extractWords(String s) {
        return extractWords(s, false);
    }

}
