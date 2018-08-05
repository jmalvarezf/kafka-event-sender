package es.eci.elejandria.event.sender.beans.randomizers;

import es.eci.elejandria.event.sender.beans.CustomerBean;
import io.github.benas.randombeans.api.Randomizer;

import java.util.Random;

public class CustomerRandomizer implements Randomizer<CustomerBean> {

    private final Integer[] customerIds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    private Random random = new Random();

    @Override
    public CustomerBean getRandomValue() {
        CustomerBean result = new CustomerBean();
        result.setCustomerId(random.nextInt(11));
        switch (result.getCustomerId()) {
            case 1:
                result.setAddress("C/ Cerro negro 11");
                result.setName("Facundo Fernandez");
                result.setPhoneNumber("666666777");
                break;
            case 2:
                result.setAddress("C/ Silvano 13");
                result.setName("Antonio Marco");
                result.setPhoneNumber("666888999");
                break;
            case 3:
                result.setAddress("C/ Tomas Breton 53");
                result.setName("Feliciano Lopez");
                result.setPhoneNumber("666555444");
                break;
            case 4:
                result.setAddress("C/ Hermosilla 112");
                result.setName("Mercedes Esteban");
                result.setPhoneNumber("666777333");
                break;
            case 5:
                result.setAddress("Plaza Conde de Casal 4");
                result.setName("Ausencio Gutierrez");
                result.setPhoneNumber("666999888");
                break;
            case 6:
                result.setAddress("C/ Daliva 87");
                result.setName("Mauricio Colmena");
                result.setPhoneNumber("662111333");
                break;
            case 7:
                result.setAddress("C/ Alfonso Gomez 14");
                result.setName("Catalina Jarrones");
                result.setPhoneNumber("654789456");
                break;
            case 8:
                result.setAddress("C/ alcala 123");
                result.setName("Beatriz Perez");
                result.setPhoneNumber("679856927");
                break;
            case 9:
                result.setAddress("C/ Andres mellado 56");
                result.setName("Curro Hernandez");
                result.setPhoneNumber("675389097");
                break;
            case 10:
                result.setAddress("C/ Tomas Gutierrez 98");
                result.setName("Cristobal Alonso");
                result.setPhoneNumber("682799325");
                break;
            default:
                result.setAddress("C/ Santos Inocentes 76");
                result.setName("Inocencio Arias");
                result.setPhoneNumber("624689087");
                break;
        }

        return result;
    }
}
