/*
* basic dot product
*
* A={a1, a2, a3,...}
* B={b1, b2, b3,...}
* dot_product = a1 * b1 + a2 * b2 + a3 * b3 ＋ ...
*
* time: O(n)
* space: O(1)
*

* */

public class dot_product {
    // basic dot product
    public int dotProduct(int[] a, int[] b) {
        int res = 0;
        for (int i = 0; i < a.length; i++)
            res += a[i] * b[i];
        return res;
    }

/*
* folstart-up 1: sparse vector
*
* If the array is very sparse
* calculate the dot product of two very large arrays with many 0 elements. Be as efficient as possible
*
* A={a1, ...., a300, ...., a5000}
* B={...., b100, ..., b300, ..., b1000, ...}
* 例子: A=[[1, a1], [300, a300], [5000, a5000]]
*      B=[[100, b100], [300, b300], [1000, b1000]]
*      返回 a300 * b300
*
* 思路：
* A,B化简成list of tuples
* 只存储非零元素和他的index
* 然后two pointer扫就行
* 还可以优化成binary search
*
* 分析：
* time: O(m+n)
* space: O(1)
*
* */
    // two pointers
    public int dotProduct(int[][] A, int[][] B) {
        int result = 0;
        int i = 0;
        int j = 0;
        while (i < A.length && j < B.length) {
            if (A[i][0] == B[j][0]) {
                result += A[i][1] * B[j][1];
                i++;
                j++;
            }
            else if (A[i][0] > B[j][0])
                i++;
            else
                j++;
        }
        return result;
    }
/*
* follow-up 2:
* 如果length(B) >>> length(A)，即B非常长，怎么做能减少时间复杂度。
* 对A里面的每个数，用binary search找B中相对应的值，这样时间复杂度是O(mlogn) (m = len(A), n =len(B))
*
* 分析：
* time: O(mlgn)
* space: O(1)
* */
    // 优化：Binary Search on array B
    public int docProduct2(int[][] A, int[][] B) {
        int result = 0;
        for (int[] pair : A) {
            int index = pair[0];
            int indexB = binarySearch(B, index);
            if (indexB != -1)
                result += pair[1] * B[indexB][1];
        }
        return result;
    }
    public int binarySearch(int[][] B, int index) {
        int start = 0, end = B.length - 1;
        while (start + 1 < end) {
            int mid = (start + end) / 2;
            if (B[mid][1] >= index)
                end = mid;
            else
                start = mid;
        }
        if (B[start][0] == index)
            return start;
        else if (B[end][0] == index)
            return end;
        return -1;
    }
}