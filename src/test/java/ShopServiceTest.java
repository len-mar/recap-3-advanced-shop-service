import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")));
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }

    @Test
    void getOrderByStatusTest_whenMatchingOrdersExist_expectPopulatedList() {
        // todo: manually add new order with different status?
        //GIVEN a shop service and order repo with one order (default status PROCESSING)
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        shopService.addOrder(productsIds);

        //WHEN the orders are retrieved by order status PROCESSING
        //THEN there should only be one order received
       assertEquals(shopService.getOrderByStatus(OrderStatus.PROCESSING).size(),1);
    }

    @Test
    void getOrderByStatusTest_whenNoMatchingOrdersExist_expectEmptyList() {
        //GIVEN a shop service and order repo with one order (default status PROCESSING)
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        shopService.addOrder(productsIds);

        //WHEN the orders are retrieved by order status COMPLETED
        //THEN there should be zero orders retrieved
        assertEquals(shopService.getOrderByStatus(OrderStatus.COMPLETED).size(),0);
    }
}
