package utils;

import java.io.*;

public class ReportGenerator {

    public static void generer() throws Exception {
        FileWriter fw = new FileWriter("rapport_plants.txt", false);
        fw.write("Rapport des plantes\n");
        fw.close();
    }
}
