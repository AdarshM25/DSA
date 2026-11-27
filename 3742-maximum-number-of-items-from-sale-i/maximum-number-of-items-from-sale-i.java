
import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;

        // Calculate benefit of each item type
        int[] benefit = new int[n];

        for (int i = 0; i < n; i++) {
            benefit[i] = 1;

            for (int j = 0; j < n; j++) {
                if (i != j &&
                    items[j][0] % items[i][0] == 0) {
                    benefit[i]++;
                }
            }
        }

        // Group items by price
        List<Integer>[] groups = new ArrayList[1501];

        for (int i = 0; i <= 1500; i++) {
            groups[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            groups[items[i][1]].add(i);
        }

        int NEG = -1000000000;

        int[] dpAll = new int[budget + 1];
        Arrays.fill(dpAll, NEG);
        dpAll[0] = 0;

        int answer = 0;

        // Try every possible minimum selected price
        for (int p = 1500; p >= 1; p--) {

            if (groups[p].isEmpty()) {
                continue;
            }

            int[] dpCurrent = new int[budget + 1];
            Arrays.fill(dpCurrent, NEG);

            // Select at least one item with price p
            for (int idx : groups[p]) {
                int cost = items[idx][1];
                int value = benefit[idx];

                for (int c = budget; c >= cost; c--) {

                    // Add to an existing selection at price p
                    if (dpCurrent[c - cost] != NEG) {
                        dpCurrent[c] = Math.max(
                            dpCurrent[c],
                            dpCurrent[c - cost] + value
                        );
                    }

                    // Start selection using more expensive items
                    if (dpAll[c - cost] != NEG) {
                        dpCurrent[c] = Math.max(
                            dpCurrent[c],
                            dpAll[c - cost] + value
                        );
                    }
                }
            }

            // Buy additional copies of the cheapest
            // selected item type
            for (int c = 0; c <= budget; c++) {
                if (dpCurrent[c] != NEG) {
                    int total = dpCurrent[c] +
                                (budget - c) / p;

                    answer = Math.max(answer, total);
                }
            }

            // Update selections with higher prices
            for (int c = 0; c <= budget; c++) {
                dpAll[c] = Math.max(dpAll[c], dpCurrent[c]);
            }
        }

        return answer;
    }
}
