/*
* 1，mutual friends， 已知一个function可以return 给定某人的friends。 找出A B的mutual friends。
* 2，friend recommendation。聊了大概十分钟对recommendation的想法。
* 最后问题是给定person A 返回一个list 包含推荐给A的不认识的人。顺序按照与A的mutual friends 的数量排。
*
* 第二题就是找两个array 里面相同的数字， follow-up就是给你一个user_id 和API 接口 get_friend(int id),
* 用hash set做，看下面
* https://leetcode.com/problems/intersection-of-two-arrays/description/
* 让你找出所有user id 是你的朋友的朋友，但不是你的朋友，然后根据mutual friends的数量排序，相当于FB的friend recommendation
*
* */

import java.util.*;

public class friend_recommendation {
    // the public api to get friend list of an id
    int[] getFriend(int id) {
        return new int[]{1,2,3};
    }
    // 第一个，find common friends
    public int[] intersection(int[] nums1, int[] nums2) {
        if (nums1.length == 0 || nums2.length == 0) return new int[0];
        Set<Integer> set = new HashSet<Integer>(), intersection = new HashSet<Integer>();
        for (int num1 : nums1) set.add(num1);
        for (int num2 : nums2) {
            if (set.contains(num2)) intersection.add(num2);
        }
        int[] result = new int[intersection.size()];
        int counter = 0;
        for (Integer num : intersection) result[counter++] = num;
        return result;
    }

    // 第二个，friend recommendation, bucket sort

    public class Person {
        int id;
        HashSet<Person> friends = new HashSet<>();
    }
    private List<Integer> friendsRecommend(Person person, int k) {
        List<Integer> result = new ArrayList<>();
        if (person == null)    return result;
        // id : frequency
        Map<Integer, Integer> map = new HashMap<>(); // recommend id -> count
        int highestFreq = 0;
        for (Person friend : person.friends)
            for (Person recommend : friend.friends) {
                int id = recommend.id;
                if (person.friends.contains(id) || id == person.id) // don't forget 'id == person.id'
                    continue;
                map.put(id, map.getOrDefault(id, 0) + 1);
                highestFreq = Math.max(highestFreq, map.get(id));
            }
        // bucket sort 'recommend list'
        List<Integer>[] buckets = new List[highestFreq];
        for (int id : map.keySet()) {
            if (buckets[map.get(id)] == null)
                buckets[map.get(id)] = new ArrayList<Integer>();
            buckets[map.get(id)].add(id);
        }
        //this two loops are O(k) time, when result has k nums, return it
        for (int i = highestFreq; i >= 0; i--)
            for (int j = 0; j < buckets[i].size(); j++) {
                result.add(buckets[i].get(j));
                if (result.size() == k)    return result;
            }
        return result;
    }
}
