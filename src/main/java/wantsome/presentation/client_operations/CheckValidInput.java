package wantsome.presentation.client_operations;

public class CheckValidInput {

    public static boolean isValidNumber(String n) {
        if (n == null) {
            return false;
        }
        try {
            int x = Integer.parseInt(n);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isNegative(String quantity){
        if (Integer.parseInt(quantity) <= 0){
            return true;
        }
        return false;
    }

}
