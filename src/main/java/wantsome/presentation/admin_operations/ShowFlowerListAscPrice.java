package wantsome.presentation.admin_operations;

import wantsome.business.FlowerService;
import wantsome.database.dto.Flower;

import static java.util.stream.Collectors.joining;

public class ShowFlowerListAscPrice {
    static FlowerService flowerService = FlowerService.getInstance();

    public static String getTable() {
        String headerRow = "<tr><th>ID</th><th>NAME</th><th>TYPE</th><th>COLOR</th><th>PRICE/UNIT</th><th>STOCK</th></tr>";

        String tableRows = flowerService.seeFlowerListByPriceAsc().stream()
                .map(flower -> String.format("<tr> <td>%s</td> <td>%s</td> <td>%s</td> <td>%s</td><td>%s</td> <td>%s</td><td>%s</td></tr>",
                        flower.getId(), flower.getName(), flower.getFlowerType(), flower.getColor(), flower.getPricePerUnit(), flower.getAvailableStock(),
                        "<a href='/removeFlower/" + flower.getId() + "'>Remove</a>"))
                .collect(joining(""));

        return "<br><div>List of flowers:</div>" +
                "<table border=1>" + headerRow + tableRows + "</table><br>";
    }
}
