package com.example.javademo.datastructure;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        int[] A = {5,8,30,12,9,10,19,2};
        MergeSort sort = new MergeSort();
        sort.sort(A);
//        System.out.println(Arrays.asList(A));
    }

    public void sort(int[] A) {
        mergeSort(A, 0, A.length);
    }
    private void mergeSort(int[] A, int l, int r) {

        if (r -l <= 1) {
            return;
        }
        int mid = (l+r+1)/2;
        mergeSort(A, l, mid);
        mergeSort(A, mid, r);
        merge(A, l, mid, r);
        System.out.println(JSON.toJSONString(A));
    }

    private void merge(int[] A, int l, int mid, int r) {
        int[] B = Arrays.copyOfRange(A, l, mid + 1);
        int[] C = Arrays.copyOfRange(A, mid, r+1);
        B[B.length -1] = Integer.MAX_VALUE;
        C[C.length - 1] = Integer.MAX_VALUE;
        int i = 0, j = 0;
        for (int k = l; k < r; k++) {
            if (B[i] < C[j]) {
                A[k] = B[i++];
            }else {
                A[k] = C[j++];
            }
        }

    }
}
