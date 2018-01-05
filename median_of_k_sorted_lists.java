import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class median_of_k_sorted_lists {
//    PriorityQueue<Integer> max = new PriorityQueue<>((a, b) -> b - a);
    public static double median(List<List<Integer>> lists) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int size = 0, median = 0, result = 0, counter = 0;
        for (List<Integer> list : lists) {
            if (list != null) size += list.size();
        }
        for (List<Integer> list: lists) {
            for (int num : list) {
                heap.offer(num);
            }
        }
        median = (size / 2);

        while (counter < median) {
            result = heap.poll();
            counter++;
        }
        // odd size
        if (size % 2 == 1) return heap.poll();
        else return (result + heap.poll()) / 2;
    }

    public static void main(String[] args) {
        List<Integer> l0 = Arrays.asList(new Integer[] {1,2,3});
        List<Integer> l1 = Arrays.asList(new Integer[] {4,5});
        List<Integer> l2 = Arrays.asList(new Integer[] {89,6,45,3,3});
        List<Integer> l3 = Arrays.asList(new Integer[] {3,5,8,4,2});
        List<List<Integer>> list = new ArrayList<>();
        list.add(l0);
        list.add(l1);
        list.add(l2);
        list.add(l3);
        System.out.println(median(list));
    }
}
