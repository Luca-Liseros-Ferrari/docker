package com.midoriPol;
import com.midoriPol.dao.BaseDao;
import com.midoriPol.dao.ProductDao;
import com.midoriPol.model.Product;

public class MainApplication {
    public static void main(String[] args) {
        BaseDao.initFactory("my-persistence-unit"); // Inizializza la factory dell'EntityManager
        ProductDao.init();
             BaseDao b = new BaseDao();
            for (Product beat : ProductDao.getListaCatalogo()) {
                b.save(beat);
            }

        BaseDao.shutdownFactory();
    }
}