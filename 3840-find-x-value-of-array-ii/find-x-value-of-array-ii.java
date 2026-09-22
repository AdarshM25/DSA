import java.util.*;

class Solution {

    static class Node {
        int prod;
        int[] cnt;

        Node(int k) {
            cnt = new int[k];
        }
    }

    int k;
    Node[] tree;

    private Node merge(Node left, Node right) {
        Node res = new Node(k);

        res.prod = (int)((long) left.prod * right.prod % k);

        // Prefixes entirely inside the left part
        for (int r = 0; r < k; r++) {
            res.cnt[r] += left.cnt[r];
        }

        // Whole left + prefix of right
        for (int r = 0; r < k; r++) {
            if (right.cnt[r] > 0) {
                int newR = (int)((long) left.prod * r % k);
                res.cnt[newR] += right.cnt[r];
            }
        }

        return res;
    }

    private Node makeNode(int value) {
        Node node = new Node(k);

        int r = value % k;
        node.prod = r;
        node.cnt[r] = 1;

        return node;
    }

    private void build(int index, int l, int r, int[] nums) {
        if (l == r) {
            tree[index] = makeNode(nums[l]);
            return;
        }

        int mid = (l + r) / 2;

        build(index * 2, l, mid, nums);
        build(index * 2 + 1, mid + 1, r, nums);

        tree[index] = merge(tree[index * 2], tree[index * 2 + 1]);
    }

    private void update(int index, int l, int r, int pos, int value) {
        if (l == r) {
            tree[index] = makeNode(value);
            return;
        }

        int mid = (l + r) / 2;

        if (pos <= mid) {
            update(index * 2, l, mid, pos, value);
        } else {
            update(index * 2 + 1, mid + 1, r, pos, value);
        }

        tree[index] = merge(tree[index * 2], tree[index * 2 + 1]);
    }

    private Node query(int index, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            return tree[index];
        }

        int mid = (l + r) / 2;

        if (qr <= mid) {
            return query(index * 2, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(index * 2 + 1, mid + 1, r, ql, qr);
        }

        Node left = query(index * 2, l, mid, ql, qr);
        Node right = query(index * 2 + 1, mid + 1, r, ql, qr);

        return merge(left, right);
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.k = k;

        int n = nums.length;
        tree = new Node[4 * n];

        build(1, 0, n - 1, nums);

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // Permanent update
            update(1, 0, n - 1, index, value);

            // Query nums[start ... n-1]
            Node res = query(1, 0, n - 1, start, n - 1);

            result[i] = res.cnt[x];
        }

        return result;
    }
}