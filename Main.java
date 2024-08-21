package java_junior.homework;

import java.lang.reflect.Method;

/**
 * Используя Reflection API, напишите программу, которая выводит на экран все методы класса String.
 */
public class Main {
    public static void main(String[] args) {
        Method[] methods = String.class.getDeclaredMethods();

        System.out.println("All methods class String: ");
        printAllMethods(methods);
    }

    private static void printAllMethods(Method[] methods) {
        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }
}
