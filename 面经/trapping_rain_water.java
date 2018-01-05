public class trapping_rain_water {
    public int trap(int[] A) {
        // two pointers
        int start = 0, end = A.length - 1;
        int result = 0, leftmax = 0, rightmax = 0;

        while (start <= end) {
            leftmax = Math.max(leftmax, A[start]);
            rightmax = Math.max(rightmax, A[end]);
            // if leftmax < rightmax, so the (leftmax-A[start]) water can be stored
            if (leftmax < rightmax) {
                result += (leftmax - A[start]);
                start++;
            }
            else {
                result += (rightmax - A[end]);
                end--;
            }
        }
        return result;
    }
}
