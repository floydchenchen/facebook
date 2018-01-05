public class maximum_swap {
    public static int maximumSwap(int num) {
        char[] digits = Integer.toString(num).toCharArray();
        // Use buckets to record the last position of digit 0 ~ 9 in this num.
        int[] buckets = new int[10];
        for (int i = 0; i < digits.length; i++) {
            buckets[digits[i] - '0'] = i;
        }
        // i是digits的position
        for (int i = 0; i < digits.length; i++) {
            // k是value, buckets[k]是这个value最后一次出现的position
            for (int k = 9; k > digits[i] - '0'; k--) { // 当value大于digits[i], value从大到小
                if (buckets[k] > i) { //如果这个大于digits[i]的value，最后一次出现的position在i的右边，换位置
                    char tmp = digits[i];
                    digits[i] = digits[buckets[k]];
                    digits[buckets[k]] = tmp;
                    return Integer.valueOf(new String(digits));
                }
            }
        }
        return num;
    }

    public static void main(String[] args) {
        System.out.println(maximumSwap(2736));
    }
}
