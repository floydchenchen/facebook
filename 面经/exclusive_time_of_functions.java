import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class exclusive_time_of_functions {
    // n functions, then logs are a "list of string"!
    // 这个程序本身就是一个用stack去进行function calls
    public static int[] exclusiveTime(int n, List<String> logs) {
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>(); // stack里存的是ongoing function id
        int time = 0;
        for (String log : logs) {
            String[] split = log.split(":");

            // 1. if stack not empty, update time length

            // 因为只要for loop到下一个log，第一个必然停止/暂停
            if (!stack.isEmpty()) {
                result[stack.peek()] +=  Integer.parseInt(split[2]) - time;
            }
            // update time
            time = Integer.parseInt(split[2]);

            // 2. then check start / end

            // this log marks the start time, push it onto the stack
            if (split[1].equals("start")) {
                stack.push(Integer.parseInt(split[0]));
            }
            // this log marks the end time, pop it off the stack, time++, result time length++
            else {
                // ++: end at the end of one second
                result[stack.pop()]++;
                time++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> log = new LinkedList<>();
        log.add("0:start:0");
        log.add("1:start:2");
        log.add("1:end:5");
        log.add("0:end:6");
        System.out.println(Arrays.toString(exclusiveTime(2,log)));
    }
}
