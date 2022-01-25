package sample;

import java.math.BigInteger;

public class Program {
    public static void main(String[] args) {
        // a^b mod c
        BigInteger a = new BigInteger("5");
        BigInteger b = new BigInteger("3");
        BigInteger c = new BigInteger("101");

        BigInteger actualValue = a.modPow(b, c);
        System.out.println(actualValue);

        BigInteger expectedValue = new BigInteger("21");
        System.out.println(expectedValue);
    }
}


