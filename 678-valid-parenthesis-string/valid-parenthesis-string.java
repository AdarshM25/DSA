class Solution {
    public boolean checkValidString(String s) {
        int low = 0;
        int high = 0;

        for (char c : s.toCharArray()) {

            if (c == '(') {
                low++;
                high++;
            } 
            else if (c == ')') {
                low--;
                high--;
            } 
            else { // '*'
                low--;   // '*' can be ')'
                high++;  // '*' can be '('
            }

            // Too many ')' no matter how we use '*'
            if (high < 0) {
                return false;
            }

            // low cannot be negative
            // because '*' can also be empty or '('
            low = Math.max(low, 0);
        }

        return low == 0;
    }
}