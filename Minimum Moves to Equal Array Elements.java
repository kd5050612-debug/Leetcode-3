class Solution {
    public int minMoves(int[] nums) {
        int min = nums[0];
        int i = 0;

        while(i < nums.length){
            if(nums[i] < min){
                min = nums[i];
            }
            i++;
        }
        int moves = 0;
        i = 0;

        while(i < nums.length){
            moves += nums[i] - min;
            i++;
        }
        return moves;
    }
}
