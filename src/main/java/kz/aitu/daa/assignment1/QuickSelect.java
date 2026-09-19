package kz.aitu.daa.assignment1;

import java.util.Random;

public class QuickSelect {
    private static final Random RANDOM = new Random();

    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0 || k < 0 || k >= a.length) {
            throw new IllegalArgumentException("Invalid input array or k is out of bounds");
        }

        long startTime = System.nanoTime();
        int result = select(a, 0, a.length - 1, k, 1, metrics);
        metrics.timeNano = System.nanoTime() - startTime;

        return result;
    }

    private static int select(int[] a, int low, int high, int k, int depth, Metrics metrics) {
        while (low < high) {
            metrics.updateMaxDepth(depth);

            int pivotIndex = low + RANDOM.nextInt(high - low + 1);
            swap(a, low, pivotIndex);
            int pivot = a[low];

            int lt = low;
            int gt = high;
            int i = low + 1;

            while (i <= gt) {
                metrics.comparisons++;
                if (a[i] < pivot) {
                    swap(a, lt++, i++);
                } else {
                    metrics.comparisons++;
                    if (a[i] > pivot) {
                        swap(a, i, gt--);
                    } else {
                        i++;
                    }
                }
            }

            if (k >= lt && k <= gt) {
                return a[k];
            } else if (k < lt) {
                high = lt - 1;
            } else {
                low = gt + 1;
            }
            depth++;
        }

        metrics.updateMaxDepth(depth);
        return a[low];
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}