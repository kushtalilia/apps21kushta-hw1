package ua.edu.ucu.tempseries;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private static final int MIN_VALUE = -273;
    private double[] temperatureSeries;
    private int initialLength;

    public TemperatureSeriesAnalysis() {
        this.temperatureSeries = new double[]{};
    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        for (double temp : temperatureSeries) {
            if (temp < MIN_VALUE) {
                throw new
                        InputMismatchException("The temperature "
                        + "exceeds minimum value!");
            }
        }
        this.temperatureSeries = temperatureSeries;
        initialLength = temperatureSeries.length;

    }

    public double average() {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }

        double sum = 0;

        int amount = 0;
        for (int el = 0; el < temperatureSeries.length; el++) {
            double element = (double) Array.get(temperatureSeries, el);
            sum += element;
            amount += 1;
        }
        double result = sum / amount;
        return result;
    }


    public double deviation() {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }

        double avarage = average();
        double sum = 0;
        for (int i = 0; i < temperatureSeries.length; i++) {
            sum += (temperatureSeries[i] - avarage)
                    * (temperatureSeries[i] - avarage);
        }
        double result = Math.sqrt(sum / temperatureSeries.length);
        return result;
    }

    public double findClosest(double element) {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
        double minDist = Double.POSITIVE_INFINITY;
        double closestEl = 0;
        for (int i = 0; i < temperatureSeries.length; i++) {
            double el = (double) Array.get(temperatureSeries, i);
            double curDist = Math.abs(el - element);
            if (curDist < minDist) {
                minDist = curDist;
                closestEl = el;
            } else if (curDist == minDist) {
                if (closestEl < el) {
                    closestEl = el;
                }
            }
        }
        return closestEl;
    }

    public double min() {
        return findClosest(Double.NEGATIVE_INFINITY);
    }

    public double max() {
        return findClosest(Double.POSITIVE_INFINITY);
    }

    public double findTempClosestToZero() {
        return findClosest(0);
    }

    public double findTempClosestToValue(double tempValue) {
        double result = findClosest(tempValue);
        return result;
    }

    public double[] findTempsLessThen(double tempValue) {
        if (temperatureSeries.length == 0) {
            throw new
                    IllegalArgumentException();
        } else {
            double[] lessValues = new double[temperatureSeries.length];
            for (int i = 0; i < temperatureSeries.length; i++) {
                if (temperatureSeries[i] < tempValue) {
                    lessValues[i] = temperatureSeries[i];
                }
            }
            return lessValues;
        }
    }

    public double[] findTempsGreaterThen(double tempValue) {
        if (temperatureSeries.length == 0) {
            throw new
                    IllegalArgumentException();
        } else {
            double[] biggerValues = new double[temperatureSeries.length];
            int counter = 0;
            for (int i = 0; i < temperatureSeries.length; i++) {
                if (temperatureSeries[i] > tempValue) {
                    biggerValues[i] = temperatureSeries[i];
                }
            }
            return biggerValues;
        }
    }

    public TempSummaryStatistics summaryStatistics() {
        double avgTemp = this.average();
        double devTemp = this.deviation();
        double minTemp = this.min();
        double maxTemp = this.max();
        return new TempSummaryStatistics(avgTemp, devTemp, minTemp, maxTemp);
    }

    public double addTemps(double... temps) {
        double sum = 0;
        for (int i = 0; i < temps.length; i++) {

            if (temps[i] < MIN_VALUE) {
                throw new InputMismatchException("The temperature "
                        + "exceeds minimum value!");
            }

            if (initialLength == 0) {
                temperatureSeries = Arrays.copyOf(temperatureSeries, 1);
            } else if (temperatureSeries.length == initialLength) {
                temperatureSeries = Arrays.copyOf(temperatureSeries,
                        2 * temperatureSeries.length);
            }
            temperatureSeries[initialLength] = temps[i];
            this.initialLength += 1;
        }
        temperatureSeries = Arrays.copyOf(temperatureSeries, initialLength);

        for (int i = 0; i < temperatureSeries.length; i++) {
            sum += temperatureSeries[i];
        }

        return sum;
    }
}
