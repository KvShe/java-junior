package java_junior.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Напишите программу, которая использует Stream API для обработки списка чисел.
 * Программа должна вывести на экран среднее значение всех четных чисел в списке.
 */
public class Main {
    public static void main(String[] args) {
        List<Integer> integers = createList();
        double average = getAverage(integers);
        printAverageOfList(average, integers);
    }

    private static List<Integer> createList() {
        Random random = new Random();
        int size = 30;
        List<Integer> integers = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            integers.add(random.nextInt(100));
        }

        return integers;
    }

    private static double getAverage(List<Integer> integers) {
        return integers.stream()
                .filter(integer -> integer % 2 == 0)
                .mapToInt(i -> i)
                .average()
                .getAsDouble();
    }

    private static void printAverageOfList(double average, List<Integer> integers) {
        System.out.println("Average of all even numbers in a list " + integers + " = " + average);
    }
}
