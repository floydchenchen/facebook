public class single_number_ii {
    public static int singleNumber(int[] A) {
        int ones = 0, twos = 0;
        for(int i = 0; i < A.length; i++){
            ones = (ones ^ A[i]) & ~twos;
            twos = (twos ^ A[i]) & ~ones;
            System.out.println("ones: " + Integer.toBinaryString(ones));
            System.out.println("twos: " + Integer.toBinaryString(twos));
            System.out.println();
        }
        return ones;
    }

    public static void main(String[] args) {
        int[] nums = {2,2,2,3};
        System.out.println(singleNumber(nums));
    }
}
