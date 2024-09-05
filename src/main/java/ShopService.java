import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();


    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product mit der Id: " + productId + "existiert nicht und konnte nicht bestellt werden!"));
            products.add(productToOrder);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrderByStatus(OrderStatus orderStatus){
        return orderRepo.getOrders().stream()
                .filter(order -> order.orderStatus().equals(orderStatus)).toList();
    }
}
