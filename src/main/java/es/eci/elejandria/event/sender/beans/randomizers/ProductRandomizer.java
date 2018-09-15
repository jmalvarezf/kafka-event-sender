package es.eci.elejandria.event.sender.beans.randomizers;

import es.eci.elejandria.event.sender.beans.ProductBean;
import io.github.benas.randombeans.api.Randomizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ProductRandomizer implements Randomizer<ProductBean> {

    private Random random = new Random();

    private static final Integer MAX_VALUE = 100;

    private static final Integer MIN_VALUE = 10;


    private String[] products = {"AA", "BB", "CC", "DD", "EE", "FF", "GG", "HH", "II", "JJ", "KK", "LL"};

    private static final Map<String, ProductBean.Category> productCategories = new HashMap<String, ProductBean.Category>();

    static {
        productCategories.put("AA", ProductBean.Category.ELECTRONICS);
        productCategories.put("BB", ProductBean.Category.FASHION);
        productCategories.put("CC", ProductBean.Category.FOOD);
        productCategories.put("DD", ProductBean.Category.SPORTS);
        productCategories.put("EE", ProductBean.Category.ELECTRONICS);
        productCategories.put("FF", ProductBean.Category.COSMETICS);
        productCategories.put("GG", ProductBean.Category.SPORTS);
        productCategories.put("HH", ProductBean.Category.FOOD);
        productCategories.put("II", ProductBean.Category.ELECTRONICS);
        productCategories.put("JJ", ProductBean.Category.FASHION);
        productCategories.put("KK", ProductBean.Category.SPORTS);
        productCategories.put("LL", ProductBean.Category.FASHION);
    }

    @Override
    public ProductBean getRandomValue() {
        ProductBean result = new ProductBean();
        result.setReference(products[random.nextInt(11)]);
        result.setCategory(productCategories.get(result.getReference()));
        result.setQuantity(random.nextInt(20) + 1);
        result.setPrice(random.nextFloat() * (MAX_VALUE - MIN_VALUE) + MIN_VALUE);
        return result;
    }
}
