package main;

import main.corolation.Kendall;
import main.corolation.Pearson;
import main.corolation.Spearman;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Tabel {
    private LinkedList<Kollom> kollomen;

    public Tabel(String bestand) {
        kollomen=new LinkedList<>();
        leesCsvBestand(bestand);
    }

    public LinkedList<Kollom> leesCsvBestand(String bestand) {
        LinkedList<String> woorden= Main.reading_files(bestand);

        for (int i = 0; i < woorden.size(); i++) {
            String[] waarden=woorden.get(i).split(",");
            String s=woorden.get(i);
            if (waarden.length>5) {
                waarden=maakKollombeter(waarden);
            }
            for (int j = 0; j < waarden.length; j++) {
                String waarde=waarden[j];
                if (i==0) {
                    kollomen.add(new Kollom(waarde));
                } else {

                    kollomen.get(j).AddString(waarde);
                }
            }
        }
        LinkedList<Kollom> verwerkteKolloms=new LinkedList<>();

        for (Kollom kolloman : kollomen) {
            verwerkteKolloms.add(kolloman.bereken());
        }
        haaldegenediewegzijnweg(verwerkteKolloms);
        schrijfverwerktekollon(verwerkteKolloms, "verwerkt.csv");
        LinkedList<Kollom> zscores=new LinkedList<>();
        for (Kollom verwerkteKollom : verwerkteKolloms) {
            Kollom kollom = new Kollom(verwerkteKollom.getNaam());

            LinkedList<String> zscore = ((VerwerkteKollom) verwerkteKollom).getzScores();
            for (String s : zscore) {
                kollom.AddString(s);
            }
            zscores.add(kollom);
        }
        schrijfverwerktekollon(zscores,"zscores.csv");
        for (int i = 0; i < verwerkteKolloms.size(); i++) {
            for (int j = i+1; j < verwerkteKolloms.size(); j++) {
                if (verwerkteKolloms.get(i) instanceof VerwerkteKollom x1 && verwerkteKolloms.get(j) instanceof VerwerkteKollom y1 && y1.isIsnumber() && x1.isIsnumber()) {
                    System.out.printf("pearson %s %s, %.2f %n",x1.getNaam(), y1.getNaam(),new Pearson().berekenging(verwerkteKolloms.get(i),verwerkteKolloms.get(j)));
                    System.out.printf("spearman %s %s, %.2f %n",x1.getNaam(), y1.getNaam(),new Spearman().berekenging(verwerkteKolloms.get(i),verwerkteKolloms.get(j)));
                    System.out.printf("kendall %s %s, %.2f %n",x1.getNaam(), y1.getNaam(),new Kendall().berekenging(verwerkteKolloms.get(i),verwerkteKolloms.get(j)));
                    System.out.println();
                }
            }
        }
        kollontoevoegen();
        return kollomen;
    }
    public void haaldegenediewegzijnweg(LinkedList<Kollom> verwerkteKolloms) {
        HashSet<Integer> verwijderd=new HashSet<>();
        for (Kollom kollom : verwerkteKolloms) {
            if (kollom instanceof VerwerkteKollom verwerkteKollom) {
                verwijderd.addAll(verwerkteKollom.getRowsDelited());
            }

        }
        for (Kollom verwerkteKollom : verwerkteKolloms) {
            LinkedList<String> lijst = verwerkteKollom.getData();
            int j = 0;
            for (Iterator<String> iterator = lijst.iterator(); iterator.hasNext(); ) {
                iterator.next();
                if (verwijderd.contains(j)) {
                    iterator.remove();
                }
                j++;
            }

        }
//        System.out.println(verwijderd.size());
    }
    public void schrijfverwerktekollon(LinkedList<Kollom> verwerkteKolloms, String naam) {
        LinkedList<String> row=new LinkedList<>();
        int i=-1;
        int grooste=maxLengt(verwerkteKolloms);
        while (i<grooste) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < verwerkteKolloms.size(); j++) {
                Kollom kollom = verwerkteKolloms.get(j);
                if (j != 0) {
                    builder.append(",");
                }
                if (i == -1) {
                    builder.append(kollom.getNaam());
                } else {
                    LinkedList<String> data = kollom.getData();
                    if (i<data.size()) {
                        builder.append(data.get(i));
                    }

                }

            }
            row.add(builder.toString());
            i++;
        }
        Main.writingfiles("/Desktop/"+naam,row);
    }
    public int maxLengt(LinkedList<Kollom> verwerkteKolloms) {
        int grootste=0;
        for (Kollom verwerkteKollom : verwerkteKolloms) {
            int groote = verwerkteKollom.getData().size();
            if (groote > grootste) {
                grootste = groote;
            }
        }
        return grootste;
    }
    public void kollontoevoegen() {
        kollomen.add(new Kollom("sync"));
        for (int i = 0; i < kollomen.size(); i++) {
            if (kollomen.get(i).getNaam().equals("gem sync speed")) {
                LinkedList<String> data=kollomen.get(i).getData();
                for (String datum : data) {
                    double d = Double.parseDouble(datum);
                    kollomen.getLast().AddString(String.format("%.0f", d));
                }
            }
        }
//        schrijfverwerktekollon(kollomen,"metrijextra.csv");
    }
    public String[] maakKollombeter(String[] waarden) {
        LinkedList<String> stringLinkedList=new LinkedList<>();
        for (int i = 0; i < waarden.length; i++) {
            if (waarden[i].startsWith("\"") && !waarden[i].endsWith("\"") && i+1 < waarden.length && !waarden[i+1].startsWith("\"") && waarden[i+1].endsWith("\"")) {
                stringLinkedList.add(waarden[i]+","+waarden[i+1]);
                i++;
            } else {
                stringLinkedList.add(waarden[i]);
            }
        }
        String[] fransicus=new String[stringLinkedList.size()];
        for (int i = 0; i < fransicus.length; i++) {
            fransicus[i]=stringLinkedList.get(i);
        }
        return fransicus;
    }
}
