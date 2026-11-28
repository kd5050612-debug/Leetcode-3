
class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int minPrice = Integer.MAX_VALUE;
        int maxFactor = 0;

        int[] freq = new int[n + 1];

        for (int[] item : items) {
            freq[item[0]]++;
            minPrice = Math.min(minPrice, item[1]);
            maxFactor = Math.max(maxFactor, item[0]);
        }

        int[] gain = new int[maxFactor + 1];

        for (int f = 1; f <= maxFactor; f++) {
            for (int j = f; j <= maxFactor; j += f) {
                gain[f] += freq[j];
            }
            gain[f]--;
        }

        Arrays.sort(items, (a, b) -> Integer.compare(a[1], b[1]));

        long ans = 0;

        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];

            if (price > 2L * minPrice) {
                break;
            }

            int bonusCount = gain[factor];

            if (bonusCount <= 0 || budget < price) {
                continue;
            }

            int buy = Math.min(bonusCount, budget / price);

            ans += 2L * buy;
            budget -= buy * price;
        }

        ans += budget / minPrice;

        return (int) ans;
    }
}
