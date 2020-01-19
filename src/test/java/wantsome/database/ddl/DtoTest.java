package wantsome.database.ddl;

import org.junit.Test;
import wantsome.database.dto.*;

import static org.junit.Assert.assertEquals;

public class DtoTest {

    Flower redRose = new Flower("Rose", FlowerColor.RED, 8, 50);
    Flower yellowTulip = new Flower("Tulip", FlowerColor.YELLOW, 5, 60);
    Flower greenOrchid = new Flower("Orchid", FlowerColor.GREEN, 12, 40);

    @Test
    public void testAdminCreateStock() {

        assertEquals(redRose.getAvailableStock(), 50);
        assertEquals(yellowTulip.getPricePerUnit(), 5);
        assertEquals(greenOrchid.getName(), "GREEN_ORCHID");
        assertEquals(greenOrchid.getFlowerType(), "ORCHID");
        assertEquals(greenOrchid.getColor().toString(), "GREEN");
        assertEquals(greenOrchid.getColor().toString(), "GREEN");
    }

    @Test
    public void testClientActionDto() {

        BouquetEntry bouquetEntry1 = new BouquetEntry(redRose, 5);
        BouquetEntry bouquetEntry2 = new BouquetEntry(yellowTulip, 2);
        BouquetEntry bouquetEntry3 = new BouquetEntry(greenOrchid, 2);

        Bouquet bouquet = new Bouquet();
        bouquet.addToBouquet(bouquetEntry1);
        bouquet.addToBouquet(bouquetEntry2);
        bouquet.addToBouquet(bouquetEntry3);

        Order order1 = new Order("Bogdan", "Vlas", "074000000", "Iasi, nr.55");
        order1.addBouquets(bouquet);

        assertEquals(order1.getBouquets().size(), 1);
        assertEquals(order1.getTotalPrice(), 74);
        assertEquals(bouquet.getPrice(), 74);
        assertEquals(order1.getClientFirstName(), "Bogdan");
        assertEquals(order1.getClientLastName(), "Vlas");
        assertEquals(order1.getPhoneNumber(), "074000000");
    }
}
