package com.hidata.framework.util.datastructure;

import java.util.Collections;

/**
 * 包含各种查找算法
 *
 * @author: houzhaowei
 */

public class Search {

    private Search() {
    }

    /**
     * 二分查找（折半查找）
     *
     * @param data
     * @param key
     * @return
     */
    public static <T extends Comparable<T>> int binarySearch(T[] data, T key) {
        int low;
        int high;
        int mid;

        if (data == null || data.length == 0) {
            return -1;
        }


        low = 0;
        high = data.length - 1;

        while (low <= high) {
            mid = (low + high) / 2;

            if (key.compareTo(data[mid]) < 0) {
                high = mid - 1;
            } else if (key.compareTo(data[mid]) > 0) {
                low = mid + 1;
            } else if (key.compareTo(data[mid]) == 0) {
                return mid;
            }
        }

        return -1;
    }


    public static void main(String[] args) {
        String[] data = {"1", "4", "5", "8", "15", "33", "48", "77", "96"};
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        int result = Search.binarySearch(data, "48");
        System.out.println("Key index:" + result + "time is :" + (end - start));

    }
}