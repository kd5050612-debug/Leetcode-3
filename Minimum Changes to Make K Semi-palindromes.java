class Solution {
    public int minimumChanges(String s, int k) {
       int n = s.length();
       int[][] cost = new int[n][n];

       for(int len = 2; len <= n; len++){
        for(int i = 0; i + len <= n; i++){
            int j = i + len - 1;
            cost[i][j] = Integer.MAX_VALUE;

            for(int d = 1; d < len; d++){
                if(len % d != 0){
                    continue;
                }

                int changes = 0;

                for(int r = 0; r < d; r++){
                    int left = i + r;
                    int right = i + r +((len - 1 - r) / d) * d;

                    while(left < right){
                        if(s.charAt(left) != s.charAt(right)){
                            changes++;
                        }

                        left += d;
                        right -= d;
                    }
                }
                cost[i][j] = Math.min(cost[i][j], changes);
            }
        }
       }
       int INF = 1000000;
       int[][] dp = new int[k + 1][n + 1];

       for(int[] row : dp){
        Arrays.fill(row, INF);
       } 

       dp[0][0] = 0;

       for(int groups = 1; groups <= k; groups++){
        for(int end = groups * 2; end <= n; end++){
            for(int start = (groups - 1) * 2; start <= end - 2;start++){
                if(dp[groups - 1][start] == INF){
                    continue;
                }

                dp[groups][end] = Math.min(
                    dp[groups][end],
                    dp[groups - 1][start] + cost[start][end - 1]
                );
            }
        }
       }
        return dp[k][n];
       }
    }
