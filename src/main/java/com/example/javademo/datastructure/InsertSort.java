package com.example.javademo.datastructure;

public class InsertSort {

    public static void sort(int[] A) {

        for (int i = 1; i < A.length; i++) {

            int c = A[i];
            int j = i;

            for (;j > 0 && A[j-1] > c; j--) {
                A[j] = A[j-1];
            }
            A[j] = c;
        }
        for (int i = 0; i < A.length; i++) {
            System.out.println(A[i]);
        }
    }

    public static void main(String[] args) {
        int[] A = {5,2,3,1};
        sort(A);
    }

}
