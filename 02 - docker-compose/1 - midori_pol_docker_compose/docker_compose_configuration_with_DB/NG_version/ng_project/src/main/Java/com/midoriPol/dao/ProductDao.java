package com.midoriPol.dao;
import com.midoriPol.model.CategoriaProdotto;
import com.midoriPol.model.Product;
import com.midoriPol.model.Cart;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductDao {
    private static List<Product> listaCatalogo = new ArrayList<>();

    public static void init() {

            List<String> nomiProdotti = Arrays.asList(
                    "Free Space",
                    "GrooveLand",
                    "Fucking Envy",
                    "Addicted",
                    "Flavours",
                    "Suspended",
                    "Last Drink",
                    "Communications Down",
                    "Halo",
                    "Secret Door");

        List<String> pics = Arrays.asList(
                "../../assets/images/beats/Free Space.jpg",
                "../../assets/images/beats/Grooveland.jpg",
                "../../assets/images/beats/Fucking Envy.jpg",
                "../../assets/images/beats/Addicted.jpg"
        );


            //free space
            Product primoBasic = new Product(nomiProdotti.get(0), 1, 30L, CategoriaProdotto.BASIC, "prodotto basic", pics.get(0));
            Product primoPremium = new Product(nomiProdotti.get(1), 1, 40L, CategoriaProdotto.PREMIUM, "prodotto premium", pics.get(1));
            Product primoPlus = new Product(nomiProdotti.get(2), 1, 50L, CategoriaProdotto.PLUS, "prodotto plus", pics.get(2));
            Product primoExclusive = new Product(nomiProdotti.get(3), 1, 60L, CategoriaProdotto.EXCLUSIVE, "prodotto exclusive", pics.get(3));


            primoBasic.setId(1L);
            primoPremium.setId(2L);
            primoPlus.setId(3L);
            primoExclusive.setId(4L);

            listaCatalogo.add(primoBasic);
            listaCatalogo.add(primoPremium);
            listaCatalogo.add(primoPlus);
            listaCatalogo.add(primoExclusive);

            /*
            BaseDao basedao = new BaseDao();
            for (Product beat : listaCatalogo) {
                basedao.save(beat);
            }
            */

    }

    // end init
    public static List<Product> getListaCatalogo() {
        return listaCatalogo;
    }

}
