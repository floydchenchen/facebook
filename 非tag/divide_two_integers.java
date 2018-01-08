/*
*
* 29. Divide Two Integers
* https://leetcode.com/problems/divide-two-integers/description/
*
* Divide two integers without using multiplication, division and mod operator.
* If it is overflow, return MAX_INT.
*
* 用 << operator : x << i 代表着 x * (2 ^ i)
* 直观的方法是，用被除数逐个的减去除数，直到被除数小于0。这样做会超时。
*
*
* */

package 非tag;

public class divide_two_integers {
    // time: O(logn), space: O(1)
    public static int divide(int dividend, int divisor) {
        // negative flag, xor
        boolean negative = dividend < 0 ^ divisor < 0;
        // reduce the problem to positive long integer to make it easier.
        // use long to avoid integer overflow cases.
        long Dividend = Math.abs((long)dividend);
        long Divisor = Math.abs((long)divisor);
        long result = 0;

        // 利用Binary search的思路
        // 直观的方法是，用被除数逐个的减去除数，直到被除数小于0，但是这样做会超时。
        // 所以我们通过每次让临时除数*2的方法，第0次减去1个除数，第1次减去2个除数，第2次减去4个除数，第i次减去2^i个除数，直到临时除数大于被除数
        while (Dividend >= Divisor) {
            long tempDivisor = Divisor;
            // tempDivisor << i 相当于tempDivisor每次*(2^i)
            for (int i = 0; (tempDivisor << i) <= Dividend; i++) {
                System.out.println("tempDivisor << " + i +": " + (tempDivisor << i));
                Dividend -= (tempDivisor << i);
                System.out.println("Dividend: " + Dividend);
                result += (1 << i);
                System.out.println("result: " + result);
                System.out.println();
            }
        }
        if (negative) result = -result;
        if (result > Integer.MAX_VALUE) return Integer.MAX_VALUE;
        return (int)result;
    }

    public static void main(String[] args) {
        divide(100, 4);
    }
}
