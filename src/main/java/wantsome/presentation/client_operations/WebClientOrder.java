package wantsome.presentation.client_operations;

import wantsome.business.BouquetEntryService;
import wantsome.business.BouquetService;
import wantsome.business.OrderService;
import wantsome.business.mail.SendMailBySite;

import javax.mail.MessagingException;
import java.util.stream.Collectors;

public class WebClientOrder {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;

    public WebClientOrder(String firstName, String lastName, String phoneNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String createOrder() {
        return "Client Info: " +
                "<form method='post'>" +
                "<div style='background-color:#34ebcf; display:inline'>" +
                "  First Name:<br>" +
                "<input type='text' name='firstName' value='" + firstName + "' required>" + "<br>" +
                "  Last Name:<br>" +
                "<input type='text' name='lastName' value='" + lastName + "' required>" + "<br>" +
                "  Phone Number:<br>" +
                "<input type='text' name='phoneNumber' value='" + phoneNumber + "' required>" + "<br>" +
                "  Delivery Address:<br>" +
                "<input type='text' name='address' value='" + address + "' required>" + "<br>" +
                "<input type='submit' value='Next' >" + "<br>" +
                "</form>";
    }

    public String completeOrder() throws MessagingException {
        BouquetService bouquetService = BouquetService.getInstance();
        OrderService orderService = OrderService.getInstance();
        BouquetEntryService bouquetEntryService = BouquetEntryService.getInstance();
        int orderId = orderService.getLastId();
        CheckFlowerStock.check();
        SendMailBySite.sendPlainTextEmail("New Order! Order number: " + orderService.getLastId(),
                orderService.findByID(orderId).toString() + "\n"
                        + bouquetService.seeBouquetListByOrderId(orderId).stream()
                        .map(b -> "\nBouquet number: " + b.getId() + ", price: " + b.getPrice() + ", contains: " + bouquetEntryService.entryListByBouquetId2(b.getId()))
                        .collect(Collectors.joining()));

        return "<p style='font-size:160%;' >Order completed. Thank you!</p>";
    }
}
