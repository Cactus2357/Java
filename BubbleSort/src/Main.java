
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hi
 */
public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Random r = new Random(System.nanoTime());
        int size, arr[];
        while (true) {
            try {
                System.out.print("Enter number of array: ");
                size = Integer.parseInt(s.nextLine());
                arr = new int[size];
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        for (int i = 0; i < size; i++) {
            arr[i] = r.nextInt(size);
        }

        System.out.println("Unsorted: " + Arrays.toString(arr));
        System.out.println("Sorted:   " + Arrays.toString(bubbleSort(arr)));

    }

    public static int[] bubbleSort(int arr[]) {
        int len = arr.length, _arr[] = arr;
        boolean _swaped = true;
        TreeSet<Integer> change = new TreeSet<>();

        while (_swaped) {
            _swaped = false;
            for (int i = 0, t; i < len - 1; i++) {
                if (_arr[i] > _arr[i + 1]) {
                    change.add(i);
                    change.add(i + 1);
                    t = _arr[i];
                    _arr[i] = _arr[i + 1];
                    _arr[i + 1] = t;
                    _swaped = true;
                }
            }
            if (_swaped) {
                System.out.println("          " + showChange(_arr, change));
                change.clear();
            }

        }
        return _arr;
    }

    private static String showChange(int arr[], TreeSet<Integer> change) {
        String redBg = "\u001B[41m";  // Red background
        String greenBg = "\u001B[42m";  // Green background
        String reset = "\u001B[0m";  // Reset color to default
        String red = "\u001B[31m";  // Red
        String green = "\u001B[32m";  // Green

        StringBuilder str = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            
            str.append("\u001B[34m");
            
            if (change.contains(i)) {
                str.append(green);
            }
            str.append(arr[i]);
            if (i + 1 < arr.length) {
                str.append(", ");
            }
            if (!change.contains(i + 1)) {
                str.append(reset);
            }
        }
        return str.append("]").toString();
    }
}
