package com.example.tripper.algorithm;

import java.math.BigInteger;

public class Combinations {
    private int[] a;
    private int[] set;
    private int[] index;
    private int n;
    private int r;
    int numLeft;
    private int total;

    Combinations(int n, int r) {
        this.n = n;
        setStandardConstructor(r);
        resetNumber();
    }

    private void setStandardConstructor(int r) {
        this.r = r;
        if(r > n || n < 1) {
            throw new IllegalArgumentException();
        }
        a = new int[r];
        setTotal();
    }

    private void setTotal() {
        BigInteger nFact = getFactorial(n);
        BigInteger rFact = getFactorial(r);
        BigInteger nminusrFact = getFactorial(n - r);
        BigInteger resTotal = nFact.divide(rFact.multiply(nminusrFact));
        total = resTotal.intValue();
    }

    private void resetNumber() {
        for (int i = 0; i < a.length; i++) {
            a[i] = i + 1; //starting city == 0
        }
        numLeft = total;
    }

    private static BigInteger getFactorial(int n) {
        BigInteger fact = BigInteger.ONE;
        for (int i = n; i > 1; i--) {
            fact = fact.multiply (new BigInteger (Integer.toString (i)));
        }
        return fact;
    }

    //--------------------------------------------------------
    // Generate next combination (algorithm from Rosen p. 286)
    //--------------------------------------------------------
    // Number combination
    int[] getNext() {
        if (numLeft == total) {
            numLeft--;
            return a;
        }
        int i = r - 1;
        while (a[i] == n - r + i + 1) {
            i--;
        }
        a[i] = a[i] + 1;
        for (int j = i + 1; j < r; j++) {
            a[j] = a[i] + j - i;
        }
        numLeft--;
        return a;
    }

    // Set combination
    public int[] getNextSet() {
        if (numLeft == total) {
            numLeft--;
            return a;
        }
        int i = r - 1;
        while (a[i] == set[n - r + i]) {
            i--;
        }
        index[i]++;
        a[i] = set[index[i]];
        for (int j = i + 1; j < r; j++) {
            index[j] = index[i] + j - i;
            a[j] = set[index[j]];
        }
        numLeft--;
        return a;
    }
}
