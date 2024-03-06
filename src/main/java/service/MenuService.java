package service;

import model.Menu;
import model.MenuWeightResultDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MenuService {

    private final EntityManager entityManager;

    public MenuService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void insertMenu(String name, Double price, Double weight, boolean discount, Integer percentDiscount) {
        Menu menu = new Menu(name, price, weight, discount, percentDiscount);
        runTransaction(em -> em.persist(menu));
    }

    public List<Menu> getMenusInPriceRange(Double from, Double to) {
        if (from < 0 || to < 0 || to < from) {
            throw new IllegalArgumentException("incorrect args");
        }

        TypedQuery<Menu> query = entityManager.createQuery("select m from Menu m where m.price > :from and m.price < :to", Menu.class);
        query.setParameter("from", from);
        query.setParameter("to", to);
        return query.getResultList();

    }

    public List<Menu> getDishesWithDiscount() {
        TypedQuery<Menu> query = entityManager.createQuery("select m from Menu m where m.discount is true", Menu.class);
        return query.getResultList();
    }

    public MenuWeightResultDto getDishesWhereWeightIsLessThan1kg(String... names) {
        TypedQuery<String> queryNames = entityManager.createQuery("select m.name from Menu m group by m.name", String.class);
        List<String> namesFromDb = queryNames.getResultList();
        namesFromDb.forEach(System.out::println);
        Arrays.stream(names)
                .filter(name -> !namesFromDb.contains(name))
                .findAny().ifPresent(s -> {throw new RuntimeException("Incorrect argument:" + s);});

        List<Menu> list = Arrays.stream(names).map(name -> {
            TypedQuery<Menu> query = entityManager.createQuery("select m from Menu m where m.name = :name", Menu.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        }).collect(Collectors.toList());

        double sum = list.stream().mapToDouble(Menu::getWeight).sum();
        return new MenuWeightResultDto(list, sum < 1000, sum);
    }


    public void runTransaction(Consumer<EntityManager> consumer) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            consumer.accept(entityManager);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e.getCause());
        }
    }


}
