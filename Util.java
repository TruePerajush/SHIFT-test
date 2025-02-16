import java.io.*;
import java.util.Vector;

public class Util {
    enum Statistic{
        Short, Long, None
    }
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Не указаны параметры");
            return;
        }

        String prefix = "";
        String path = "";
        Statistic stat = Statistic.None;
        boolean isAppend = false;
        Vector<String> files = new Vector<>();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-s" -> {
                    stat = Statistic.Short;
                    continue;
                }
                case "-f" -> {
                    stat = Statistic.Long;
                    continue;
                }
                case "-p" -> {
                    prefix = args[++i];
                    continue;
                }
                case "-o" -> {
                    path = args[++i];
                    continue;
                }
                case "-a" -> {
                    isAppend = true;
                    continue;
                }
            }
            files.add(args[i]);
        }

        if(path.startsWith("\\")){
            path = '.' + path;
        }

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (!path.isEmpty()) {
            path += '\\';
        }

        String intFile = path + prefix + "integers.txt";
        String floatFile = path + prefix + "floats.txt";
        String stringFile = path + prefix + "strings.txt";

        Vector<String> ints = new Vector<>();
        Vector<String> floats = new Vector<>();
        Vector<String> strings = new Vector<>();

        for (String fileName : files) {
            try (BufferedReader reader = new BufferedReader(new java.io.FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (isInteger(line)) {
                        ints.add(line);
                    } else if (isFloat(line)) {
                        floats.add(line);
                    } else {
                        strings.add(line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла: " + fileName);
                return;
            }
        }

        makeStatistic(stat, ints, floats, strings);

        if(!ints.isEmpty()){
            try (FileWriter writer = new FileWriter(intFile, isAppend)){
                writeToFile(ints, writer);
            }catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
        if(!floats.isEmpty()){
            try (FileWriter writer = new FileWriter(floatFile, isAppend)){
                writeToFile(ints, writer);
            }catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
        if(!strings.isEmpty()){
            try (FileWriter writer = new FileWriter(stringFile, isAppend)){
                writeToFile(ints, writer);
            }catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
    }
    public static void makeStatistic(Statistic stat, Vector<String> ints, Vector<String> floats, Vector<String> strings){
        switch (stat) {
            case Short -> {
                System.out.printf("Количество целых чисел: %d\n", ints.size());
                System.out.printf("Количество вещественных чисел: %d\n", floats.size());
                System.out.printf("Количество строк: %d\n", strings.size());
            }
            case Long -> {
                System.out.printf("Целые числа.\nКоличество: %d\nСумма: %d\nСреднее: %d\nМинимальное: %d\nМаксимальное: %d\n\n", ints.size(), sumInteger(ints), sumInteger(ints)/ints.size(), minInteger(ints), maxInteger(ints));

                System.out.printf("Вещественные числа.\nКоличество: %d\nСумма: %f\nСреднее: %f\nМинимальное: %f\nМаксимальное: %f\n\n", floats.size(), sumFloats(floats), sumFloats(floats)/floats.size(), minFloat(floats), maxFloat(floats));

                System.out.printf("Строки.\nКоличество: %d\nМинимальная: %d\nМаксимальная: %d\n\n", strings.size(), minString(strings), maxString(strings));
            }
            case None -> {
            }
        }
    }
    public static int maxString(Vector<String> strings){
        int max = 0;
        for (String string : strings) {
            max = Math.max(max, string.length());
        }
        return max;
    }
    public static int minString(Vector<String> strings){
        int min = Integer.MAX_VALUE;
        for (String string : strings) {
            min = Math.min(min, string.length());
        }
        return min;
    }
    public static long maxInteger(Vector<String> ints){
        long max = Long.MIN_VALUE;
        for (String s : ints) {
            max = Math.max(max, Long.parseLong(s));
        }
        return max;
    }
    public static double maxFloat(Vector<String> floats){
        double max = Double.MIN_VALUE;
        for (String s : floats) {
            max = Math.max(max, Double.parseDouble(s));
        }
        return max;
    }
    public static long minInteger(Vector<String> ints){
        long min = Long.MAX_VALUE;
        for (String s : ints) {
            min = Math.min(min, Long.parseLong(s));
        }
        return min;
    }
    public static double minFloat(Vector<String> floats){
        double min = Double.MAX_VALUE;
        for (String s : floats) {
            min = Math.min(min, Double.parseDouble(s));
        }
        return min;
    }
    public static long sumInteger(Vector<String> ints){
        long sum = 0;
        for (String s : ints) {
            sum += Long.parseLong(s);
        }
        return sum;
    }
    public static double sumFloats(Vector<String> floats){
        double sum = 0;
        for (String s : floats) {
            sum += Double.parseDouble(s);
        }
        return sum;
    }
    //Запись отфильтрованных строк в файл
    public static void writeToFile(Vector<String> to_write, FileWriter fw) throws IOException {
        for (String line : to_write) {
            fw.write(line);
        }
    }
    // Проверка, является ли строка целым числом
    public static boolean isInteger(String input) {
        try {
            Long.parseLong(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    // Проверка, является ли строка вещественным числом
    public static boolean isFloat(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}