public class power_mod {
    // 第一题: implement pow(x,n)
    // recursive
    public static double myPow(double x, int n) {
        if (n == 0) return 1;
        double temp = myPow(x, n / 2);
        if (n % 2 == 0) { // even, not 0
            return temp * temp;
        }
        if (n > 0) { // odd, > 0
            return temp * temp * x;
        } // < 0
        return temp * temp / x;
    }

    public static void main(String[] args) {
        System.out.println(myPow(8, 2));
    }

    /*
    * power mod:
    * Calculates a to the power of b, mod c.
    * (x*y)%z == ((x%z)*y)%z == (x*(y%z))%z
    * Examples:
    * PowMod(2,3,5) = 2*2*2 % 5 = 8%5 =3
    * PowMod(3, 6, 7) = 3*3*3*3*3*3 % 7 = 729%7 =1.
    * PowMod(16,16,5) = 1
    * Solution: recursion
    * Time: O(logb)
    *
    * */
    public int powmod(int a, int b, int c) {
        return pow(a, b) % c;
    }

    // double a
    private int pow(int a, int b) {
        if (b == 0) return 1;
        if (b % 2 == 0) return pow(a * a, b / 2);
        else return a * pow(a * a, b / 2);
    }
}
