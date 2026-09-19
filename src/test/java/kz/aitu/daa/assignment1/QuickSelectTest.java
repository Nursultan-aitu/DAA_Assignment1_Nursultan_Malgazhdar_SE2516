package kz.aitu.daa.assignment1;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuickSelectTest {

    @Test
    void testInvalidInputsThrowException() {
        Metrics metrics = new Metrics();
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(null, 0, metrics));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{}, 0, metrics));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{1, 2, 3}, -1, metrics));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{1, 2, 3}, 3, metrics));
    }

    @Test
    void testFindMinAndMax() {
        Metrics metrics = new Metrics();
        int[] arr = {5, 2, 9, 1, 7};

        assertEquals(1, QuickSelect.select(arr.clone(), 0, metrics));
        assertEquals(9, QuickSelect.select(arr.clone(), arr.length - 1, metrics));
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
            int k = random.nextInt(size);
            assertEquals(expected[k], QuickSelect.select(actual, k, metrics));
        }
    }
}