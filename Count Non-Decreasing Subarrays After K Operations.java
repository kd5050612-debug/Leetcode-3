import java.util.*;

class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        Deque<int[]> dq = new ArrayDeque<>();

        long cost = 0;
        long ans = 0;
        int right = n - 1;

        for (int i = n - 1; i >= 0; i--) {
            int num = nums[i];
            int count = 1;

            while (!dq.isEmpty() && dq.peekLast()[0] < num) {
                int[] curr = dq.pollLast();

                cost += (long) (num - curr[0]) * curr[1];
                count += curr[1];
            }

            dq.offerLast(new int[]{num, count});

            while (cost > k) {
                int[] curr = dq.pollFirst();

                cost -= curr[0] - nums[right];
                right--;

                if (curr[1] > 1) {
                    dq.offerFirst(new int[]{curr[0], curr[1] - 1});
                }
            }

            ans += right - i + 1;
        }

        return ans;
    }
}
