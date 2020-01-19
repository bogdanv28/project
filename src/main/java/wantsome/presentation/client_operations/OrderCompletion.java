package wantsome.presentation.client_operations;

import wantsome.business.OrderService;

public class OrderCompletion {
    private static OrderService orderService = OrderService.getInstance();

    public static String complete() {

        if (orderService.findByID(orderService.getLastId()).getTotalPrice() < 25) {
            return "<p style='font-size:160%;' ><b>Minimum value of order is 25.</b></p>";
        }

        return "<button onclick='myFunction()'>Complete order</button>" +
                "<script>" +
                "function myFunction() {" +
                " window.location.href = '/completedOrder'; " +
                "}" +
                "</script>";
    }
}
