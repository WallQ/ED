package FP1.Two;

import FP1.One.TwoTypePair;

public class Main {
    public static void main(String[] args) {
        Pair<String> TestString = new Pair<>("String 1", "String 2");
        Pair<Integer> TestInteger = new Pair<>(1, 2);
        Pair<Boolean> TestBoolean = new Pair<>(true, false);

        System.out.println(TestString.getFirst());
        System.out.println(TestString.getSecond());
        System.out.println(TestString.toString());
        TestString.setFirst("String 2");
        TestString.setSecond("String 1");
        System.out.println(TestString.toString());
        System.out.println(TestString.max());

        System.out.println(TestInteger.getFirst());
        System.out.println(TestInteger.getSecond());
        System.out.println(TestInteger.toString());
        TestInteger.setFirst(2);
        TestInteger.setSecond(1);
        System.out.println(TestInteger.toString());
        System.out.println(TestInteger.max());

        System.out.println(TestBoolean.getFirst());
        System.out.println(TestBoolean.getSecond());
        System.out.println(TestBoolean.toString());
        TestBoolean.setFirst(false);
        TestBoolean.setSecond(true);
        System.out.println(TestBoolean.toString());
        System.out.println(TestBoolean.max());
    }
}
