import java.util.*;

class Solution {
    String s;
    int pos;

    public List<String> braceExpansionII(String expression) {
        s = expression;
        pos = 0;

        Set<String> set = parseExpression();

        List<String> ans = new ArrayList<>(set);
        Collections.sort(ans);

        return ans;
    }

    // Handles union: a,b,c
    private Set<String> parseExpression() {
        Set<String> result = parseTerm();

        while (pos < s.length() && s.charAt(pos) == ',') {
            pos++;
            result.addAll(parseTerm());
        }

        return result;
    }

    // Handles concatenation: ab{c,d}ef
    private Set<String> parseTerm() {
        Set<String> result = new HashSet<>();
        result.add("");

        while (pos < s.length()
                && s.charAt(pos) != '}'
                && s.charAt(pos) != ',') {

            Set<String> next;

            if (s.charAt(pos) == '{') {
                pos++; // skip '{'
                next = parseExpression();
                pos++; // skip '}'
            } else {
                next = new HashSet<>();
                next.add(String.valueOf(s.charAt(pos)));
                pos++;
            }

            result = concatenate(result, next);
        }

        return result;
    }

    // Cartesian product of two sets
    private Set<String> concatenate(Set<String> a, Set<String> b) {
        Set<String> result = new HashSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}