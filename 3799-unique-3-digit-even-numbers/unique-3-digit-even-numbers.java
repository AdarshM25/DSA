class Solution {
    public int totalNumbers(int[] digits) {
        int[] count = new int[10];

        for (int d : digits) {
            count[d]++;
        }

        int ans = 0;

        for (int num = 100; num <= 999; num++) {
            // Must be even
            if (num % 2 != 0) {
                continue;
            }

            int a = num / 100;
            int b = (num / 10) % 10;
            int c = num % 10;

            // Check whether we have enough copies of each digit
            int[] need = new int[10];
            need[a]++;
            need[b]++;
            need[c]++;

            boolean possible = true;

            for (int d = 0; d <= 9; d++) {
                if (need[d] > count[d]) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                ans++;
            }
        }

        return ans;
    }
}