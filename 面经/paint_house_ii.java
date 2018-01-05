public class paint_house_ii {
    public static int minCostII(int[][] costs) {
        if (costs == null || costs.length == 0) return 0;
        int n = costs.length, k = costs[0].length;
        // min1: index of 1st-smallest cost till previous house
        // min2: index of 2nd-smallest cost till previous house
        // two pointers, if current color same as min1's color, use min2; otherwise min1 is good
        int min1 = -1, min2 = -1;

        // iterate across houses
        for (int i = 0; i < n; i++) {
            int last1 = min1, last2 = min2;
            min1 = -1; min2 = -1;
            // iterate across colors
            for (int j = 0; j < k; j++) {
                // current color j is different to last min1 color
                if (j != last1) {
                    costs[i][j] += last1 < 0 ? 0 : costs[i - 1][last1];
                } else {
                    costs[i][j] += last2 < 0 ? 0 : costs[i - 1][last2];
                }

                // find the indices of 1st and 2nd smallest cost of painting current house i
                // update min1 and min2
                if (min1 < 0 || costs[i][j] < costs[i][min1]) {
                    min2 = min1;
                    min1 = j;
                } else if (min2 < 0 || costs[i][j] < costs[i][min2]) {
                    min2 = j;
                }
            }
//            for (int p = 0; p < n; p++) {
//                for (int q = 0; q < k; q++) {
//                    System.out.print(costs[p][q] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println();
        }
        return costs[n - 1][min1];
    }

    public static void main(String[] args) {
        int[][] costs = {{1,2,3},{3,2,1},{4,6,1},{3,1,6}};
        System.out.println(minCostII(costs));
    }
}
