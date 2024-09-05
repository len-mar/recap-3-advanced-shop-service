import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ShopService {
    @NonNull private ProductRepo productRepo;
    @NonNull private OrderRepo orderRepo;
    @NonNull private UUID uuid;

    public ShopService(){
        this.productRepo = new ProductRepo();
        this.orderRepo = new OrderMapRepo();
        this.uuid = IdService.generateID();
    }

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
