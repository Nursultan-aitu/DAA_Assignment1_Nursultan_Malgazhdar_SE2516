package kz.aitu.daa.assignment1;

public class Metrics {
    public long comparisons = 0;
    public int maxDepth = 0;
    public long timeNano = 0;

    /**
     * Обновляет максимальную зафиксированную глубину рекурсии.
     */
    public void updateMaxDepth(int currentDepth) {
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    /**
     * Сбрасывает счетчики для повторного использования объекта.
     */
    public void reset() {
        comparisons = 0;
        maxDepth = 0;
        timeNano = 0;
    }
}