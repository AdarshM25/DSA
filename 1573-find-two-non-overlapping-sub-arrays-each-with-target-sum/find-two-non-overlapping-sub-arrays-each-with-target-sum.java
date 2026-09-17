class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int INF = n + 1;
        
        int[] best = new int[n];
        for (int i = 0; i < n; i++) {
            best[i] = INF;
        }

        int ans = INF;
        int left = 0;
        int sum = 0;
        int minLength = INF;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            while (sum > target) {
                sum -= arr[left++];
            }

            if (sum == target) {
                int len = right - left + 1;

                // Previous non-overlapping subarray
                if (left > 0 && best[left - 1] != INF) {
                    ans = Math.min(ans, len + best[left - 1]);
                }

                minLength = Math.min(minLength, len);
            }

            best[right] = minLength;
        }

        return ans == INF ? -1 : ans;
    }
}