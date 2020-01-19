package wantsome.presentation.admin_operations;

public class WebAdminSetNewStock {

    private String id;
    private String newStock;

    public WebAdminSetNewStock(String id, String newStock) {
        this.id = id;
        this.newStock = newStock;
    }

    public String newStock() {
        return "Set new stock, flower with ID: " +
                "<form method='post'>" +
                "<div style='background-color:#34ebcf; display:inline'>" +
                "  Enter the id of flower where you want to set new stock:<br>" +
                "<input type='text' name='id' value='" + id + "' required>" + "<br>" +
                "  Enter the new stock:<br>" +
                "<input type='text' name='newAmount' value='" + newStock + "' required>" + "<br>" +
                "<input type='submit' value='Update'>" + "<br>" +
                "</form>";
    }
}
