package wantsome.presentation.client_operations;

import wantsome.business.FlowerService;
import wantsome.database.dto.Flower;

public class WebClientSelectFlowers {
    private String flower;
    private String quantity;

    public WebClientSelectFlowers(String flower, String quantity) {
        this.flower = flower;
        this.quantity = quantity;
    }

    public String selectFlowers() {
        return "Select flowers you want in your bouquet: " +
                "<form method='post'>" +
                "<div style='background-color:#34ebcf; display:inline'>" +
                "  Select flower type:<br>" +
                "<select name='flower'>" +
                selectFlowerType() +
                "</select>" + "<br>" +
                "  Select quantity:<br>" +
                "<input type='text' name='quantity' value='" + quantity + "' required>" + "<br>" +
                "<input type='submit' value='Add to Bouquet'>" + "<br>" +
                "</form>";
    }

    private String selectFlowerType() {
        FlowerService flowerService = FlowerService.getInstance();
        String line = "";
        for (Flower fl : flowerService.seeFlowerList()) {
            line += "<option value='" + fl.getName() + "'" + ">" + fl.getName() + "</option>";
        }
        return line;
    }


}
