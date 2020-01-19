package wantsome.presentation.client_operations;

public class NotEnoughStock {

    public static String message(String name, int quantity){
        return "<div><b><h2> Not enough stock of '" + name+"'. Available in stock: '" + quantity + "'.</h2></b> </div>";
    }
}
