package wantsome.business;

import wantsome.business.exceptions.BouquetNotFound;
import wantsome.business.exceptions.FlowerNotFound;
import wantsome.database.dao.BouquetDao;
import wantsome.database.dto.Bouquet;


import java.sql.SQLException;
import java.util.List;

public class BouquetService {
    private BouquetDao bouquetDao;

    private static BouquetService instance;

    private BouquetService(){
        bouquetDao = new BouquetDao();
    }

    public static BouquetService getInstance(){
        if (instance == null) {
            instance = new BouquetService();
        }
        return instance;
    }
    public void insert(Bouquet bouquet){
        try {
            bouquetDao.insert(bouquet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteByID(Integer ID){
        try {
            bouquetDao.deleteBouquetsByid(ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getLastId(){
        int res = 0;
        try {
            res = bouquetDao.getLastId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void setPrice(int bouquetId,int price) {
        try {
            bouquetDao.updatePrice(bouquetId, price);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Bouquet> seeBouquetListByOrderId(int orderId) {
        try {
            return bouquetDao.seeBouquetListByOrderId(orderId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BouquetNotFound();
        }

    }
}
