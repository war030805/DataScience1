package main;

import java.io.*;
import java.util.*;

public class Main {


    public static LinkedList<String> reading_files(String file) {
//        System.out.println(file);
        LinkedList<String> opslaan = new LinkedList<>();
        String lijn;
        try {
            File file4 = new File(file);
            if (file4.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                while ((lijn = reader.readLine()) != null) {
                    opslaan.add(lijn);
                }
                reader.close();
            } else {
                opslaan.add("$");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return opslaan;
    }



    public static void writingfiles(String file, List arrayList) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < arrayList.size(); i++) {
                if (i!=0) {
                    writer.write("\n");
                }
                writer.write(arrayList.get(i).toString());
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}