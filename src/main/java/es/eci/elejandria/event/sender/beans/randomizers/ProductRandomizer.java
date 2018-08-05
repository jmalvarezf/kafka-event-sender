package es.eci.elejandria.event.sender.beans.randomizers;

import es.eci.elejandria.event.sender.beans.ProductBean;
import io.github.benas.randombeans.api.Randomizer;

import java.util.Random;

public class ProductRandomizer implements Randomizer<ProductBean> {

    private Random random = new Random();

    private static final Integer MAX_VALUE=100;

    private static final Integer MIN_VALUE=10;


    private String[] products = {"AA", "BB", "CC", "DD", "EE", "FF", "GG", "HH", "II", "JJ", "KK", "LL"};

    @Override
    public ProductBean getRandomValue() {
        ProductBean result = new ProductBean();
        result.setReference(products[random.nextInt(11)]);
        result.setQuantity(random.nextInt(20) + 1);
        result.setPrice(random.nextFloat()* (MAX_VALUE - MIN_VALUE) + MIN_VALUE);
        return result;
    }
}
