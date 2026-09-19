package kz.aitu.daa.assignment1;

import java.util.Random;

public class QuickSort {
    private static final Random RANDOM = new Random();

    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) {
            return;
        }
        long startTime = System.nanoTime();
        sort(a, 0, a.length - 1, 1, metrics);
        metrics.timeNano = System.nanoTime() - startTime;
    }

    private static void sort(int[] a, int low, int high, int depth, Metrics metrics) {
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

            int leftPartSize = lt - low;
            int rightPartSize = high - gt;

            if (leftPartSize < rightPartSize) {
                sort(a, low, lt - 1, depth + 1, metrics);
                low = gt + 1;
            } else {
                sort(a, gt + 1, high, depth + 1, metrics);
                high = lt - 1;
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}