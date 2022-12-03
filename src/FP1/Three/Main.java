package FP1.Three;

public class Main {
    public static void main(String[] args) {
        UnorderedPair<String> TestString = new UnorderedPair<String>("String 1", "String 2");
        UnorderedPair<Integer> TestInteger = new UnorderedPair<Integer>(1, 2);
        UnorderedPair<Boolean> TestBoolean = new UnorderedPair<Boolean>(true, false);

        System.out.println(TestString.getFirst());
        System.out.println(TestString.getSecond());
        if (TestString.getFirst().equals(TestString.getSecond())) {
            System.out.println(TestString.getFirst() + " and " + TestString.getSecond() + " are the same!");
        } else {
            System.out.println(TestString.getFirst() + " and " + TestString.getSecond() + " aren't the same!");
        }

        System.out.println(TestInteger.getFirst());
        System.out.println(TestInteger.getSecond());
        if (TestInteger.getFirst().equals(TestInteger.getSecond())) {
            System.out.println(TestInteger.getFirst() + " and " + TestInteger.getSecond() + " are the same!");
        } else {
            System.out.println(TestInteger.getFirst() + " and " + TestInteger.getSecond() + " aren't the same!");
        }

        System.out.println(TestBoolean.getFirst());
        System.out.println(TestBoolean.getSecond());
        if (TestBoolean.getFirst().equals(TestBoolean.getSecond())) {
            System.out.println(TestBoolean.getFirst() + " and " + TestBoolean.getSecond() + " are the same!");
        } else {
            System.out.println(TestBoolean.getFirst() + " and " + TestBoolean.getSecond() + " aren't the same!");
        }
    }
}
