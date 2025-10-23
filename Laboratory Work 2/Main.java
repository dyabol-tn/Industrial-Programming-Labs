import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {

        String[] strings = new String[2];
        String[] leksems = null;
        String[] razd = null;
        String addenRandom = null;
        String resultLeksems = null;
        String resultRazd = null;
        String[] date = null;
        String resultDate = null;
        String delSub = null;
        Scanner scanner = new Scanner(System.in);
        strings = String_Leks.fileRead();
        String firstString = strings[0];
        String secondString = strings[1];
        leksems = String_Leks.stringSplit(firstString, secondString);
        razd = String_Leks.findRazd(leksems);
        addenRandom = String_Leks.addRandChislo(leksems);
        resultLeksems = String_Leks.formString(leksems);
        resultRazd = String_Leks.formString(razd);
        date = String_Leks.findDate(resultLeksems);
        resultDate = String_Leks.formStringDate(date);
        delSub = String_Leks.delSubstring(resultLeksems);
        System.out.println(resultDate);
        String_Leks.fileWrite(resultLeksems);
        System.out.println(resultRazd);
        String_Leks.fileWrite(resultRazd);
        String_Leks.fileWrite(addenRandom);
        String_Leks.fileWrite(resultDate);
        String_Leks.fileWrite(delSub);
        String_Leks.printPercentCash(0.875, 1234.56, 9876543.21);
        scanner.close();
    }
}

