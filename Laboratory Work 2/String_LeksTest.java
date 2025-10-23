import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

class String_LeksTest {

    private File tempInputFile;
    private File tempOutputFile;

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws IOException {
        tempInputFile = tempDir.resolve("test_input.txt").toFile();
        tempOutputFile = tempDir.resolve("test_output.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempInputFile))) {
            writer.write("hello,chelovek,test 01 01 01 hoho,.!proga");
            writer.newLine();
            writer.write(",.!");
        }
    }

    @AfterEach
    void tearDown() {
        if (tempInputFile.exists()) tempInputFile.delete();
        if (tempOutputFile.exists()) tempOutputFile.delete();
    }

    @Test
    void fileRead() throws IOException {
        String testData = "first line\nsecond line";
        Files.write(tempInputFile.toPath(), testData.getBytes());
        String[] result = String_Leks.fileRead();
        assertNotNull(result);
        assertEquals(2, result.length);
        assertEquals("first line", result[0]);
        assertEquals("second line", result[1]);
    }

    @Test
    void fileRead_FileNotFound() {
        tempInputFile.delete();
        String[] result = String_Leks.fileRead();
        assertNotNull(result);
    }

    @Test
    void fileWrite() throws IOException {
        String testString = "Test output string";
        String_Leks.fileWrite(testString);
        assertTrue(tempOutputFile.exists());
        String content = Files.readString(tempOutputFile.toPath());
        assertTrue(content.contains(testString));
    }

    @Test
    void formString() {
        String[] input = {"hello", "world", "test"};
        String expected = "hello world test";
        String result = String_Leks.formString(input);
        assertEquals(expected, result);
    }

    @Test
    void formString_EmptyArray() {
        String[] input = {};
        String expected = "";
        String result = String_Leks.formString(input);
        assertEquals(expected, result);
    }

    @Test
    void formString_NullArray() {
        String result = String_Leks.formString(null);
        assertEquals("", result);
    }

    @Test
    void formStringDate() {
        String[] input = {"01 01 01", "02 02 02", "03 03 03"};
        String expected = "01 01 01\n02 02 02\n03 03 03\n";
        String result = String_Leks.formStringDate(input);
        assertEquals(expected, result);
    }

    @Test
    void stringSplit_SingleDelimiter() {
        String firstString = "hello,world,test";
        String secondString = ",";
        String[] result = String_Leks.stringSplit(firstString, secondString);
        assertNotNull(result);
        assertEquals(3, result.length);
        assertArrayEquals(new String[]{"hello", "world", "test"}, result);
    }

    @Test
    void stringSplit_MultipleDelimiters() {
        String firstString = "hello,world.test;data";
        String secondString = ",.;";
        String[] result = String_Leks.stringSplit(firstString, secondString);
        assertNotNull(result);
        assertEquals(4, result.length);
        assertArrayEquals(new String[]{"hello", "world", "test", "data"}, result);
    }

    @Test
    void stringSplit_ComplexDelimiters() {
        String firstString = "hello,.world!.test";
        String secondString = ",.!";
        String[] result = String_Leks.stringSplit(firstString, secondString);
        assertNotNull(result);
        assertEquals(3, result.length);
    }

    @Test
    void stringSplit_NullInput() {
        String[] result = String_Leks.stringSplit(null, ",");
        assertNotNull(result);
        assertEquals(0, result.length);
    }

    @Test
    void findRazd() {
        String[] leksems = {"hello", ",", "world", ".", "test", "!", "data"};
        String[] expected = {",", ".", "!"};
        String[] result = String_Leks.findRazd(leksems);
        assertArrayEquals(expected, result);
    }

    @Test
    void findRazd_NoDelimiters() {
        String[] leksems = {"hello", "world", "test"};
        String[] expected = {};
        String[] result = String_Leks.findRazd(leksems);
        assertArrayEquals(expected, result);
    }

    @Test
    void findRazd_OnlyDelimiters() {
        String[] leksems = {",", ".", "!", ";", ":"};
        String[] expected = {",", ".", "!", ";", ":"};
        String[] result = String_Leks.findRazd(leksems);
        assertArrayEquals(expected, result);
    }

    @Test
    void findDate_ValidDates() {
        String leksems = "Some text with 01 01 01 date and 12 31 99 another";
        String[] expected = {"01 01 01", "12 31 99"};
        String[] result = String_Leks.findDate(leksems);
        assertArrayEquals(expected, result);
    }

    @Test
    void findDate_InvalidDates() {
        String leksems = "Invalid 13 01 01 and 00 01 01 dates 99 99 99";
        String[] expected = {};
        String[] result = String_Leks.findDate(leksems);
        assertArrayEquals(expected, result);
    }

    @Test
    void findDate_MixedValidInvalid() {
        String leksems = "Valid 01 01 01 and invalid 13 01 01 dates";
        String[] expected = {"01 01 01"};
        String[] result = String_Leks.findDate(leksems);
        assertArrayEquals(expected, result);
    }

    @Test
    void findDate_EmptyString() {
        String leksems = "";
        String[] expected = {};
        String[] result = String_Leks.findDate(leksems);
        assertArrayEquals(expected, result);
    }

    @Test
    void addRandChislo_WithRussianWord() {
        String[] leksems = {"hello", "world", "привет", "test", "data"};
        String result = String_Leks.addRandChislo(leksems);
        assertNotNull(result);
        String[] parts = result.split(" ");
        boolean foundRussian = false;
        boolean foundNumber = false;

        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("привет")) {
                foundRussian = true;
            }
            if (foundRussian && i + 1 < parts.length) {
                try {
                    Long.parseLong(parts[i + 1]);
                    foundNumber = true;
                    break;
                } catch (NumberFormatException e) {
                }
            }
        }
        assertTrue(foundRussian && foundNumber, "Число должно быть добавлено после русского слова");
    }

    @Test
    void addRandChislo_NoRussianWord() {
        String[] leksems = {"hello", "world", "test", "data"};
        String result = String_Leks.addRandChislo(leksems);
        assertNotNull(result);
        String[] parts = result.split(" ");
        boolean foundNumber = false;
        for (String part : parts) {
            try {
                Long.parseLong(part);
                foundNumber = true;
                break;
            } catch (NumberFormatException e) {
            }
        }
        assertTrue(foundNumber, "Число должно быть добавлено в середину когда нет русских слов");
    }

    @Test
    void addRandChislo_EmptyArray() {
        String[] leksems = {};
        String result = String_Leks.addRandChislo(leksems);
        assertEquals("", result);
    }

    @Test
    void addRandChislo_NullArray() {
        String result = String_Leks.addRandChislo(null);
        assertEquals("", result);
    }

    @Test
    void delSubstring_BasicCase() {
        String input = "hello!123world";
        String expected = "hello23world";
        String result = String_Leks.delSubstring(input);
        assertEquals(expected, result);
    }

    @Test
    void delSubstring_MultipleCandidates() {
        String input = "a!1b@2c#3d";
        String result = String_Leks.delSubstring(input);
        assertTrue(result.contains("a") && result.contains("b") && result.contains("c") && result.contains("d"));
        assertTrue(result.length() < input.length());
    }

    @Test
    void delSubstring_NoSuitableSubstring() {
        String input = "hello world test";
        String expected = "hello world test";
        String result = String_Leks.delSubstring(input);
        assertEquals(expected, result);
    }

    @Test
    void delSubstring_StartsWithLetter() {
        String input = "a1b2c3";
        String expected = "a1b2c3";
        String result = String_Leks.delSubstring(input);
        assertEquals(expected, result);
    }

    @Test
    void delSubstring_EndsWithNonDigit() {
        String input = "!abc@";
        String expected = "!abc@";
        String result = String_Leks.delSubstring(input);
        assertEquals(expected, result);
    }

    @Test
    void delSubstring_EmptyString() {
        String input = "";
        String expected = "";
        String result = String_Leks.delSubstring(input);
        assertEquals(expected, result);
    }

    @Test
    void delSubstring_NullInput() {
        String result = String_Leks.delSubstring(null);
        assertNull(result);
    }

    @Test
    void printPercentCash() {
        assertDoesNotThrow(() -> {
            String_Leks.printPercentCash(0.875, 1234.56, 9876543.21);
        });
    }

    @Test
    void integrationTest_CompleteWorkflow() {
        String firstString = "hello,world,01 01 01 привет,.!test";
        String secondString = ",.!";
        String[] leksems = String_Leks.stringSplit(firstString, secondString);
        String[] razd = String_Leks.findRazd(leksems);
        String withRandom = String_Leks.addRandChislo(leksems);
        String dates = String_Leks.formString(String_Leks.findDate(String_Leks.formString(leksems)));
        String cleaned = String_Leks.delSubstring(String_Leks.formString(leksems));
        assertNotNull(leksems);
        assertNotNull(razd);
        assertNotNull(withRandom);
        assertNotNull(dates);
        assertNotNull(cleaned);
        assertTrue(razd.length > 0);
        assertTrue(dates.contains("01 01 01"));
    }

    @Test
    void integrationTest_FileOperations() throws IOException {
        String[] input = String_Leks.fileRead();
        assertNotNull(input);
        assertTrue(input.length >= 2);
        String[] leksems = String_Leks.stringSplit(input[0], input[1]);
        assertNotNull(leksems);
        String result = String_Leks.formString(leksems);
        assertNotNull(result);
        String_Leks.fileWrite(result);
        assertTrue(tempOutputFile.exists());
    }
}