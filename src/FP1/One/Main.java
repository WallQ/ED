package FP1.One;

public class Main {
    public static void main(String[] args) {
        TwoTypePair<String, String> TestString = new TwoTypePair<>("String 1", "String 2");
        TwoTypePair<Integer, Integer> TestInteger = new TwoTypePair<>(1, 2);
        TwoTypePair<Boolean, Boolean> TestBoolean = new TwoTypePair<>(true, false);

        System.out.println(TestString.getFirst());
        System.out.println(TestString.getSecond());
        System.out.println(TestString.toString());
        TestString.setFirst("String 2");
        TestString.setSecond("String 1");
        System.out.println(TestString.toString());
        System.out.println(TestString.equals(TestString));
        System.out.println(TestString.equals(TestInteger));
        System.out.println(TestString.equals(TestBoolean));

        System.out.println(TestInteger.getFirst());
        System.out.println(TestInteger.getSecond());
        System.out.println(TestInteger.toString());
        TestInteger.setFirst(2);
        TestInteger.setSecond(1);
        System.out.println(TestInteger.toString());
        System.out.println(TestInteger.equals(TestString));
        System.out.println(TestInteger.equals(TestInteger));
        System.out.println(TestInteger.equals(TestBoolean));

        System.out.println(TestBoolean.getFirst());
        System.out.println(TestBoolean.getSecond());
        System.out.println(TestBoolean.toString());
        TestBoolean.setFirst(false);
        TestBoolean.setSecond(true);
        System.out.println(TestBoolean.toString());
        System.out.println(TestBoolean.equals(TestString));
        System.out.println(TestBoolean.equals(TestInteger));
        System.out.println(TestBoolean.equals(TestBoolean));
    }
}
