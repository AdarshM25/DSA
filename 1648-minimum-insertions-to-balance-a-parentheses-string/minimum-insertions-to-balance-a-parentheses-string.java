class Solution {
    public int minInsertions(String s) {
        int ans = 0;
        int open = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                open++;
            } else {
                // Check whether we have a pair of ')'
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    i++;
                } else {
                    ans++; // Insert a missing ')'
                }

                if (open > 0) {
                    open--;
                } else {
                    ans++; // Insert a missing '('
                }
            }
        }

        return ans + open * 2;
    }
}