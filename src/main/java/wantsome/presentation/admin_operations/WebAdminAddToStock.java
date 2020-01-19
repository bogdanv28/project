package wantsome.presentation.admin_operations;

public class WebAdminAddToStock {
    private String id;
    private String amountToBeAdded;

    public WebAdminAddToStock(String id, String amountToBeAdded) {
        this.id = id;
        this.amountToBeAdded = amountToBeAdded;
    }

    public String addToStock() {
        return "Add to stock, flower with ID: " +
                "<form method='post'>" +
                "<div style='background-color:#34ebcf; display:inline'>" +
                "  Enter the id of flower where you want to add to stock:<br>" +
                "<input type='text' name='id' value='" + id + "' required>" + "<br>" +
                "  Enter the amount of flowers to be added to the current stock:<br>" +
                "<input type='text' name='amountToBeAdded' value='" + amountToBeAdded + "' required>" + "<br>" +
                "<input type='submit' value='Add'>" + "<br>" +
                "</form>";
    }
}
