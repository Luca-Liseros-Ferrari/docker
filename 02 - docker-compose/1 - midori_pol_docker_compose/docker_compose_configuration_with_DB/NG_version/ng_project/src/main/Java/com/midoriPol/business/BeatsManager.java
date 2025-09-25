package com.midoriPol.business;
import com.midoriPol.dao.ProductDao;
import com.midoriPol.model.Product;
import com.midoriPol.dao.ProductDao;

import java.util.List;

public class BeatsManager {

    // get products
    public static List<Product> getAll () {
        return ProductDao.getListaCatalogo();
    }
    public static Product getById (Integer id) {
        for (Product p : ProductDao.getListaCatalogo()) {
            if(p.getId().equals(id))
                return p;
        }
        return null;
    }







}
