package wantsome.presentation;

import spark.Request;
import spark.Response;
import wantsome.business.*;
import wantsome.database.dto.*;
import wantsome.presentation.admin_operations.ShowFLowerList;
import wantsome.presentation.client_operations.*;

import static spark.Spark.get;
import static spark.Spark.post;

public class MainFlowerClient {

    public static void main(String[] args) {
        OrderService orderService = OrderService.getInstance();
        BouquetEntryService bouquetEntryService = BouquetEntryService.getInstance();

        orderPage(orderService);
        selectFlowers(bouquetEntryService);
        completeOrder(orderService);

        get("/removeBouquet/:id", MainFlowerClient::removeBouquet);
        get("/removeEntry/:id", MainFlowerClient::removeEntryBouquet);

        System.out.println("create order -> http://localhost:4567/orderPage");
    }

    private static void orderPage(OrderService orderService) {
        BouquetService bouquetService = BouquetService.getInstance();
        get("/orderPage", (request, response) -> {
            WebClientOrder newOrder = new WebClientOrder("", "", "", "");
            return newOrder.createOrder();
        });

        post("/orderPage", (request, response) -> {
            String firstName = request.queryParams("firstName");
            String lastName = request.queryParams("lastName");
            String phoneNumber = request.queryParams("phoneNumber");
            String address = request.queryParams("address");
            WebClientOrder newOrder = new WebClientOrder("", "", "", "");
            Order order = new Order(firstName, lastName, phoneNumber, address);
            orderService.insert(order);
            order.setId(orderService.getLastId());

            createNewBouquet(orderService, bouquetService);

            response.redirect("/selectFlowers");
            return newOrder.createOrder();
        });
    }

    private static void selectFlowers(BouquetEntryService bouquetEntryService){
        OrderService orderService = OrderService.getInstance();
        FlowerService flowerService = FlowerService.getInstance();
        BouquetService bouquetService = BouquetService.getInstance();

        get("/selectFlowers", (request, response) -> {
            if (bouquetService.seeBouquetListByOrderId(orderService.getLastId()).isEmpty()){
                bouquetService.insert(new Bouquet(orderService.getLastId()));
            }
            WebClientSelectFlowers flowers = new WebClientSelectFlowers("", "");
            return ShowFLowerList.flowerTableClient() +
                    flowers.selectFlowers() +
                    WebClientShowCurrentBouquet.buildBouquetEntryTable() +
                    ShowBouquetList.buildBouquetTable()+
                    OrderCompletion.complete();
        });

        post("/selectFlowers", (request, response) -> {
            if (bouquetService.seeBouquetListByOrderId(orderService.getLastId()).isEmpty()){
                bouquetService.insert(new Bouquet(orderService.getLastId()));
            }

            WebClientSelectFlowers flowers = new WebClientSelectFlowers("", "");
            String orderID;
            try {
                orderID = request.queryParams("orderID");
                if (orderID != null) {
                    createNewBouquet(orderService, bouquetService);
                    return ShowFLowerList.flowerTableClient() +
                            flowers.selectFlowers() +
                            WebClientShowCurrentBouquet.buildBouquetEntryTable() +
                            ShowBouquetList.buildBouquetTable()+
                            OrderCompletion.complete();
                }
            } catch (NullPointerException n) {
                System.out.println("Button Add new bouquet NOT pressed, so value from form is null.");
            }
            String flower = request.queryParams("flower");
            String quantity = request.queryParams("quantity");

            if (!(CheckValidInput.isValidNumber(quantity)) || (CheckValidInput.isNegative(quantity))) {
                return InvalidParam.message() +
                        ShowFLowerList.flowerTableClient() +
                        flowers.selectFlowers() +
                        WebClientShowCurrentBouquet.buildBouquetEntryTable() +
                        ShowBouquetList.buildBouquetTable()+
                        OrderCompletion.complete();
            }

            if (Integer.parseInt(quantity) > flowerService.getFlowerStock(flowerService.findByName(flower).getId())) {
                return NotEnoughStock.message(flower, flowerService.findByName(flower).getAvailableStock()) +
                        ShowFLowerList.flowerTableClient() +
                        flowers.selectFlowers() +
                        WebClientShowCurrentBouquet.buildBouquetEntryTable() +
                        ShowBouquetList.buildBouquetTable()+
                        OrderCompletion.complete();
            }

            bouquetEntryService.insert(
                    new BouquetEntry(flowerService.findByName(flower).getId(), bouquetService.getLastId(), Integer.parseInt(quantity)));
            flowerService.updateStock(flowerService.findByName(flower).getId(), -Integer.parseInt(quantity));

            return ShowFLowerList.flowerTableClient() +
                    flowers.selectFlowers() +
                    WebClientShowCurrentBouquet.buildBouquetEntryTable() +
                    ShowBouquetList.buildBouquetTable()+
                    OrderCompletion.complete();
        });
    }

    private static Response removeBouquet(Request req, Response res) {
        BouquetService bouquetService = BouquetService.getInstance();
        BouquetEntryService bouquetEntryService = BouquetEntryService.getInstance();
        FlowerService flowerService = FlowerService.getInstance();
        String id = req.params("id");

        for (BouquetEntry be : bouquetEntryService.entryListByBouquetId(Integer.parseInt(id))) {
            int flowerId = be.getFlowerId();
            int quantity = be.getQuantity();
            flowerService.updateStock(flowerId, quantity);
        }

        bouquetEntryService.deleteByBouquetId(Integer.parseInt(id));
        bouquetService.deleteByID(Integer.parseInt(id));

        res.redirect("/selectFlowers");
        return res;
    }

    private static Response removeEntryBouquet(Request req, Response res) {
        BouquetEntryService bouquetEntryService = BouquetEntryService.getInstance();
        FlowerService flowerService = FlowerService.getInstance();
        String id = req.params("id");

        int flowerId = bouquetEntryService.findById(Integer.parseInt(id)).getFlowerId();
        int quantity = bouquetEntryService.findById(Integer.parseInt(id)).getQuantity();;

        flowerService.updateStock(flowerId, quantity);

        bouquetEntryService.deleteById(Integer.parseInt(id));

        res.redirect("/selectFlowers");
        return res;
    }

    private static void completeOrder(OrderService orderService){
        get("/completedOrder", (request, response) -> {
            WebClientOrder newOrder = new WebClientOrder("", "", "", "");
            return newOrder.completeOrder();
        });
    }

    private static void createNewBouquet(OrderService orderService, BouquetService bouquetService) {
        Bouquet bouquet = new Bouquet(orderService.getLastId());
        bouquetService.insert(bouquet);
    }

}
