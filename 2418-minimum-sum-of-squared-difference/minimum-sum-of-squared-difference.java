import java.util.*;

class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        long[] diff = new long[n];

        long total = 0;
        long max = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            total += diff[i];
            max = Math.max(max, diff[i]);
        }

        long k = (long) k1 + k2;

        // If all differences can be reduced to zero
        if (k >= total) {
            return 0;
        }

        // Find the largest level to which differences can be reduced
        long left = 0, right = max;

        while (left < right) {
            long mid = left + (right - left) / 2;

            long needed = 0;
            for (long d : diff) {
                if (d > mid) {
                    needed += d - mid;
                }
            }

            if (needed <= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        long level = left;
        long used = 0;
        long ans = 0;

        for (long d : diff) {
            if (d > level) {
                used += d - level;
                ans += level * level;
            } else {
                ans += d * d;
            }
        }

        // Use remaining operations to reduce differences at the level by 1
        long remaining = k - used;

        for (long d : diff) {
            if (remaining == 0) break;

            if (d >= level && d > 0) {
                ans -= level * level - (level - 1) * (level - 1);
                remaining--;
            }
        }

        return ans;
    }
}