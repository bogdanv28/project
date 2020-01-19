package wantsome.business;

import wantsome.business.exceptions.BouquetNotFound;
import wantsome.business.exceptions.FlowerNotFound;
import wantsome.database.dao.BouquetEntryDao;
import wantsome.database.dto.BouquetEntry;


import java.sql.SQLException;
import java.util.List;

public class BouquetEntryService {
    private BouquetEntryDao bouquetEntryDao;

    private static BouquetEntryService instance;

    private BouquetEntryService() {
        bouquetEntryDao = new BouquetEntryDao();
    }

    public static BouquetEntryService getInstance() {
        if (instance == null) {
            instance = new BouquetEntryService();
        }
        return instance;
    }

    public void insert(BouquetEntry bouquetEntry) {
        try {
            bouquetEntryDao.insert(bouquetEntry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        try {
            bouquetEntryDao.deleteBouquetEntryByid(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<BouquetEntry> entryListByBouquetId(int bouquetId) {
        try {
            return bouquetEntryDao.entryListByBouquetId(bouquetId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BouquetNotFound();
        }
    }

    public int getLastId() {
        int res = 0;
        try {
            res = bouquetEntryDao.getLastId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public BouquetEntry findById(int id){
        try {
            return bouquetEntryDao.findByID(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FlowerNotFound();
        }
    }

    public List<BouquetEntry> entryListByBouquetId2(int bouquetId) {
        try {
            return bouquetEntryDao.entryListByBouquetId2(bouquetId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BouquetNotFound();
        }
    }

    public void deleteByBouquetId(int boqId) {
        try {
            bouquetEntryDao.deleteByBouquetId(boqId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

