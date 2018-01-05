/*
*
* Task schedule with cool down time
* 给了一串task，不同的task可能属于不同type。这些task要放到CPU里运行，运行同一种type是要考虑一个冷却时间。。。
* 类似于一个OS。给你一个单线程的scheduler，和eg. 4种thread：1，2，3，4, 冷却时间: 3，
* 在multithreading的时候同类型的thread要等上一个thread跑完冷却时间之后才能运行，求最后scheduler用了多少time slot。

举个例子，thread: 1, 2, 1, 1, 3, 4; 冷却时间: 2 time slot，scheduler应该是这样的：1, 2, _, 1, _, _, 1, 3, 4，最后返回9.
* */

import java.util.*;

public class task_scheduler {
    // 1. 最正常的task schedule：输出的是最后的时间
    private static int taskSchedule1(int[] tasks, int cooldown) {
        if (tasks == null || tasks.length == 0)    return 0;
        // map: task -> 下一个task出现的时间点
        HashMap<Integer, Integer> map = new HashMap<>();
        int slots = 0;
        for (int task : tasks) {
            //if we need to wait for the cooldown of the same task, just update the slots
            if (map.containsKey(task) && map.get(task) > slots) slots = map.get(task);
            //update the time slot to the time when curr task can be done again
            map.put(task, slots + 1 + cooldown);
            slots++; //important!! update the used 1 slot of current task
        }
        return slots;
    }

    // 2. 最正常的task schedule：输出的是字符串 '#'
    // //if we need to output a string of the task scheduling(without reordering),
    // eg.1,2,1,1,3,4, k=2, -> 1 2 _ 1 _ _ 1 3 4
    public static String taskSchedule2(int[] tasks, int cooldown) {
        if (tasks == null || tasks.length == 0)   return "";
        // map: task -> 下一个task出现的时间点
        Map<Integer, Integer> map = new HashMap<>();
        int slots = 0;
        StringBuilder sb = new StringBuilder();
        for (int task : tasks) {
            if (map.containsKey(task) && map.get(task) > slots) {
                int waitingTime = map.get(task) - slots;
                while (waitingTime-- > 0)
                    sb.append("#");
                slots = map.get(task);
            }
            map.put(task, slots + cooldown + 1);
            sb.append(task);
            slots++;
        }
        return sb.toString();
    }

    // 3. 无序的，频率统计的做法，算最后时间
    //if tasks can be reordered, output the minimum time needed: O(n) time, O(n) space
    public int leastInterval(char[] tasks, int cooldown) {
        // map: task -> frequency of occurrences
        Map<Integer, Integer> map = new HashMap<>();
        for (int task : tasks) {
            map.put(task, map.getOrDefault(task, 0) + 1);
        }

        int maxFrequency = 0, maxCount = 0;
        for (int frequency : map.values()) {
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
                maxCount = 1;
            } else if (frequency == maxFrequency) {
                maxCount++;
            }
        }
        // 具体分析见621 task scheduler的doc
        int minTime = (maxFrequency - 1) * (cooldown + 1) + maxCount;
        // 填坑的时候，有可能坑填满了，但是还有task没被放进去，这时候输出input.length即可
        return Math.max(tasks.length, minTime);
    }

    // 4. 无序的，统计频率的做法，输出最后字符串
    //output a sequence of tasks that takes least time:O(maxFrequency*klogk) time, O(n) space,n is number of unique tasks
    private static String taskSchedule4(String str, int k) {
        StringBuilder sb = new StringBuilder();
        Map<Character, Integer> map = new HashMap<>();
        for (char c : str.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        Queue<Map.Entry<Character, Integer>> maxHeap = new PriorityQueue<>((entry1, entry2) -> entry2.getValue() - entry1.getValue());
        maxHeap.addAll(map.entrySet());//O(nlogn) time, O(n) space
        // 因为要一组一组加，用temp来存被展示poll下来还会被放回maxHeap的entry
        ArrayList<Map.Entry<Character, Integer>> temp = new ArrayList<>();//O(k) space

        while (!maxHeap.isEmpty()) {//O(max frequency) time
            int i = 0;
            temp.clear();
            while (i <= k && !maxHeap.isEmpty()) {//O(k) time
                Map.Entry<Character, Integer> entry = maxHeap.poll(); // O(lgn) time
                sb.append(entry.getKey());
                entry.setValue(entry.getValue() - 1);
                temp.add(entry);
                i++;
            }

            //add this code if we wanna add _ to show that we need to wait for cooldown, eg.AABB, 2 -> AB_AB
            while (i <= k) {//i <= k, not i < k !!!
                sb.append("_");
                i++;//remember to add i++ !!!
            }

            // 把entry放回去
            for (Map.Entry<Character, Integer> e : temp) {// O(klogk) time
                if (e.getValue() > 0) {
                    maxHeap.offer(e);
                }
            }
        }

        //add this code if we add "_" to the string, we need to delete all the "_" at the tail, eg.A__A__ -> A__A
        for (int i = sb.length() - 1; i >= 0 && sb.charAt(i) == '_'; i--) {
            sb.deleteCharAt(i);
        }

        return sb.toString();
    }

    // 5. if cooldown is very small, but there are lots of tasks, how to reduce space? O(cooldown) space
    // 用一个queue存cooldown waiting的queue，让map和queue都只存O(cooldown)的tasks
    private static int taskSchedule5(int[] tasks, int cooldown) {
        if (tasks == null || tasks.length == 0) {
            return 0;
        }
        Queue<Integer> queue = new LinkedList<>();//store tasks that are waiting for cooldown
        HashMap<Integer, Integer> map = new HashMap<>();//store indices, where cooldown stops, of tasks in window
        int slots = 0;
        for (int task : tasks) {
            if (map.containsKey(task) && map.get(task) > slots) {
                //add this code if our output is a string, eg.AA, 2 -> A__A
                //int waitingTime = map.get(task) - slots;
                //for (int i = 0; i < waitingTime; i++) {
                //    sb.append("_");
                //}
                slots = map.get(task);//if we need to wait for the cooldown of the same task, just update the slots
            }
            if (queue.size() == cooldown + 1) {
                map.remove(queue.poll());//we should do this after updating the slots, cuz we store indices of cooldown
            }//stops, we don't know whether we have any idol period between these two same tasks yet, so update it first
            map.put(task, slots + cooldown + 1);//update the time slot to the time when curr task can be done again
            queue.offer(task);
            slots++;//update the used 1 slot of curr task
        }
        return slots;
    }


    public static void main(String[] args) {
        int[] tasks = {1, 2, 1, 1, 3, 4};
        String s = "121134";
        System.out.println(taskSchedule5(tasks, 2));
    }

}
