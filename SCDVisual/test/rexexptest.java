/**
 * Created by Сергей on 24.09.2015.
 */
public class rexexptest {
    final private static String DIGITPATERN1 = "[\\-]{0,1}[0123456789]{1,}";
    final private static String DIGITPATERN2 = "[0123456789]{1,}";
    final private static String DIGITPATERN3 = "[-]{0,1}[0123456789]{1,}";

    public static void main(String[] args) {
        String text = "-546546";
        System.out.println(text.matches(DIGITPATERN1));
        System.out.println(text.matches(DIGITPATERN2));
        System.out.println(text.matches(DIGITPATERN3));
    }
}
