package kz.aitu.daa.assignment1;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuickSortTest {

    @Test
    void testDepthOnSortedArray() {
        int n = 100000;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }

        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);

        double maxAllowedDepth = 2 * (Math.log(n) / Math.log(2));
        assertTrue(metrics.maxDepth <= maxAllowedDepth,
                "Глубина рекурсии " + metrics.maxDepth + " превышает предел " + maxAllowedDepth);
    }

    @Test
    void testArraysWithDuplicates() {
        Metrics metrics = new Metrics();
        int[] arr = {4, 2, 4, 4, 1, 4, 8, 4, 9, 4};
        int[] expected = arr.clone();

        Arrays.sort(expected);
        QuickSort.sort(arr, metrics);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testRandomArraysAgainstJavaSort() {
        Random random = new Random(42);
        Metrics metrics = new Metrics();

        for (int i = 0; i < 100; i++) {
            int size = random.nextInt(1000) + 1;
            int[] actual = random.ints(size, -10000, 10000).toArray();
            int[] expected = actual.clone();

            Arrays.sort(expected);
            QuickSort.sort(actual, metrics);

            assertArrayEquals(expected, actual);
        }
    }
}