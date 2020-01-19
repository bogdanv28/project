package wantsome.presentation;

import spark.Request;
import spark.Response;
import wantsome.business.FlowerService;
import wantsome.business.OrderService;
import wantsome.database.ddl.*;
import wantsome.database.dto.Flower;
import wantsome.database.dto.FlowerColor;
import wantsome.presentation.admin_operations.*;
import wantsome.presentation.client_operations.CheckValidInput;
import wantsome.presentation.client_operations.InvalidParam;
import wantsome.presentation.client_operations.WebClientOrder;

import java.sql.SQLException;

import static spark.Spark.*;

public class MainFlowerAdministrator {
    public static void main(String[] args) {
        FlowerService flowerService = FlowerService.getInstance();
        OrderService orderService = OrderService.getInstance();
        createMissingTables();

        //adding flower
        addingFlower(flowerService);
        //adding to stock
        addingToStock(flowerService);
        //setting new stock
        setNewStock(flowerService);
        //set new price
        setNewPrice(flowerService);
        //get the list of flowers
        getFlowerList(flowerService);
        //sort flower list by price asc
        getFlowerListByPriceAsc(flowerService);
        //sort flower list by stock asc
        getFlowerListByStockAsc(flowerService);
        //find client
        findClientByName(orderService);
        //removing flower by id
        get("/removeFlower/:id", MainFlowerAdministrator::removeFlower);

        checkPassword(flowerService);

        System.out.println("to first page -> http://localhost:4567/checkPassword");

    }

    private static void mainPage(FlowerService flowerService) {
        get("/mainAdminPage", (request, response) -> {
            return "<p style=\"font-size:28px\">Administrator page" +
                    "<p style=\"font-size:25px\"><a href='http://localhost:4567/addFlower'>Add flower</a><br>" +
                    "<p style=\"font-size:25px\"><a href='http://localhost:4567/addToStock'>Add flowers to stock</a><br>" +
                    "<p style=\"font-size:25px\"><a href='http://localhost:4567/setNewStock'>Set new stock of flowers</a><br>" +
                    "<p style=\"font-size:25px\"><a href='http://localhost:4567/setNewPrice'>Set new price flowers</a><br>" +
                    "<p style=\"font-size:25px\"><a href='http://localhost:4567/getFlowerList'>Get flower list</a>" +
                    "<p style=\"font-size:25px\"><a href='http://localhost:4567/findClient'>Find client</a>";
        });
    }

    private static void checkPassword(FlowerService flowerService) {
        get("/checkPassword", (request, response) -> {
            CheckPassword pass = new CheckPassword("");
            return pass.check();
        });

        post("/checkPassword", (request, response) -> {
            String passwordInput = request.queryParams("password");
            CheckPassword pass = new CheckPassword("");
            if (passwordInput.equals("1234")) {
                mainPage(flowerService);

                response.redirect("/mainAdminPage");
                return pass.check();
            } else {
                return IncorrectPassword.message() +
                        pass.check();
            }
        });
    }

    private static void addingFlower(FlowerService flowerService) {
        get("/addFlower", (request, response) -> {
            WebAdminAddFlower addFlower = new WebAdminAddFlower("", "", "", "");
            return addFlower.addFlower() +
                    ShowFLowerList.flowerTableAdmin() +
                    BackToMainPage.back() ;
        });

        post("/addFlower", (request, response) -> {
            String type = request.queryParams("type");
            String color = request.queryParams("color");
            String pricePerUnit = request.queryParams("pricePerUnit");
            String availableStock = request.queryParams("availableStock");

            WebAdminAddFlower addFlower = new WebAdminAddFlower("", "", "", "");

            if ((!(CheckValidInput.isValidNumber(pricePerUnit))) || CheckValidInput.isNegative(pricePerUnit) ||
                    (!(CheckValidInput.isValidNumber(availableStock))) || CheckValidInput.isNegative(availableStock)) {
                return InvalidParam.message() +
                        addFlower.addFlower() +
                        ShowFLowerList.flowerTableAdmin() +
                        BackToMainPage.back();
            }

            for (Flower f : flowerService.seeFlowerList()) {
                if ((color.toUpperCase() + "_" + type.toUpperCase()).equals(f.getName())) {
                    return "<b>This flower already exists!</b>" +
                            addFlower.addFlower() +
                            ShowFLowerList.flowerTableAdmin() +
                            BackToMainPage.back();
                }
            }

            flowerService.insert(new Flower(type, getColor(color), Integer.parseInt(pricePerUnit), Integer.parseInt(availableStock)));
            return "Added " + getColor(color) + "_" + type + "." +
                    addFlower.addFlower() +
                    ShowFLowerList.flowerTableAdmin() +
                    BackToMainPage.back();
        });
    }

    private static void addingToStock(FlowerService flowerService) {
        get("/addToStock", (request, response) -> {
            WebAdminAddToStock addToStock = new WebAdminAddToStock("", "");
            return addToStock.addToStock() +
                    ShowFLowerList.flowerTableAdmin() +
                    BackToMainPage.back();
        });

        post("/addToStock", (request, response) -> {
            String id = request.queryParams("id");
            String amountToBeAdded = request.queryParams("amountToBeAdded");
            WebAdminAddToStock addToStock = new WebAdminAddToStock("", "");

            if ((!(CheckValidInput.isValidNumber(id))) || CheckValidInput.isNegative(id) ||
                    !(CheckValidInput.isValidNumber(amountToBeAdded)) || CheckValidInput.isNegative(amountToBeAdded)) {
                return InvalidParam.message() +
                        addToStock.addToStock() +
                        ShowFLowerList.flowerTableAdmin() +
                        BackToMainPage.back();
            }

            flowerService.updateStock(Integer.parseInt(id), Integer.parseInt(amountToBeAdded));
            return "Updated! " +
                    addToStock.addToStock() +
                    ShowFLowerList.flowerTableAdmin() +
                    BackToMainPage.back();
        });
    }

    private static void setNewStock(FlowerService flowerService) {
        get("/setNewStock", (request, response) -> {
            WebAdminSetNewStock newStock = new WebAdminSetNewStock("", "");
            return newStock.newStock() +
                    ShowFLowerList.flowerTableAdmin() +
                    BackToMainPage.back();
        });

        post("/setNewStock", (request, response) -> {
            String id = request.queryParams("id");
            String newAmount = request.queryParams("newAmount");

            WebAdminSetNewStock newStock = new WebAdminSetNewStock("", "");


            if ((!(CheckValidInput.isValidNumber(id))) || CheckValidInput.isNegative(id) ||
                    (!(CheckValidInput.isValidNumber(newAmount))) || CheckValidInput.isNegative(newAmount)) {
                return InvalidParam.message() +
                        newStock.newStock() +
                        ShowFLowerList.flowerTableAdmin() +
                        BackToMainPage.back();
            }

            flowerService.setNewStock(Integer.parseInt(id), Integer.parseInt(newAmount));
            return "Updated! " +
                    newStock.newStock() +
                    ShowFLowerList.flowerTableAdmin() +
                    BackToMainPage.back();
        });
    }

    private static void setNewPrice(FlowerService flowerService) {
        get("/setNewPrice", (request, response) -> {
            WebAdminSetNewPrice price = new WebAdminSetNewPrice("", "");
            return  price.newPrice() +
                    ShowFLowerList.flowerTableAdmin() +
                    BackToMainPage.back();
        });

        post("/setNewPrice", (request, response) -> {
            String id = request.queryParams("id");
            String newAmount = request.queryParams("newPrice");
            WebAdminSetNewPrice price = new WebAdminSetNewPrice("", "");

            if ((!(CheckValidInput.isValidNumber(id))) || CheckValidInput.isNegative(id) ||
                    !(CheckValidInput.isValidNumber(newAmount)) || CheckValidInput.isNegative(newAmount)) {
                return  InvalidParam.message() +
                        price.newPrice() +
                        ShowFLowerList.flowerTableAdmin() +
                        BackToMainPage.back();
            }

            flowerService.updatePrice(Integer.parseInt(id), Integer.parseInt(newAmount));
            return "Updated! " +
                    price.newPrice() +
                    ShowFLowerList.flowerTableAdmin() +
                    BackToMainPage.back();
        });
    }

    private static Response removeFlower(Request req, Response res) {
        FlowerService flowerService = FlowerService.getInstance();
        String id = req.params("id");

        flowerService.deleteItemByID(Integer.parseInt(id));

        res.redirect("/addFlower");
        return res;
    }

    private static void getFlowerList(FlowerService flowerService) {
        get("/getFlowerList", (request, response) -> {
            return ShowFLowerList.flowerTableAdmin() +
                    LinkToSortedFLowerByPrice.showSortedByPrice() +
                    LinkToSortedFlowerByStock.showSortedByStock() +
                    BackToMainPage.back();
        });
    }

    private static void getFlowerListByPriceAsc(FlowerService flowerService) {
        get("/getFlowerPriceAsc", (request, response) -> {
            return ShowFlowerListAscPrice.getTable() +
                    LinkToSortedFlowerByStock.showSortedByStock() +
                    BackToMainPage.back();
        });
    }

    private static void getFlowerListByStockAsc(FlowerService flowerService) {
        get("/getFlowerStockAsc", (request, response) -> {
            return ShowFLowerListAscStock.getTable() +
                    LinkToSortedFLowerByPrice.showSortedByPrice() +
                    BackToMainPage.back();
        });
    }

    private static void findClientByName(OrderService orderServiceService) {
        get("/findClient", (request, response) -> {
            FindClient findClient = new FindClient("");
            return findClient.find();
        });

        post("/findClient", (request, response) -> {
            String name = request.queryParams("name");

            FindClient findClient = new FindClient("");

            return findClient.showList(name.toUpperCase().trim()) +
                    BackToMainPage.back();
        });
    }

    private static FlowerColor getColor(String color) {
        color = color.toUpperCase();
        if (color.equals(FlowerColor.BLUE.toString())) {
            return FlowerColor.BLUE;
        }
        if (color.equals(FlowerColor.RED.toString())) {
            return FlowerColor.RED;
        }
        if (color.equals(FlowerColor.YELLOW.toString())) {
            return FlowerColor.YELLOW;
        }
        if (color.equals(FlowerColor.PINK.toString())) {
            return FlowerColor.PINK;
        }
        if (color.equals(FlowerColor.ORANGE.toString())) {
            return FlowerColor.ORANGE;
        }
        return FlowerColor.GREEN;
    }

    private static void createMissingTables() {
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

}
