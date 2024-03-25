package main.corolation;

import main.Kollom;

import java.util.LinkedList;

public class Kendall extends Corolations  {

    @Override
    public double berekenging(Kollom x, Kollom y) {
        super.berekenging(x, y);
        LinkedList<String> datax= x.getData();
        LinkedList<String> datay= y.getData();
        long score=0;
        for (int i = 0; i < datax.size() && i < datay.size(); i++) {
            double xWaardeI= Double.parseDouble(datax.get(i));
            double yWaardeI= Double.parseDouble(datay.get(i));
            for (int j = 0; j < datax.size() && j < datay.size(); j++) {
                double xWaardeJ= Double.parseDouble(datax.get(j));
                double yWaardeJ= Double.parseDouble(datay.get(j));

                if ((xWaardeI > xWaardeJ && yWaardeI > yWaardeJ) || (xWaardeI < xWaardeJ && yWaardeI < yWaardeJ)) {
                    score++;
                }
                if ((xWaardeI > xWaardeJ && yWaardeI < yWaardeJ) || (xWaardeI < xWaardeJ && yWaardeI > yWaardeJ)) {
                    score--;
                }
            }
        }
        corolation=score/(Math.pow(Math.min(datax.size(),datay.size()),2));
        return corolation;
    }
}
