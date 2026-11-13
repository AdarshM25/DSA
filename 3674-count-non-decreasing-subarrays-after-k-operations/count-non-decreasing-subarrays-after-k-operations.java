
import java.util.*;

class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        long ans = 0;
        long cost = 0;

        // Stores {value, count} pairs
        Deque<int[]> dq = new ArrayDeque<>();

        int j = nums.length - 1;

        for (int i = nums.length - 1; i >= 0; i--) {
            int num = nums[i];
            int count = 1;

            // Merge smaller values into the current value
            while (!dq.isEmpty() && dq.peekLast()[0] < num) {
                int[] last = dq.pollLast();

                cost += (long) (num - last[0]) * last[1];
                count += last[1];
            }

            dq.offerLast(new int[]{num, count});

            // Shrink the window while cost exceeds k
            while (cost > k) {
                int[] first = dq.pollFirst();

                cost -= first[0] - nums[j];
                j--;

                if (first[1] > 1) {
                    dq.offerFirst(new int[]{
                        first[0], first[1] - 1
                    });
                }
            }

            // All subarrays [i...j], [i+1...j], etc. are valid
            ans += j - i + 1;
        }

        return ans;
    }
}
