import java.util.*;

class Solution {

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        // [left, right, weight, originalIndex]
        int[][] a = new int[n][4];

        for (int i = 0; i < n; i++) {
            a[i][0] = intervals.get(i).get(0);
            a[i][1] = intervals.get(i).get(1);
            a[i][2] = intervals.get(i).get(2);
            a[i][3] = i;
        }

        // Sort by starting point
        Arrays.sort(a, (x, y) -> {
            if (x[0] != y[0]) {
                return Integer.compare(x[0], y[0]);
            }
            return Integer.compare(x[1], y[1]);
        });

        int[] starts = new int[n];

        for (int i = 0; i < n; i++) {
            starts[i] = a[i][0];
        }

        // First interval whose start > current right
        int[] next = new int[n];

        for (int i = 0; i < n; i++) {
            next[i] = upperBound(starts, a[i][1]);
        }

        State[][] dp = new State[n + 1][5];

        // Choosing 0 intervals
        for (int i = 0; i <= n; i++) {
            dp[i][0] = new State(0L, new int[0]);
        }

        // No intervals remaining
        for (int k = 1; k <= 4; k++) {
            dp[n][k] = new State(0L, new int[0]);
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int k = 1; k <= 4; k++) {

                // Don't take interval i
                State skip = dp[i + 1][k];

                // Take interval i
                State nextState = dp[next[i]][k - 1];

                int[] indices =
                    new int[nextState.indices.length + 1];

                indices[0] = a[i][3];

                System.arraycopy(
                    nextState.indices,
                    0,
                    indices,
                    1,
                    nextState.indices.length
                );

                // Indices must be sorted for lexicographical comparison
                Arrays.sort(indices);

                State take = new State(
                    (long) a[i][2] + nextState.score,
                    indices
                );

                dp[i][k] = better(skip, take);
            }
        }

        return dp[0][4].indices;
    }

    private int upperBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    private State better(State a, State b) {
        // Higher score is better
        if (a.score != b.score) {
            return a.score > b.score ? a : b;
        }

        // Same score -> lexicographically smaller indices
        return compareLexicographically(a.indices, b.indices) <= 0
                ? a
                : b;
    }

    private int compareLexicographically(int[] a, int[] b) {
        int len = Math.min(a.length, b.length);

        for (int i = 0; i < len; i++) {
            if (a[i] != b[i]) {
                return Integer.compare(a[i], b[i]);
            }
        }

        return Integer.compare(a.length, b.length);
    }

    static class State {
        long score;
        int[] indices;

        State(long score, int[] indices) {
            this.score = score;
            this.indices = indices;
        }
    }
}