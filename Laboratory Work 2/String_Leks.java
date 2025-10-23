import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class String_Leks extends Main {
    public static String[] fileRead() {
        String[] result = new String[2];
        String inputName = "C://Users//stepa//IdeaProjects//Laboratory Work 2//Data//input.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(inputName))) {
            result[0] = reader.readLine();
            result[1] = reader.readLine();
            System.out.println(result[0]);
            System.out .println(result[1]);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void fileWrite(String inputStr) {
        String outputName = "C://Users//stepa//IdeaProjects//Laboratory Work 2//Data//output.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputName, true))) {
            writer.write(inputStr);
            writer.newLine();
            writer.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String formString(String[] array) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            result.append(array[i]).append(" ");
        }
        return result.toString();
    }

    public static String formStringDate(String[] array) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            result.append(array[i]).append("\n");
        }
        return result.toString();
    }

    public static String[] stringSplit(String firstString, String secondString) {
        String[] splittedString = null;
        if (secondString.length() == 1) {
            splittedString = firstString.split(secondString);
            //System.out.println(splittedString[3]);
        }
        else if (secondString.length() > 1) {
            ArrayList<String> stringList = new ArrayList<String>();
            StringTokenizer tokenizer = new StringTokenizer(firstString, secondString);
            while (tokenizer.hasMoreTokens()) {
                //System.out.println(tokenizer.nextToken());
                stringList.add(tokenizer.nextToken());
            }
            splittedString = stringList.toArray(new String[0]);
        }
        else {
            System.out.println("Error!");
        }
        return splittedString;
    }

    public static String[] findRazd(String[] leksems) {
        ArrayList<String> findLeksList = new ArrayList<String>();
        String regex = "^[\\s,.;:!?()\\[\\] {}<>\"'\\\\/|_-]+$";
        for (int i = 0; i < leksems.length; i++) {
            if (leksems[i].matches(regex)) {
                findLeksList.add(leksems[i]);
            }
        }
        if (!findLeksList.isEmpty()) {
            return findLeksList.toArray(new String[0]);
        }
        return findLeksList.toArray(new String[0]);
    }

    public static String[] findDate(String leksems) {
        Pattern pattern = Pattern.compile("\\b(0[1-9]|1[0-2])\\s(\\d{2})\\s(\\d{2})\\b");
        Matcher matcher = pattern.matcher(leksems);
        ArrayList<String> validDates = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd yy");
        sdf.setLenient(false);
        while (matcher.find()) {
            String dateStr = matcher.group();
            try {
                sdf.parse(dateStr);
                validDates.add(dateStr);
            } catch (ParseException e) {
            }
        }
        validDates.sort(Comparator.comparingInt(date -> {
            String[] parts = date.split(" ");
            return Integer.parseInt(parts[2]);
        }));

        return validDates.toArray(new String[0]);
    }

    public static String addRandChislo(String[] leksems) {
        if (leksems == null || leksems.length == 0) {
            return "";
        }
        Random rand = new Random();
        StringBuilder result = new StringBuilder();
        String regex = "^[А-Яа-яЁё]+$";
        int russianIndex = -1;
        for (int i = 0; i < leksems.length; i++) {
            if (leksems[i].matches(regex)) {
                russianIndex = i;
                break;
            }
        }
        if (russianIndex != -1) {
            for (int i = 0; i <= russianIndex; i++) {
                result.append(leksems[i]).append(" ");
            }
            result.append(rand.nextInt()).append(" ");
            for (int i = russianIndex + 1; i < leksems.length; i++) {
                result.append(leksems[i]).append(" ");
            }
        } else {
            int mid = leksems.length / 2;
            for (int i = 0; i <= mid; i++) {
                result.append(leksems[i]).append(" ");
            }
            result.append(rand.nextInt()).append(" ");
            for (int i = mid + 1; i < leksems.length; i++) {
                result.append(leksems[i]).append(" ");
            }
        }
        return result.toString().trim();
    }

    public static String delSubstring(String input) {
        StringBuilder sb = new StringBuilder(input);
        int minLen = Integer.MAX_VALUE;
        int removeStart = -1;
        int removeEnd = -1;
        for (int start = 0; start < sb.length(); start++) {
            char first = sb.charAt(start);
            if (Character.toString(first).matches("[A-Za-zА-Яа-яЁё]")) {
                continue;
            }
            int end = start;
            int actualLen = 0;
            boolean digitFound = false;
            while (end < sb.length()) {
                char c = sb.charAt(end);
                if (c != ' ') actualLen++;
                if (Character.isDigit(c)) {
                    digitFound = true;
                    if (actualLen >= 2 && actualLen < minLen) {
                        minLen = actualLen;
                        removeStart = start;
                        removeEnd = end + 1;
                    }
                    break;
                }
                end++;
            }
        }
        if (removeStart != -1 && removeEnd != -1) {
            sb.delete(removeStart, removeEnd);
        }
        return sb.toString();
    }

    public static void printPercentCash(double percentValue, double currencyValue, double rawNumber) {
        Locale belarus = new Locale("be", "BY");
        NumberFormat percentFormat = NumberFormat.getPercentInstance(belarus);
        percentFormat.setMinimumFractionDigits(2);
        System.out.println("Процент: " + percentFormat.format(percentValue));
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(belarus);
        System.out.println("Сумма: " + currencyFormat.format(currencyValue));
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        System.out.println("Форматированное число: " + decimalFormat.format(rawNumber));
        Formatter formatter = new Formatter(belarus);
        formatter.format("Сумма через Formatter: %.2f BYN", currencyValue);
        System.out.println(formatter);
        formatter.close();
    }
}
