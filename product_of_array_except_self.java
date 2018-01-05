public class product_of_array_except_self {
    public static int[] productExceptSelf(int[] nums) {
        int[] output = new int[nums.length];
        int product = 1;
        for (int i = 0; i < nums.length; i++) {
            output[i] = product;
            product *= nums[i];
        }
        product = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            output[i] *= product;
            product *= nums[i];
        }
        return output;
    }

    public static void main(String[] args) {
        int[] input = {2,3,4,5};
        System.out.println(productExceptSelf(input));
    }
}
