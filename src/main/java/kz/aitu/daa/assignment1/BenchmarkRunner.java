package kz.aitu.daa.assignment1;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class BenchmarkRunner {
    private static final int[] SIZES = {1000, 10000, 100000, 1000000};
    private static final int RUNS = 5;
    private static final Random RANDOM = new Random(42);

    public static void main(String[] args) {
        System.out.println("Запуск бенчмарков...");

        try (PrintWriter writer = new PrintWriter("results.csv")) {
            writer.println("algorithm,input,n,time_ms,comparisons,max_depth");

            for (int n : SIZES) {
                String[] inputTypes = {"Random", "Sorted", "Duplicates"};

                for (String inputType : inputTypes) {
                    System.out.println("Тестирование: n = " + n + ", тип = " + inputType);

                    int[][] testArrays = new int[RUNS][];
                    for (int i = 0; i < RUNS; i++) {
                        testArrays[i] = generateArray(n, inputType);
                    }

                    benchmark(writer, "MergeSort", inputType, n, testArrays, MergeSort::sort);
                    benchmark(writer, "QuickSort", inputType, n, testArrays, QuickSort::sort);
                    benchmark(writer, "QuickSelect", inputType, n, testArrays,
                            (arr, metrics) -> QuickSelect.select(arr, n / 2, metrics));
                }
            }
            System.out.println("Готово! Результаты в results.csv");
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    private static int[] generateArray(int n, String type) {
        int[] arr = new int[n];
        switch (type) {
            case "Random":
                for (int i = 0; i < n; i++) arr[i] = RANDOM.nextInt();
                break;
            case "Sorted":
                for (int i = 0; i < n; i++) arr[i] = RANDOM.nextInt();
                Arrays.sort(arr);
                break;
            case "Duplicates":
                for (int i = 0; i < n; i++) arr[i] = RANDOM.nextInt(10);
                break;
        }
        return arr;
    }

    private static void benchmark(PrintWriter writer, String algoName, String inputType, int n, int[][] baseArrays, AlgorithmRunner runner) {
        List<Metrics> results = new ArrayList<>();

        for (int i = 0; i < RUNS; i++) {
            int[] copy = baseArrays[i].clone();
            Metrics metrics = new Metrics();
            runner.run(copy, metrics);
            results.add(metrics);
        }

        results.sort(Comparator.comparingLong(m -> m.timeNano));
        Metrics median = results.get(RUNS / 2);

        double timeMs = median.timeNano / 1_000_000.0;
        writer.printf(Locale.US, "%s,%s,%d,%.4f,%d,%d%n",
                algoName, inputType, n, timeMs, median.comparisons, median.maxDepth);
    }

    @FunctionalInterface
    interface AlgorithmRunner {
        void run(int[] array, Metrics metrics);
    }
}