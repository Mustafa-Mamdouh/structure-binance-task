package com.structure.binance.analysis.services.helpers;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MedianCalculator {
    private Queue<Double> minHeap, maxHeap;

    public MedianCalculator() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
    }

    public void add(double num) {
        if (minHeap.size() == maxHeap.size()) {
            maxHeap.offer(num);
            minHeap.offer(maxHeap.poll());
        } else {
            minHeap.offer(num);
            maxHeap.offer(minHeap.poll());
        }
    }

    public double getMedian() {
        double median;
        if (minHeap.size() > maxHeap.size()) {
            median = minHeap.peek();
        } else {
            median = (minHeap.peek() + maxHeap.peek()) / 2;
        }
        return median;
    }
}