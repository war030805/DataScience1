package main;

import java.util.LinkedList;

public class Kollom {
    private String naam;
    private LinkedList<String> data;
    public Kollom(String naam) {
        this.naam = naam;
        data=new LinkedList<>();
    }
    public void AddString(String s) {
        data.add(s);
    }

    public LinkedList<String> getData() {
        return data;
    }
    public VerwerkteKollom bereken() {
        VerwerkteKollom verwerkteKollom=new VerwerkteKollom(naam,data);
        System.out.println(verwerkteKollom);
        return verwerkteKollom;
    }
    public void vervang() {
        LinkedList<String> vervangen=new LinkedList<>();
        for (String datum : data) {
            vervangen.add(datum.replace('.', ','));
        }
        data=vervangen;
        Main.writingfiles("",data);

    }

    @Override
    public String toString() {
        return "Kollom{" +
                "naam='" + naam + '\'' +
                ", data=" + data +
                '}';
    }

    public String getNaam() {
        return naam;
    }
}
