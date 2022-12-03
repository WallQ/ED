package FP1.Four;

import java.util.Collection;

public class Main {
    static void printCollection(Collection<Object> c) {
        for (Object e : c) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Collection<Object> Test = null;

        printCollection(Test);
    }
}
