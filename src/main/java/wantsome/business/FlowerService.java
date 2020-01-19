package wantsome.business;


import wantsome.business.exceptions.FlowerNotFound;
import wantsome.database.dao.FlowerDao;
import wantsome.database.dto.Flower;

import java.sql.SQLException;
import java.util.List;

public class FlowerService {
    private FlowerDao flowerDao;

    private static FlowerService instance;

    private FlowerService() {
        flowerDao = new FlowerDao();
    }

    public static FlowerService getInstance() {
        if (instance == null) {
            instance = new FlowerService();
        }
        return instance;
    }

    public void insert(Flower flower) {
        try {
            flowerDao.insert(flower);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteItemByID(Integer ID) {
        try {
            flowerDao.deleteItemByID(ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStock(Integer ID, Integer addToStock) {
        try {
            flowerDao.addToStock(ID, addToStock);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Invalid ID");
        }
    }

    public void setNewStock(Integer ID, Integer addToStock) {
        try {
            flowerDao.setNewStock(ID, addToStock);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Invalid ID");
        }
    }

    public void updatePrice(Integer ID, Integer newPrice) {
        try {
            flowerDao.updatePrice(ID, newPrice);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Flower findByID(Integer ID) {
        try {
            return flowerDao.findByID(ID);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FlowerNotFound();
        }
    }

    public Flower findByName(String name) {
        try {
            return flowerDao.findByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FlowerNotFound();
        }
    }

    public List<Flower> seeFlowerList() {
        try {
            return flowerDao.seeFlowerList();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FlowerNotFound();
        }
    }

    public List<Flower> seeFlowerListByPriceAsc() {
        try {
            return flowerDao.seeFlowerListByPriceAsc();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FlowerNotFound();
        }
    }

    public List<Flower> seeFlowerListByStockAsc() {
        try {
            return flowerDao.seeFlowerListByStockAsc();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FlowerNotFound();
        }
    }

    public int getFlowerPrice(Integer id) {
        try {
            return flowerDao.getFlowerPrice(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FlowerNotFound();
        }
    }

    public int getFlowerStock(int id) {
        try {
            return flowerDao.getFlowerStock(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FlowerNotFound();
        }
    }
}
