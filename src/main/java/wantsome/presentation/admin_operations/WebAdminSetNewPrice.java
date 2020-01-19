package wantsome.presentation.admin_operations;

public class WebAdminSetNewPrice {
    private String id;
    private String newPrice;

    public WebAdminSetNewPrice(String id, String newPrice) {
        this.id = id;
        this.newPrice = newPrice;
    }

    public String newPrice() {
        return "Set new price, flower with ID: " +
                "<form method='post'>" +
                "<div style='background-color:#34ebcf; display:inline'>" +
                "  Enter the id of flower where you want to set new price:<br>" +
                "<input type='text' name='id' value='" + id + "' required>" + "<br>" +
                "  Enter the new price:<br>" +
                "<input type='text' name='newPrice' value='" + newPrice + "' required>" + "<br>" +
                "<input type='submit' value='Update'>" + "<br>" +
                "</form>";
    }
}
