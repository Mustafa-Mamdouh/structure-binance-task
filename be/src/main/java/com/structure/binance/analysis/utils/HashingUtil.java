package com.structure.binance.analysis.utils;

public class HashingUtil {
    //This is hashing function for lowercase string
    //Reference: https://cp-algorithms.com/string/string-hashing.html
    public static int compute_hash(String s, int length) {
        final int p = 31;
        final int m = length;
        double hash_value = 0d;
        double p_pow = 1d;
        for (char c : s.toCharArray()) {
            hash_value = (hash_value + (c + 1) * p_pow) % m;
            p_pow = (p_pow * p) % m;
        }
        return (int) hash_value;
    }

    public static void main(String[] args) {
        System.out.println(compute_hash("DODOBTC".toLowerCase(), 2500));
        System.out.println(compute_hash("BICOUSDT".toLowerCase(), 2500));
    }
}
