package wantsome.presentation.client_operations;

import wantsome.business.BouquetEntryService;
import wantsome.business.BouquetService;
import wantsome.business.FlowerService;
import wantsome.database.dto.BouquetEntry;

import static java.util.stream.Collectors.joining;

public class WebClientShowCurrentBouquet {

    private static BouquetEntryService bouquetEntryService = BouquetEntryService.getInstance();
    private static BouquetService bouquetService = BouquetService.getInstance();
    private static FlowerService flowerService = FlowerService.getInstance();


    public static String buildBouquetEntryTable() {
        bouquetService.setPrice(bouquetService.getLastId(), computePriceOfBouquet());

        String headerRow = "<tr><th>Flower Name ID</th><th>Quantity</th></tr>";
        String tableRows = bouquetEntryService.entryListByBouquetId(bouquetService.getLastId()).stream()
                .map(bouquetEntry -> String.format("<tr> <td>%s</td> <td>%s</td><td>%s</td></tr>",
                        flowerService.findByID(bouquetEntry.getFlowerId()).getName(), bouquetEntry.getQuantity(),
                        "<a href='/removeEntry/" + bouquetEntry.getId() + "'>Remove this entry from bouquet</a>"))
                .collect(joining(""));

        return "<br><div>Bouquet:</div>" +
                "<table border=1>" + headerRow + tableRows + "</table><br>" +
                "<div><b><font size='3'>Total price of bouquet: " + computePriceOfBouquet() + "</font></b>";
    }

    private static int computePriceOfBouquet() {
        int bPrice = 0;

        for (BouquetEntry bouquetEntry : bouquetEntryService.entryListByBouquetId(bouquetService.getLastId())) {
            bPrice += flowerService.findByID(bouquetEntry.getFlowerId()).getPricePerUnit() * bouquetEntry.getQuantity();
        }
        return bPrice;
    }
}
