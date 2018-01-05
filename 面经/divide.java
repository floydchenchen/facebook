public class divide {
    public static int divide(int dividend, int divisor) {
        // negative flag, xor
        boolean negative = dividend < 0 ^ divisor < 0;
        // reduce the problem to positive long integer to make it easier.
        // use long to avoid integer overflow cases.
        long Dividend = Math.abs((long)dividend);
        long Divisor = Math.abs((long)divisor);
        long result = 0;

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
        System.out.println(divide(-23145,654));
    }
}
