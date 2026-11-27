class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] dp = new int[budget + 1];

        for(int i = 0; i < n; i++){
            int factor = items[i][0];
            int price = items[i][1];
            int free = 0;

            for(int j = 0; j < n; j++){
                if(i != j && items[j][0] % factor == 0){
                    free++;
                }
            }
            int[] activated = new int[budget + 1];
            java.util.Arrays.fill(activated, - 1);

            for(int b = price; b <= budget; b++){
                activated[b] = dp[b - price] + 1 + free;
            }
            for(int b = price; b <= budget; b++){
                if(activated[b - price] != -1){
                    activated[b] = Math.max(
                        activated[b],
                        activated[b - price] + 1
                    );
                }
            }
            for(int b = 0; b <= budget; b++){
                dp[b] = Math.max(dp[b],activated[b]);
            }
        }
        int ans = 0;
        for(int val : dp){
            ans = Math.max(ans, val);
        }
        return ans;
    }
}
