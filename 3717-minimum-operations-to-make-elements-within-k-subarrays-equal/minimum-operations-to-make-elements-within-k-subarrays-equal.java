import java.util.*;

class Solution {
    int n;
    int[] bitCount;
    long[] bitSum;

    void update(int i, int count, long sum) {
        while (i <= n) {
            bitCount[i] += count;
            bitSum[i] += sum;
            i += i & -i;
        }
    }

    int queryCount(int i) {
        int result = 0;
        while (i > 0) {
            result += bitCount[i];
            i -= i & -i;
        }
        return result;
    }

    long querySum(int i) {
        long result = 0;
        while (i > 0) {
            result += bitSum[i];
            i -= i & -i;
        }
        return result;
    }

    int findKth(int k) {
        int idx = 0;

        for (int step = Integer.highestOneBit(n);
             step > 0; step >>= 1) {
            int next = idx + step;

            if (next <= n && bitCount[next] < k) {
                idx = next;
                k -= bitCount[next];
            }
        }

        return idx + 1;
    }

    public long minOperations(int[] nums, int x, int k) {
        int len = nums.length;

        // Coordinate compression
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int[] values = Arrays.stream(sorted).distinct().toArray();
        n = values.length;

        bitCount = new int[n + 1];
        bitSum = new long[n + 1];

        int[] rank = new int[len];

        for (int i = 0; i < len; i++) {
            rank[i] = Arrays.binarySearch(values, nums[i]) + 1;
        }

        // Calculate cost for every window of size x
        long[] cost = new long[len - x + 1];

        for (int i = 0; i < x; i++) {
            update(rank[i], 1, nums[i]);
        }

        for (int i = 0; i <= len - x; i++) {
            int medianIndex = findKth((x + 1) / 2);
            long median = values[medianIndex - 1];

            int leftCount = queryCount(medianIndex);
            long leftSum = querySum(medianIndex);

            long totalSum = querySum(n);

            cost[i] = median * leftCount - leftSum
                    + (totalSum - leftSum)
                    - median * (x - leftCount);

            if (i + x < len) {
                update(rank[i], -1, -nums[i]);
                update(rank[i + x], 1, nums[i + x]);
            }
        }

        // DP: minimum cost to select j non-overlapping windows
        long INF = Long.MAX_VALUE / 4;

        long[] prev = new long[len + 1];
        long[] curr = new long[len + 1];

        Arrays.fill(prev, 0);

        for (int j = 1; j <= k; j++) {
            Arrays.fill(curr, INF);

            for (int i = 1; i <= len; i++) {
                // Do not select a window ending at i
                curr[i] = curr[i - 1];

                // Select window [i-x, i-1]
                if (i >= x && prev[i - x] != INF) {
                    curr[i] = Math.min(
                        curr[i],
                        prev[i - x] + cost[i - x]
                    );
                }
            }

            long[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[len];
    }
}