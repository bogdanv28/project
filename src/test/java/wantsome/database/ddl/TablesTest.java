package wantsome.database.ddl;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import wantsome.business.BouquetEntryService;
import wantsome.business.BouquetService;
import wantsome.business.FlowerService;
import wantsome.business.OrderService;
import wantsome.database.connection.DatabaseManager;
import wantsome.database.dto.*;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class TablesTest {

    FlowerService flowerService = FlowerService.getInstance();
    OrderService orderService = OrderService.getInstance();
    BouquetService bouquetService = BouquetService.getInstance();
    BouquetEntryService bouquetEntryService = BouquetEntryService.getInstance();

    @BeforeClass
    public static void createTables() {
        DatabaseManager.getConnection();

        //createTableFlower
        try {
            FlowerDdl.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //createTableOrderDdl
        try {
            OrdersDdl.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //createTableBouquetDdl
        try {
            BouquetDdl.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //createTableBouquetEntry
        try {
            BouquetEntryDdl.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void deleteTables() throws SQLException {
        BouquetEntryDdl.deleteTable();
        FlowerDdl.deleteTable();
        BouquetDdl.deleteTable();
        OrdersDdl.deleteTable();
    }

    @Test
    public void flowerTableTest() {

        flowerService.insert(new Flower("Rose", FlowerColor.RED, 8, 20));
        flowerService.insert(new Flower("Tulip", FlowerColor.GREEN, 4, 30));
        flowerService.insert(new Flower("Orchid", FlowerColor.BLUE, 12, 35));
        flowerService.insert(new Flower("Lilly", FlowerColor.WHITE, 3, 65));

        assertEquals(8, flowerService.getFlowerPrice(1));
        assertEquals(4, flowerService.seeFlowerList().size());

        flowerService.updateStock(3, 100);
        flowerService.updateStock(1, 100);
        flowerService.updatePrice(1, 9);
        assertEquals(9, flowerService.getFlowerPrice(1));
        assertEquals("BLUE_ORCHID", flowerService.findByID(3).getName());
        assertEquals(3, flowerService.findByName("BLUE_ORCHID").getId());
        flowerService.updateStock(2, 25);
        assertEquals(55, flowerService.findByID(2).getAvailableStock());
        flowerService.updatePrice(3, 14);
        assertEquals(14, flowerService.findByID(3).getPricePerUnit());
        flowerService.setNewStock(4, 80);
        assertEquals(80, flowerService.findByID(4).getAvailableStock());

        flowerService.deleteItemByID(4);
        assertEquals(3, flowerService.seeFlowerList().size());
    }

    @Test
    public void OrdersTableTest() {
        Order o1 = new Order("Bogdan", "Vlas", "12345678", "Iasi, 12A");
        orderService.insert(o1);
        Order o2 = new Order("Andrei", "Popescu", "92345679", "Iasi, 18A");
        orderService.insert(o2);

        assertEquals(o1.getClientFirstName(), orderService.findByID(1).getClientFirstName());
        assertEquals(o1.getClientLastName(), orderService.findByID(1).getClientLastName());
        assertEquals("Popescu".toUpperCase(), orderService.findByID(2).getClientLastName());
        assertEquals(1, orderService.findByName("Popescu").size());
        assertEquals(2, orderService.getLastId());
        orderService.setPrice(22);//to last id
        assertEquals(22, orderService.findByID(2).getTotalPrice());

        orderService.setPriceToSpecificOrder(35, 1);//to specific id
        assertEquals(35, orderService.findByID(1).getTotalPrice());
    }

    @Test
    public void BouquetTableTest() {
        Bouquet bouquet = new Bouquet(orderService.getLastId());
        bouquetService.insert(bouquet);
        assertEquals(1, bouquetService.getLastId());

        Bouquet bouquet2 = new Bouquet(orderService.getLastId());
        bouquetService.insert(bouquet2);
        assertEquals(2, bouquetService.getLastId());

        Bouquet bouquet3 = new Bouquet(orderService.getLastId());
        bouquetService.insert(bouquet3);

        bouquetService.deleteByID(3);
        assertEquals(2, bouquetService.seeBouquetListByOrderId(orderService.getLastId()).size());
    }

    @Test
    public void BouquetEntryTableTest() {
        BouquetEntry bouquetEntry = new BouquetEntry(2, 1, 3);
        bouquetEntryService.insert(bouquetEntry);
        assertEquals(1, bouquetEntryService.getLastId());
        for (BouquetEntry be : bouquetEntryService.entryListByBouquetId2(1)) {
            assertEquals(3, be.getQuantity());
            assertEquals(3, be.getQuantity());
        }

        BouquetEntry bouquetEntry2 = new BouquetEntry(3, 1, 2);
        bouquetEntryService.insert(bouquetEntry2);

        assertEquals(2, bouquetService.getLastId());
        assertEquals(2, bouquetEntryService.entryListByBouquetId(1).size());
        bouquetEntryService.deleteById(2);
        assertEquals(1, bouquetEntryService.entryListByBouquetId(1).size());
    }

}