package main;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class VerwerkteKollom extends Kollom{

    private double IQR;
    private double gemmidelde;
    private double MAD;
    private double variantie;
    private double s;
    private double laagsteUItschieter;
    private double hoogsteUitsichieter;
    private LinkedList<String> modus;
    private double mediaan;
    private boolean isnumber;
    private LinkedList<Integer> rowsDelited;
    private long klassenBreede;
    private double aantalKlassen;
    private double klassenBreedeSturges;
    private double aantalKlassenSturges;
    private double klassenBreedeFreedman;
    private int aantalKlassenFreedman;
    private LinkedList<String> zScores;
    private double berijk;

    public VerwerkteKollom(String naam, LinkedList<String> data) {
        super(naam);
        rowsDelited=new LinkedList<>();
        zScores=new LinkedList<>();
        modus=new LinkedList<>();
        bereken(new LinkedList<>(data));

    }
    private void bereken(LinkedList<String> data) {

        LinkedList<Double> cijfers=isNumbers(data);
        if (cijfers!=null) {
            cijfers(cijfers);
        }
        modus(data);

    }

    private void modus(LinkedList<String> data) {
        HashMap<String, Integer> woordens=new HashMap<>();
        int grootste=0;
        for (String cell : data) {
            super.AddString(cell);
            if (woordens.containsKey(cell)) {
                int waarde = woordens.get(cell);
                waarde++;
                if (waarde>grootste) {

                    grootste=waarde;
                }
                woordens.put(cell, waarde);
            } else {
                woordens.put(cell, 1);
                if (grootste==0) {
                    grootste=1;

                }
            }
        }
        Set<String> keys=woordens.keySet();
        for (String next : keys) {
            if (woordens.get(next) == grootste) {
                modus.add(next);
            }
        }
    }

    private void cijfers(LinkedList<Double> cijfers) {
        LinkedList<Double>originalcijfers=cijfers;
        cijfers=new LinkedList<>(originalcijfers);
        Collections.sort(cijfers);

        IQR=portion(cijfers,0.75)- portion(cijfers,0.25);
        laagsteUItschieter= portion(cijfers,0.25)-(1.5*IQR);
        hoogsteUitsichieter= portion(cijfers,0.75)+(1.5*IQR);

        System.out.println("Q1 "+portion(cijfers,0.25));
        System.out.println("Q2 "+portion(cijfers,0.50));
        System.out.println("Q3 "+portion(cijfers,0.75));
        double som=0;
        LinkedList<String> rowsomteVerwijderen=new LinkedList<>();
        for (int i = 0; i < originalcijfers.size(); i++) {
            double cijfer=originalcijfers.get(i);
            som += cijfer;

            if (!(cijfer> laagsteUItschieter && cijfer<hoogsteUitsichieter)) {
                rowsDelited.add(i);
                rowsomteVerwijderen.add(String.valueOf(cijfer));
            }
        }
        if (getNaam().contains("concentration")) {
            System.out.println(rowsomteVerwijderen.size());
        }
//        System.out.println(rowsomteVerwijderen);
        gemmidelde=som/ cijfers.size();
        som=0;
        double som2=0;
        double grootste=0;
        double kleinste=Double.MAX_VALUE;
        for (Double cijfer : cijfers) {
            double waarde = (cijfer - gemmidelde);
            som += Math.max(waarde, -waarde);
            som2 += Math.pow(waarde, 2);
            if (cijfer>grootste) {
                grootste=cijfer;
            }
            if (cijfer<kleinste) {
                kleinste=cijfer;
            }
        }

        berijk=grootste-kleinste;
        MAD=som/ cijfers.size();
        variantie=som2/ cijfers.size();
        s=Math.pow(som2/ (cijfers.size()-1),0.5);

        klassenBreede=Math.round((3.5*s)/(Math.pow(cijfers.size(),0.333333333)));
        aantalKlassen=(grootste-kleinste)/klassenBreede;
        klassenBreedeSturges=(Math.log(cijfers.size())/Math.log(2))+1;
        aantalKlassenSturges=(grootste-kleinste)/klassenBreedeSturges;
        klassenBreedeFreedman=(2*IQR)/(Math.pow(cijfers.size(),0.333333333));
        aantalKlassenFreedman= (int) ((grootste-kleinste)/klassenBreedeFreedman);

        grootste=0;
        for (Double cijfer : originalcijfers) {
            double waarde = (cijfer - gemmidelde);
//            waarde=Math.pow(waarde, 2);
            waarde = waarde / s;
            if (grootste<waarde) {
                grootste=waarde;
            }
            zScores.add(String.valueOf(waarde));
        }
        if (cijfers.size()%2==0) {
            int midden=cijfers.size()/2;
            mediaan=(cijfers.get(midden)+cijfers.get(midden+1))/2;
        } else {
            mediaan=cijfers.get(cijfers.size()/2);
        }

//        System.out.println(grootste);
//        System.out.println(zScores);
    }

    private double portion(LinkedList<Double> cijfers, double fractie) {
        int i= (int) (cijfers.size()*fractie);
        return cijfers.get(i);
    }
    private LinkedList<Double> isNumbers(LinkedList<String> data) {
        LinkedList<Double> ding=new LinkedList<>();
        for (String s : data) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < s.length(); j++) {
                char c = s.charAt(j);
                if (Character.isDigit(c) || c == '.' || (c=='-' && j==0)) {
                    builder.append(c);
                } else {
                    isnumber=false;
                    return null;
                }
            }
            isnumber=true;
            ding.add(Double.valueOf(builder.toString()));
        }
        return ding;
    }

    public LinkedList<Integer> getRowsDelited() {
        return rowsDelited;
    }

    @Override
    public String toString() {
        if (isnumber) {
            return String.format("naam: %-10s |{ modus= %s s=%.2f gemmidelde=%.2f IQR=%.2f laagsteUItschieter=%.2f hoogsteUitsichieter=%.2f aantalKlassenSturges=%.0f variantie=%.2f mediaan=%.2f, rijenweg=%d berijk: %.2f}", getNaam(), modus.toString(), s, gemmidelde, IQR, laagsteUItschieter,hoogsteUitsichieter,aantalKlassenSturges,variantie, mediaan, rowsDelited.size(),berijk);
        } else {
            return String.format("naam: %-10s modus= %s", getNaam(), modus);
        }
    }

    public LinkedList<String> getzScores() {
        return zScores;
    }

    public boolean isIsnumber() {
        return isnumber;
    }
}
