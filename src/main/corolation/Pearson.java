package main.corolation;

import main.Kollom;
import main.VerwerkteKollom;

public class Pearson extends Corolations{

    public Pearson() {

    }

    @Override
    public double berekenging(Kollom x, Kollom y) {
        super.berekenging(x, y);

        if (x instanceof VerwerkteKollom x1 && y instanceof VerwerkteKollom y1) {
            double som=0;
            for (int i = 0; i < x1.getzScores().size() && i<y1.getzScores().size(); i++) {
                som+=Double.parseDouble(x1.getzScores().get(i))* Double.parseDouble(y1.getzScores().get(i));
            }
            corolation=som/Math.min(x1.getzScores().size(),y1.getzScores().size());
        }
        return corolation;
    }
}
