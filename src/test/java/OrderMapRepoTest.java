import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapRepoTest {

    @Test
    void getOrders() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product));
        repo.addOrder(newOrder);

        //WHEN
        List<Order> actual = repo.getOrders();

        //THEN
        List<Order> expected = new ArrayList<>();
        Product product1 = new Product("1", "Apfel");
        expected.add(new Order("1", List.of(product1)));

        assertEquals(actual.size(), expected.size());
        assertEquals(actual.get(0).orderStatus(), expected.get(0).orderStatus());
        assertEquals(actual.get(0).products(), expected.get(0).products());


    }

    @Test
    void getOrderById() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product));
        repo.addOrder(newOrder);

        //WHEN
        Order actual = repo.getOrderById("1");

        //THEN
        Product product1 = new Product("1", "Apfel");
        Order expected = new Order("1", List.of(product1));

        assertEquals(actual.products(), expected.products());
    }

    @Test
    void addOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product));

        //WHEN
        Order actual = repo.addOrder(newOrder);

        //THEN
        Product product1 = new Product("1", "Apfel");
        Order expected = new Order("1", List.of(product1));
        assertEquals(actual.products(), expected.products());
        assertEquals(repo.getOrderById("1").products(), expected.products());
        assertEquals(repo.getOrderById("1").orderStatus(), expected.orderStatus());
    }

    @Test
    void removeOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        //WHEN
        repo.removeOrder("1");

        //THEN
        assertNull(repo.getOrderById("1"));
    }
}
