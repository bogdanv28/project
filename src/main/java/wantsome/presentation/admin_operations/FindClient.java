package wantsome.presentation.admin_operations;

import wantsome.business.OrderService;
import wantsome.database.dto.Flower;
import wantsome.database.dto.Order;

import java.util.List;

public class FindClient {
    private String name;

    public FindClient(String name) {
        this.name = name;
    }

    public String find() {
        return "Find client " +
                "<form method='post'>" +
                "<div style='background-color:#34ebcf; display:inline'>" +
                "  Enter client's name:<br>" +
                "<input type='text' name='name' value='" + name.toUpperCase() + "' required>" + "<br>" +
                "<input type='submit' value='Search'>" + "<br>" +
                "</form>";
    }

    public static String showList(String name) {
        if(entries(name).isEmpty()){
            return "<div>Not found: '" + name+"'<div>";
        }
        return "<br>Client: <br>" +
                "<div>" +
                "<table border=1; bgcolor='#c5e2f0'>" +
                entries(name) +
                "</table></div>";

    }

    private static String entries(String name) {
        OrderService orderService = OrderService.getInstance();
        List<Order> listO = orderService.findByName(name.toUpperCase());
        String f = "";
        for (Order order : listO) {
            String line = "<tr>" +
                    "<td>" + order + "</td>" +
                    "</tr>";
            f += line;

        }
        return f;
    }
}
