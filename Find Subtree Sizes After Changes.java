
class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        List<Integer>[] tree = new ArrayList[n];
        List<Integer>[] newTree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
            newTree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        int[] nearest = new int[26];
        Arrays.fill(nearest, -1);

        dfsBuild(0, tree, newTree, parent, s, nearest);

        int[] ans = new int[n];
        dfsSize(0, newTree, ans);

        return ans;
    }

    private void dfsBuild(int node, List<Integer>[] tree,
                         List<Integer>[] newTree, int[] parent,
                         String s, int[] nearest) {

        int c = s.charAt(node) - 'a';
        int old = nearest[c];

        if (node != 0) {
            int newParent = (old != -1) ? old : parent[node];
            newTree[newParent].add(node);
        }

        nearest[c] = node;

        for (int child : tree[node]) {
            dfsBuild(child, tree, newTree, parent, s, nearest);
        }

        nearest[c] = old;
    }

    private int dfsSize(int node, List<Integer>[] tree, int[] ans) {
        int size = 1;

        for (int child : tree[node]) {
            size += dfsSize(child, tree, ans);
        }

        ans[node] = size;
        return size;
    }
}
