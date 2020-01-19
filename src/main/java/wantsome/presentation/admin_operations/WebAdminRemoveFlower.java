package wantsome.presentation.admin_operations;

public class WebAdminRemoveFlower {
    private String id;

    public WebAdminRemoveFlower(String id) {
        this.id = id;
    }

    public String removeFlower() {
        return "Delete flower with ID: " +
                "<form method='post'>" +
                "<div style='background-color:#34ebcf; display:inline'>" +
                "  Enter the id of flower to be deleted:<br>" +
                "<input type='text' name='id' value='" + id + "' required>" + "<br>" +
                "<input type='submit' value='Remove'>" + "<br>" +
                "</form>";
    }
}
