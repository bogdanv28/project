package wantsome.presentation.client_operations;

public class WebClientRemoveBouquetEntry {
    private String id;

    public WebClientRemoveBouquetEntry(String id) {
        this.id = id;
    }

    public String removeEntry() {
        return "Delete entry with ID: " +
                "<form method='post'>" +
                "<div style='background-color:#34ebcf; display:inline'>" +
                "  Enter the id of entry to be deleted:<br>" +
                "<input type='text' name='id' value='" + id + "' required>" + "<br>" +
                "<input type='submit' value='Remove'>" + "<br>" +
                "</form>";
    }
}
