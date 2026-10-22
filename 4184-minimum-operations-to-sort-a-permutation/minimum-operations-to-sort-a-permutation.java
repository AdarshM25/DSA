class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;

        int zero = -1;
        int last = -1;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) zero = i;
            if (nums[i] == n - 1) last = i;
        }

        boolean ascending = true;
        boolean descending = true;

        for (int i = 0; i < n; i++) {
            if (nums[i] != (nums[0] + i) % n) {
                ascending = false;
            }

            if (nums[i] != (nums[0] - i + n) % n) {
                descending = false;
            }
        }

        if (ascending) {
            return Math.min(zero, n - zero + 2);
        }

        if (descending) {
            int reverseRotations = n - 1 - zero;
            int rotateThenReverse = last + 1;

            return Math.min(reverseRotations + 1, rotateThenReverse);
        }

        return -1;
    }
}