package main.pl.ostrowski.dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class BloomFilter {


    private static final int BYTE_ARRAY_SIZE = 10_000;
    private byte[] dictionary;
    private ArrayList<Function<String,Integer>> hashAlgorithms;



    public BloomFilter() {
        dictionary = new byte[BYTE_ARRAY_SIZE];
        hashAlgorithms = new ArrayList<>();

        initHashAlgorithms();
    }

    private void initHashAlgorithms() {
        hashAlgorithms.add(this::customHashFunction);
        hashAlgorithms.add(String::hashCode);
    }

    public Set<Integer> add(String wordToAdd) {
        if(wordToAdd == null || wordToAdd.equals("")) {
            return Collections.emptySet();
        }
        Set<Integer> indices = calculateHashValues(wordToAdd);
        for(Integer i : indices) {
            dictionary[i] = 1;
        }
        return indices;
    }

    private Set<Integer> calculateHashValues(String wordToAdd) {
        HashSet<Integer> indices = new HashSet<>();
        for(Function<String, Integer> f : hashAlgorithms) {
            indices.add(Math.abs(f.apply(wordToAdd)) % BYTE_ARRAY_SIZE);
        }
        return indices;
    }


    private int customHashFunction(String wordToHash) {
        int hash = 7;
        for (int i = 0; i < wordToHash.length(); i++) {
            hash = hash*31 + wordToHash.charAt(i);
        }
        return hash % BYTE_ARRAY_SIZE;
    }

    public boolean test(String wordToTest) {
        if(wordToTest == null || wordToTest.equals("")) {
            return false;
        }
        Set<Integer> indices = calculateHashValues(wordToTest);
        for(Integer i : indices) {
            if(dictionary[i] != 1) {
                return false;
            }
        }
        return true;
    }

    public int getAlgorithmCount() {
        return hashAlgorithms.size();
    }
}