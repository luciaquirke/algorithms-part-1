/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

public class HelloGoodbye {
    public static void main(String[] args) {

        if (args == null || args.length != 2) {
            System.out.println("Not enough arguments");
            return;
        }

        if (args[0] == null || args[1] == null) {
            System.out.println("Not enough arguments");
            return;
        }

        String name1 = args[0];
        String name2 = args[1];

        System.out.println("Hello " + name1 + " and " + name2 + ".");
        System.out.println("Goodbye " + name2 + " and " + name1 + ".");
    }
}
