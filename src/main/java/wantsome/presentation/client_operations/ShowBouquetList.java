package wantsome.presentation.client_operations;

import wantsome.business.BouquetEntryService;
import wantsome.business.BouquetService;
import wantsome.business.OrderService;
import wantsome.database.dto.Bouquet;


import static java.util.stream.Collectors.joining;

public class ShowBouquetList {
    private static BouquetService bouquetService = BouquetService.getInstance();
    private static BouquetEntryService bouquetEntryService = BouquetEntryService.getInstance();
    private static OrderService orderService = OrderService.getInstance();


    public static String buildBouquetTable() {


        orderService.setPrice(getTotalPrice());
        String headerRow = "<tr><th>Bouquet ID</th><th>Price</th><th>Contains</th></tr>";
        String tableRows = bouquetService.seeBouquetListByOrderId(orderService.getLastId()).stream()
                .map(b -> String.format("<tr> <td>%s</td> <td>%s</td> <td>%s</td> <td>%s</td></tr>",
                        b.getId(), b.getPrice(), bouquetEntryService.entryListByBouquetId2(b.getId()),
                        "<a href='/removeBouquet/" + b.getId() + "'>Remove this bouquet</a>"))
                .collect(joining(""));

        return "<br><div>Your order:</div>" +
                "<table border=1>" + headerRow + tableRows + "</table><br>" +
                "<div><b><font size='4'>Total Price: " + getTotalPrice() + "</font></b></div>" +
                createNewBouquet();
    }

    private static String createNewBouquet() {
        OrderService orderService = OrderService.getInstance();
        return "<br><form method='post'>" +
                "<input type='hidden' name='orderID' value='" + orderService.getLastId() + "'>" +
                "<input type='submit' value='Add new Bouquet'>" + "<br>" +
                "</form>";
    }

    private static int getTotalPrice() {
        int totalPrice = 0;
        for (Bouquet bouquet : bouquetService.seeBouquetListByOrderId(orderService.getLastId())) {
            totalPrice += bouquet.getPrice();
        }
        return totalPrice;
    }
}
