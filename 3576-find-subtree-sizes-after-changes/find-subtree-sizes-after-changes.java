import java.util.*;

class Solution {
    List<Integer>[] tree;
    int[] newParent;
    int[] ans;
    String s;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        this.s = s;

        tree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        // Build the original tree
        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        newParent = parent.clone();

        // Track the closest ancestor for each character
        int[] last = new int[26];
        Arrays.fill(last, -1);

        dfsChange(0, last);

        // Build the modified tree
        List<Integer>[] newTree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            newTree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            newTree[newParent[i]].add(i);
        }

        ans = new int[n];

        // Calculate subtree sizes
        dfsSize(0, newTree);

        return ans;
    }

    private void dfsChange(int node, int[] last) {
        int ch = s.charAt(node) - 'a';

        int previous = last[ch];

        if (previous != -1) {
            newParent[node] = previous;
        }

        last[ch] = node;

        for (int child : tree[node]) {
            dfsChange(child, last);
        }

        // Backtrack to restore the previous ancestor
        last[ch] = previous;
    }

    private int dfsSize(int node, List<Integer>[] newTree) {
        int size = 1;

        for (int child : newTree[node]) {
            size += dfsSize(child, newTree);
        }

        ans[node] = size;
        return size;
    }
}