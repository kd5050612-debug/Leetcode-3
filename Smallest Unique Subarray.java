
class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;

        int[] sa = new int[n];
        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            sa[i] = i;
            rank[i] = nums[i];
        }

        sa = countingSortValues(sa, nums);

        int classes = 0;
        int[] newRank = new int[n];

        for (int i = 0; i < n; i++) {
            if (i == 0 || nums[sa[i]] != nums[sa[i - 1]]) {
                classes++;
            }
            newRank[sa[i]] = classes - 1;
        }

        rank = newRank;

        for (int len = 1; len < n && classes < n; len <<= 1) {
            sa = countingSort(sa, rank, len, classes);
            sa = countingSort(sa, rank, 0, classes);

            newRank = new int[n];
            int newClasses = 1;
            newRank[sa[0]] = 0;

            for (int i = 1; i < n; i++) {
                int a = sa[i - 1];
                int b = sa[i];

                if (rank[a] != rank[b] ||
                    (a + len < n ? rank[a + len] : -1) !=
                    (b + len < n ? rank[b + len] : -1)) {
                    newClasses++;
                }

                newRank[b] = newClasses - 1;
            }

            rank = newRank;
            classes = newClasses;
        }

        int[] lcp = new int[n];
        int h = 0;

        for (int i = 0; i < n; i++) {
            int r = rank[i];

            if (r == 0) {
                h = 0;
                continue;
            }

            int j = sa[r - 1];

            while (i + h < n && j + h < n &&
                   nums[i + h] == nums[j + h]) {
                h++;
            }

            lcp[r] = h;

            if (h > 0) {
                h--;
            }
        }

        int ans = n;

        for (int r = 0; r < n; r++) {
            int i = sa[r];

            int left = lcp[r];
            int right = (r + 1 < n) ? lcp[r + 1] : 0;

            int uniqueLen = Math.max(left, right) + 1;

            if (uniqueLen <= n - i) {
                ans = Math.min(ans, uniqueLen);
            }
        }

        return ans;
    }

    private int[] countingSortValues(int[] sa, int[] nums) {
        int max = 100000;
        int[] count = new int[max + 1];

        for (int i : sa) {
            count[nums[i]]++;
        }

        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }

        int[] sorted = new int[sa.length];

        for (int i = sa.length - 1; i >= 0; i--) {
            int idx = sa[i];
            sorted[--count[nums[idx]]] = idx;
        }

        return sorted;
    }

    private int[] countingSort(int[] sa, int[] rank, int offset, int classes) {
        int n = sa.length;
        int[] count = new int[classes + 1];

        for (int idx : sa) {
            int key = (offset == 0)
                ? rank[idx] + 1
                : (idx + offset < n ? rank[idx + offset] + 1 : 0);
            count[key]++;
        }

        for (int i = 1; i <= classes; i++) {
            count[i] += count[i - 1];
        }

        int[] sorted = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            int idx = sa[i];
            int key = (offset == 0)
                ? rank[idx] + 1
                : (idx + offset < n ? rank[idx + offset] + 1 : 0);

            sorted[--count[key]] = idx;
        }

        return sorted;
    }
}
