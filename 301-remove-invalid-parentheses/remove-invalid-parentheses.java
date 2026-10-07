import java.util.*;

class Solution {
    Set<String> result = new HashSet<>();

    public List<String> removeInvalidParentheses(String s) {
        int leftRemove = 0;
        int rightRemove = 0;

        // Find minimum number of removals
        for (char c : s.toCharArray()) {
            if (c == '(') {
                leftRemove++;
            } else if (c == ')') {
                if (leftRemove > 0) {
                    leftRemove--;
                } else {
                    rightRemove++;
                }
            }
        }

        dfs(s, 0, 0, leftRemove, rightRemove, new StringBuilder());

        return new ArrayList<>(result);
    }

    private void dfs(String s, int index, int balance,
                     int leftRemove, int rightRemove,
                     StringBuilder current) {

        if (index == s.length()) {
            if (balance == 0 && leftRemove == 0 && rightRemove == 0) {
                result.add(current.toString());
            }
            return;
        }

        char c = s.charAt(index);

        if (c == '(') {

            // Remove '('
            if (leftRemove > 0) {
                dfs(s, index + 1, balance,
                    leftRemove - 1, rightRemove, current);
            }

            // Keep '('
            current.append(c);

            dfs(s, index + 1, balance + 1,
                leftRemove, rightRemove, current);

            current.deleteCharAt(current.length() - 1);

        } else if (c == ')') {

            // Remove ')'
            if (rightRemove > 0) {
                dfs(s, index + 1, balance,
                    leftRemove, rightRemove - 1, current);
            }

            // Keep ')' only if there is an unmatched '('
            if (balance > 0) {
                current.append(c);

                dfs(s, index + 1, balance - 1,
                    leftRemove, rightRemove, current);

                current.deleteCharAt(current.length() - 1);
            }

        } else {

            // Keep letters
            current.append(c);

            dfs(s, index + 1, balance,
                leftRemove, rightRemove, current);

            current.deleteCharAt(current.length() - 1);
        }
    }
}