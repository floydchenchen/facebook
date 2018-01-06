/*
*
* 152. Maximum Product Subarray
* Find the contiguous subarray within an array (containing at least one number) which has the largest product.
* For example, given the array [2,3,-2,4],
* the contiguous subarray [2,3] has the largest product = 6.
*
* */

package 非tag;

public class maximum_product_subarray {
    public static int maxProduct(int A[]) {
        int maxProduct = A[0], minProduct = A[0], maxResult = A[0];
        // 一直更新minProduct和maxProduct（两者可以选择之前的乘以A[i]或者直接选择A[i]）
        // 如果A[i]是负数，那么先交换minProduct和maxProduct
        // 每次iteration更新maxResult
        for (int i = 1; i < A.length; i++) {
            if (A[i] >= 0) {
                maxProduct = Math.max(maxProduct * A[i], A[i]);
                minProduct = Math.min(minProduct * A[i], A[i]);
            } else {
                int temp = maxProduct;
                maxProduct = Math.max(minProduct * A[i], A[i]);
                minProduct = Math.min(temp * A[i], A[i]);
            }
            maxResult = Math.max(maxResult, maxProduct);
        }
        return maxResult;
    }

    public static void main(String[] args) {
        int[] arr = {2,0,-2,5,-8};
        System.out.println(maxProduct(arr));
    }
}
