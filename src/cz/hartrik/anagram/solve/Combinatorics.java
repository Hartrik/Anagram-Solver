package cz.hartrik.anagram.solve;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @version 2015-01-24
 * @author Patrik Harag
 */
public class Combinatorics {

    private Combinatorics() {}
    
    /**
     * Vrátí počet permutací s opakováním. Neprovádí úpravu vstupního textu.
     * Při velkých číslech může dojít k přetečení.
     * Vzorec: <code> n! / (n1! * n2! * ... * nk!) </code>
     * 
     * @param text text k analýze
     * @return počet permutací s opakováním
     */
    public static long permutations(String text) {
        Set<Integer> chars = text.chars().boxed().collect(Collectors.toSet());
        Long denominator = chars.stream()
                .map(i -> text.chars().filter(c -> c == i).count())
                .map(Combinatorics::factorial)
                .reduce(1L, (i, j) -> i * j);
        
        return factorial(text.length()) / denominator;
    }

    /**
     * Stejné jako {@link #permutations(String) permutations(String)}, ale
     * při přetečení vrací <code>-1</code>.
     * 
     * @see #permutations(String)
     * @param text text k analýze
     * @return počet permutací s opakováním
     */
    public static long permutationsExact(String text) {
        Set<Integer> chars = text.chars().boxed().collect(Collectors.toSet());
        
        long denominator = chars.stream()
                .map(i -> text.chars().filter(c -> c == i).count())
                .map(Combinatorics::factorialExact)
                .reduce(1L, (i, j)
                        -> (i == -1 || j == -1) ? -1 : multiplyExact(i, j));
        
        long numerator = factorialExact(text.length());
        
        return (denominator == -1 || numerator == -1)
                ? -1 : numerator / denominator;
    }
    
    private static long multiplyExact(long i, long j) {
        try {
            return Math.multiplyExact(i, j);
        } catch (ArithmeticException e) {
            return -1;
        }
    }
    
    public static long factorial(long number) {
        long factorial = 1;
        for (long i = 1; i <= number; ++i)
            factorial *= i;
        
        return factorial;
    }
    
    public static long factorialExact(long number) {
        try {
            long factorial = 1;
            for (long i = 1; i <= number; ++i)
                factorial = Math.multiplyExact(factorial, i);

            return factorial;
        } catch (ArithmeticException e) {
            return -1;
        }
    }
    
}