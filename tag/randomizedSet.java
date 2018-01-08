package tag;

import java.util.*;

class RandomizedSet {
    // key is val, value is the val's location
    HashMap<Integer, Integer> map;
    // 因为要让getRandom() O(1)，所以需要一个list来存locations[i]的val是啥，然后使用list的size
    ArrayList<Integer> locations;
    Random random = new Random();
    /** Initialize your data structure here. */
    public RandomizedSet() {

    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        boolean contain = map.containsKey(val); // ????为啥必须要这个？
        if (contain) return false;
        map.put(val, locations.size());
        locations.add(val);
        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        boolean contain = map.containsKey(val);
        if (!contain) return false;
        int location = map.get(val);
        // if the one removed is not the last location, sway them and remove it
        if (location < locations.size() - 1) {
            int lastVal = locations.get(locations.size() - 1);
            locations.set(location, lastVal);
            map.put(lastVal, location);
        }
        map.remove(val);
        locations.remove(locations.size() - 1);
        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        return locations.get(random.nextInt(locations.size()));
    }
}

/*
*
* follow-up: duplicates allowed
*
* 思路：
* hash map: key is val, value is the val's location（第几个出现）
* list: locations[i]的val是啥，然后getRandom()使用list的size，去实现getRandom O(1)
* The idea is to add a set to the hashMap to remember all the locations of a duplicated number.
*
* */
class RandomizedCollection {

    // key is val, value是一个记录val出现位置的set
    HashMap<Integer, Set<Integer>> map;
    // 因为要让getRandom() O(1)，所以需要一个list来存nums[i]的val是啥，然后使用list的size
    ArrayList<Integer> nums;
    Random random = new Random();

    /**
     * Initialize your data structure here.
     */
    public RandomizedCollection() {
        nums = new ArrayList<Integer>();
        map = new HashMap<Integer, Set<Integer>>();
    }

    /**
     * Inserts a value to the collection. Returns true if the collection did not already contain the specified element.
     */
    public boolean insert(int val) {
        // 如果map中不包含val，则新建一个key为val的HashSet
        boolean contain = map.containsKey(val);
        if (!contain) map.put(val, new HashSet<>());
        map.get(val).add(nums.size());
        nums.add(val);
        return !contain;
    }

    /**
     * Removes a value from the collection. Returns true if the collection contained the specified element.
     */
    public boolean remove(int val) {
        boolean contain = map.containsKey(val);
        if (!contain) return false;
        int location = map.get(val).iterator().next();
        map.get(val).remove(location);
        // if the one removed is not the last location, swap them and remove it
        if (location < nums.size() - 1) {
            int lastVal = nums.get(nums.size() - 1);
            nums.set(location, lastVal);
            map.get(lastVal).remove(nums.size() - 1);
            map.get(lastVal).add(location);
        }
        nums.remove(nums.size() - 1);
        // 如果map中的以某个val为key的set空了，remove这个key与set
        if (map.get(val).isEmpty()) map.remove(val);
        return true;
    }

    /**
     * Get a random element from the collection.
     */
    public int getRandom() {
        return nums.get(random.nextInt(nums.size()));
    }
}
/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */