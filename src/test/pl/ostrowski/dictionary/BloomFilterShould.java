package test.pl.ostrowski.dictionary;

import main.pl.ostrowski.dictionary.BloomFilter;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class BloomFilterShould {


    BloomFilter bloomFilter;

    @Before
    public void setUp() {
        bloomFilter = new BloomFilter();
    }

    @Test
    public void add_new_word() {
        String wordToAdd = "kacper";
        Set<Integer> indices = bloomFilter.add(wordToAdd);
        assertEquals(indices.size(), bloomFilter.getAlgorithmCount());
    }

    @Test
    public void test_existing_word() {
        String wordToAdd = "kacper";
        Set<Integer> indices = bloomFilter.add(wordToAdd);

        boolean result = bloomFilter.test(wordToAdd);
        assertEquals(result, true);
    }

    @Test
    public void test_non_existing_word() {
        String wordToAdd = "kacper";
        Set<Integer> indices = bloomFilter.add(wordToAdd);

        String wordToTest = "nie-kacper";
        boolean result = bloomFilter.test(wordToTest);
        assertEquals(result, false);
    }

    @Test
    public void test_add_null_word() {
        Set<Integer> indices = bloomFilter.add(null);
        assertEquals(indices.size(), 0);
    }

    @Test
    public void test_test_null_word() {
        boolean result = bloomFilter.test(null);
        assertEquals(result, false);
    }
}