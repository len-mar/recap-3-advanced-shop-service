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
        //THEN
        assertThrows(ProductNotFoundException.class, () -> shopService.addOrder(productsIds));
    }

    @Test
    void getOrdersByStatusTest_whenMatchingOrdersExist_expectPopulatedList() {
        // todo: manually add new order with different status?
        //GIVEN a shop service and order repo with one order (default status PROCESSING)
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        shopService.addOrder(productsIds);

        //WHEN the orders are retrieved by order status PROCESSING
        //THEN there should only be one order received
       assertEquals(shopService.getOrdersByStatus(OrderStatus.PROCESSING).size(),1);
    }

    @Test
    void getOrdersByStatusTest_whenNoMatchingOrdersExist_expectEmptyList() {
        //GIVEN a shop service and order repo with one order (default status PROCESSING)
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");
        shopService.addOrder(productsIds);

        //WHEN the orders are retrieved by order status COMPLETED
        //THEN there should be zero orders retrieved
        assertEquals(shopService.getOrdersByStatus(OrderStatus.COMPLETED).size(),0);
    }

    @Test
    void getOldestOrderPerStatus_whenSeveralOrdersExistForProcessing_expectOldestToBeReturned() {
        //GIVEN a shop service and order repo with two orders (default status PROCESSING)
        ShopService shopService = new ShopService();
        shopService.addOrder(List.of("1"));
        shopService.addOrder(List.of("5"));

        //WHEN the orders are retrieved by age and order status
        //THEN the map should be empty except for the oldest order in PROCESSING (which is "1")
        assertEquals(shopService.getOldestOrdersByStatus().size(),3);
        assertEquals(shopService.getOldestOrdersByStatus().get(OrderStatus.PROCESSING).products().get(0).id(),"1");
    }

    // todo: tests for no orders at all
    // todo: tests for several orders per status

}
