import service.MenuService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory jpahw1 = Persistence.createEntityManagerFactory("jpahw1");
        EntityManager entityManager = jpahw1.createEntityManager();
        MenuService menuService = new MenuService(entityManager);

        //task1
        menuService.insertMenu("first", 10d, 500d, true, 10);
        menuService.insertMenu("second", 30d, 300d, false, 0);
        menuService.insertMenu("third", 40d, 600d, true, 20);

        //task2
        menuService.getMenusInPriceRange(20d, 50d);//second, third

        //task3
        menuService.getDishesWithDiscount();//first, third

        //task4
        menuService.getDishesWhereWeightIsLessThan1kg("second", "third");

    }
}
