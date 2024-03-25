package main.corolation;

import main.Kollom;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class Spearman extends Corolations{

    public Spearman() {

    }
    @Override
    public double berekenging(Kollom x, Kollom y) {
        LinkedList<Double>x1=isNumbers(x.getData());
        LinkedList<Double>y1=isNumbers(y.getData());
        if (x1!=null && y1!=null) {
            double[] x2=rangOmzetting(x1);
            double[] y2=rangOmzetting(y1);
            double som=0;
            for (int i = 0; i < x2.length && i<y2.length; i++) {
                som+=Math.pow(x2[i]-y2[i],2);
            }
//            corolation=som/Math.min(x2.length,y2.length);
//            return corolation;
            int n = x2.length;
            corolation = 1 - (6 * som) / (n * (n * n - 1));
            return corolation;
        }
        return 0;
    }
    private double[] rangOmzetting(LinkedList<Double> data) {
        int n = data.size();
        double[] ranks = new double[n];
        Double[][] valueAndOriginalPosition = new Double[n][2];

        for (int i = 0; i < n; i++) {
            valueAndOriginalPosition[i][0] = data.get(i);
            valueAndOriginalPosition[i][1] = (double) i;
        }

        // Sort the array based on values
        Arrays.sort(valueAndOriginalPosition, Comparator.comparing(a -> a[0]));

        // Handle ties by averaging their ranks
        double sumOfRanks;
        int i = 0;
        while (i < n) {
            sumOfRanks = i + 1; // Start with the current position (ranks are 1-based)
            int count = 1;
            int j = i + 1;
            while (j < n && valueAndOriginalPosition[j][0].equals(valueAndOriginalPosition[i][0])) {
                sumOfRanks += j + 1;
                count++;
                j++;
            }

            double averageRank = sumOfRanks / count;
            for (j = i; j < i + count; j++) {
                ranks[valueAndOriginalPosition[j][1].intValue()] = averageRank;
            }

            i += count;
        }

        return ranks;
    }
    private LinkedList<Double> isNumbers(LinkedList<String> data) {
        LinkedList<Double> ding = new LinkedList<>();
        for (String s : data) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < s.length(); j++) {
                char c = s.charAt(j);
                if (Character.isDigit(c) || c == '.' || (c == '-' && j == 0)) {
                    builder.append(c);
                } else {
                    return null;
                }
            }
            ding.add(Double.valueOf(builder.toString()));
        }
        return ding;
    }
}
