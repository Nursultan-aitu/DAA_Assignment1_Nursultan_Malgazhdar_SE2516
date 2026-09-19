package kz.aitu.daa.assignment1;

public class MergeSort {

    private static final int INSERTION_SORT_CUTOFF = 15;

    /**
     * Точка входа в сортировку.
     */
    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) {
            return;
        }

        long startTime = System.nanoTime();

        // 1. Выделяем память под буфер строго один раз O(n)
        int[] temp = new int[a.length];

        // 2. Запускаем рекурсивную сортировку
        sort(a, temp, 0, a.length - 1, 1, metrics);

        metrics.timeNano = System.nanoTime() - startTime;
    }

    private static void sort(int[] a, int[] temp, int left, int right, int depth, Metrics metrics) {
        metrics.updateMaxDepth(depth);

        // Порог переключения: на малых отрезках используем Insertion Sort
        if (right - left + 1 <= INSERTION_SORT_CUTOFF) {
            insertionSort(a, left, right, metrics);
            return;
        }

        int mid = left + (right - left) / 2;

        // Рекурсивный спуск в левую и правую части
        sort(a, temp, left, mid, depth + 1, metrics);
        sort(a, temp, mid + 1, right, depth + 1, metrics);

        // Оптимизация: если левая часть уже меньше правой, слияние не нужно
        metrics.comparisons++;
        if (a[mid] <= a[mid + 1]) {
            return;
        }

        // Слияние двух отсортированных половин
        merge(a, temp, left, mid, right, metrics);
    }

    private static void merge(int[] a, int[] temp, int left, int mid, int right, Metrics metrics) {
        // Копируем текущий рабочий диапазон в единый вспомогательный буфер
        for (int k = left; k <= right; k++) {
            temp[k] = a[k];
        }

        int i = left;
        int j = mid + 1;

        // Сливаем обратно в исходный массив a
        for (int k = left; k <= right; k++) {
            if (i > mid) {
                a[k] = temp[j++];
            } else if (j > right) {
                a[k] = temp[i++];
            } else {
                metrics.comparisons++;
                if (temp[j] < temp[i]) {
                    a[k] = temp[j++];
                } else {
                    a[k] = temp[i++];
                }
            }
        }
    }

    private static void insertionSort(int[] a, int left, int right, Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;

            while (j >= left) {
                metrics.comparisons++;
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }
}