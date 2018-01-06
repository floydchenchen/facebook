/*
*
* 121. Best Time to Buy and Sell Stock
* https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
* If you were only permitted to complete at most one transaction (ie, buy one and sell one share of the stock),
* design an algorithm to find the maximum profit.
* 简单版，一次交易
*
* */

package tag;

public class best_time_to_buy_and_sell_stock {
    // 121. Best Time to Buy and Sell Stock (only one transaction)
    // Kadane's Algorithm, time: O(n), space: O(1)
    public int maxProfit1(int[] prices) {
        int sell = 0, buy = Integer.MIN_VALUE;
        for (int price : prices) {
            sell = Math.max(sell, buy + price);
            buy = Math.max(buy, -price);
        }
        return sell;
    }

    // 122. Best Time to Buy and Sell Stock II (multiple transactions)
    // Kadane's Algorithm, time: O(n), space: O(1)
    public int maxProfit2(int[] prices) {
        int sell = 0, buy = Integer.MIN_VALUE;
        for (int price : prices) {
            sell = Math.max(sell, buy + price);
            buy = Math.max(buy, sell - price);
        }
        return sell;
    }

    // 123. Best Time to Buy and Sell Stock III (at most two transactions)
    // Kadane's Algorithm, time: O(n), space: O(1)
    public int maxProfit3(int[] prices) {
        int buy1 = Integer.MIN_VALUE, buy2 = Integer.MIN_VALUE;
        int sell1 = 0, sell2 = 0;
        for (int price: prices) {
            // release2写在最前面，因为最后一个直接卖
            sell2 = Math.max(sell2, buy2 + price); // The maximum if we've just sold 2nd stock so far.
            buy2 = Math.max(buy2, sell1 - price); // The maximum if we've just buy  2nd stock so far.
            sell1 = Math.max(sell1, buy1 + price); // The maximum if we've just sold 1nd stock so far.
            buy1 = Math.max(buy1, -price); // The maximum if we've just buy  1st stock so far.[相当于sell0 - price]
        }
        return sell2;
    }

    // 188. Best Time to Buy and Sell Stock IV (at most k transactions)
    // iteratively apply Kadane's algorithm (buy and sell for k times), DP
    // time: O(nk), space: O(k)
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        // 如果k多余个数的一半，只需要两个相邻的价格，一有利润就马上买卖
        if (k >= n / 2) return quickSolve(prices);

        // DP, reduce 2D array to 1D
        int[] buy = new int[k+1], sell = new int[k+1];

        for (int i = 0; i <= k; i++) {
            buy[i] = Integer.MIN_VALUE;
        }

        for (int i = 0; i < prices.length; i++) { // prices
            for (int j = k; j > 0; j--) { // k transactions
                // sell[j]: 上一个price的同一次sell
                sell[j] = Math.max(sell[j], buy[j] + prices[i]);
                // buy[j]: 同一个price的上一次sell
                buy[j] = Math.max(buy[j], sell[j-1] - prices[i]);
            }
        }
        return sell[k];
    }

    private int quickSolve(int[] prices) {
        int n = prices.length, result = 0;
        // start from 1
        for (int i = 1; i < n; i++) {
            // if there is profit, add to result
            if (prices[i] - prices[i-1] > 0) result += prices[i] - prices[i-1];
        }
        return result;
    }

     /* 309. Best Time to Buy and Sell Stock with Cooldown
     * 最开始有
     * buy[i] = Math.max(buy[i-1], rest[i-1] - prices[i-1])
     * sell[i] = Math.max(sell[i-1], buy[i-1] + prices[i-1])
     * rest[i] = Math.max(Math.max(rest[i-1], buy[i-1]), sell[i-1])
     * 观察得出：rest[i] = max(sell[i-1], rest[i-1])，在将要跌的时候卖，前一天要不然sell，要不然rest
     * 进一步得出：rest[i] <= sell[i]，而且rest[i] == sell[i-1]
     * 把rest[i]替换成sell[i-1]，得到
     * buy[i] = max(buy[i-1], sell[i-2] - price);
     * sell[i] = max(sell[i-1], buy[i-1] + price);
     * 只需要i, i-1, i-2，所以可以reduce O(n) space to O(1)
     *
     * time: O(n), space: O(1)
     * */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int sell = 0, prev_sell = 0, buy = Integer.MIN_VALUE;
        for (int price : prices) {
            // ready to update sell, so store current sell in old_sell
            int old_sell = sell;
            sell = Math.max(sell, buy + price);
            buy = Math.max(buy, prev_sell - price);
            prev_sell = old_sell;
        }
        return sell;
    }

    /*
    * 714. Best Time to Buy and Sell Stock with Transaction Fee
    * DP
    * 两种方法，一种在买的时候算费用，一种在卖的时候算费用。其中在买的时候算好一些，
    * 因为卖的时候牵扯到buy最初的value是Integer.MIN_VALUE，会出现underflow的情况。
    * 注意714的buy, sell和309 cool down的buy, sell有些不一样，309有prev_sell和存当前sell的old_sell，714只有old_sell
    *
    * time: O(n), space: O(1)
    * */
    // pay the fee when buying the stock, to avoid underflow
    public int maxProfit(int[] prices, int fee) {
        int sell = 0, buy = Integer.MIN_VALUE;
        for (int price : prices) {
            // store current sell in old_sell
            int old_sell = sell;
            sell = Math.max(old_sell, buy + price);
            buy = Math.max(buy, old_sell - price - fee);
        }
        return sell;
    }
}
