package wantsome.presentation.admin_operations;


import wantsome.database.dto.FlowerColor;

public class WebAdminAddFlower {
    private String type;
    private String color;
    private String pricePerUnit;
    private String availableStock;

    public WebAdminAddFlower(String type, String color, String pricePerUnit, String availableStock) {
        this.type = type;
        this.color = color;
        this.pricePerUnit = pricePerUnit;
        this.availableStock = availableStock;
    }

    public String addFlower() {
        return "Add: " +
                "<form method='post'>" +
                "<div style='background-color:#34ebcf; display:inline'>" +
                "  Enter the name of flower type:<br>" +
                "<input type='text' name='type' value='" + type + "' required>" + "<br>" +
                "  Select the color:<br>" +
                "<input type='radio' name='color' value='BLUE' > BLUE </input>" +
                "  <input type='radio' name='color' value='GREEN' checked > GREEN </input>" +
                "  <input type='radio' name='color' value='RED' > RED </input>" +
                "  <input type='radio' name='color' value='PINK' > PINK </input>" +
                "  <input type='radio' name='color' value='ORANGE' > ORANGE </input>" +
                "  <input type='radio' name='color' value='YELLOW' > YELLOW </input> <br>  " +
                "  Set price per unit:<br>" +
                "<input type='text' name='pricePerUnit' value='" + pricePerUnit + "' required>" + "<br>" +
                "  Set the available stock:<br>" +
                "<input type='text' name='availableStock' value='" + availableStock + "' required>" + "<br>" +
                "<input type='submit' value='Add'>" + "<br>" +
                "</form>";
    }

}
