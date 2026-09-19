package kz.aitu.daa.assignment1;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MergeSortTest {

    @Test
    void testEmptyAndSingleElement() {
        Metrics metrics = new Metrics();

        int[] empty = {};
        MergeSort.sort(empty, metrics);
        assertArrayEquals(new int[]{}, empty);

        int[] single = {42};
        MergeSort.sort(single, metrics);
        assertArrayEquals(new int[]{42}, single);
    }

    @Test
    void testAllIdenticalElements() {
        Metrics metrics = new Metrics();
        int[] arr = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
        int[] expected = arr.clone();

        MergeSort.sort(arr, metrics);
        assertArrayEquals(expected, arr);
    }

    @Test
    void testRandomArraysAgainstJavaSort() {
        Random random = new Random(42);
        Metrics metrics = new Metrics();

        // Проверяем на 100 случайных массивах разного размера
        for (int i = 0; i < 100; i++) {
            int size = random.nextInt(1000) + 1;
            int[] actual = random.ints(size, -10000, 10000).toArray();
            int[] expected = actual.clone();

            Arrays.sort(expected);
            MergeSort.sort(actual, metrics);

            assertArrayEquals(expected, actual, "Массив должен быть корректно отсортирован");
        }
    }
}