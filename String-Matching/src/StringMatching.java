// you can get this from here:
// https://code.google.com/p/guava-libraries/
import com.google.common.base.CharMatcher;

public class StringMatching {
    /** the text you want to search in */
    private final String text;

    private final int n;

    public StringMatching(String text) {
        assert (text != null);
        assert (text.length() <= Integer.MAX_VALUE);
        this.text = text;
        this.n = text.length();
    }

    private boolean isMatchAt(String pattern, int position) {
        boolean match = true;

        for (int j = 0; j < pattern.length(); j++) {
            if (text.charAt(position + j) != pattern.charAt(j)) {
                match = false;
                break;
            }
        }

        return match;
    }

    /**
     * Search for pattern in text.
     * @param pattern the string you want to search
     * @return The position of the first character of pattern in the
     *         first occurrence in text.
     *         If pattern is not in the text, then -1.
     */
    public int naiveStringMatching(String pattern) {
        int m = pattern.length();

        for (int s = 0; s <= n - m; s++) {
            if (isMatchAt(pattern, s)) {
                return s;
            }
        }

        return -1;
    }

    /**
     * This method returns a long for an ASCII-string.
     * @param s
     * @return
     */
    private long getLongFromString(String s) {
        assert (CharMatcher.ASCII.matchesAllOf(s));

        long representation = 0;

        // What happens if s is longer than
        // log(Long.MAX_VALUE) / log(128) = 8 characters?
        for (int i = 0; i < s.length(); i++) {
            representation += s.charAt(i) * Math.pow(128, s.length());
        }

        return representation;
    }

    /**
     * Search for pattern in text with Rabin-Karps method
     * @param pattern the string you want to search
     * @return The position of the first character of pattern in the
     *         first occurrence in text.
     *         If pattern is not in the text, then -1.
     */
    public int rabinKarp(String pattern, int q) {
        // TODO: Doesn't work by now.
        int m = pattern.length();
        long pTmp = getLongFromString(pattern);
        long tTmp = getLongFromString(text.substring(0, m)) % q;

        for (int s = 0; s < n - m; s++) {
            if (pTmp == tTmp && isMatchAt(pattern, s)) {
                return s;
            }

            tTmp = ((tTmp - text.charAt(s) * ((long) Math.pow(128, m - 1))) * 128 + text
                    .charAt(s + m))
                    % q;
        }

        if (isMatchAt(pattern, n - m - 1)) {
            return n - m - 1;
        }

        return -1;
    }

    private int suffixFunction(String pattern, String w) {
        int maxMatch = Math.min(w.length(), pattern.length());
        String patternPart;
        String wordPart;
        do {
            patternPart = pattern.substring(0, maxMatch);
            wordPart = w.substring(w.length() - maxMatch, w.length());
            maxMatch--;
        } while (!patternPart.equals(wordPart) && maxMatch >= 0);
        return maxMatch + 1;
    }

    /**
     * Search for pattern in text with an automaton
     * @param pattern the string you want to search
     * @return The position of the first character of pattern in the
     *         first occurrence in text.
     *         If pattern is not in the text, then -1.
     */
    public int stringMatchingAutomaton(String pattern) {
        return -1;
    }
}
