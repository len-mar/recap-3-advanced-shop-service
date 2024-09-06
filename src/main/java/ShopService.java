import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class ShopService {
    @NonNull
    private ProductRepo productRepo;
    @NonNull
    private OrderRepo orderRepo;
    @NonNull
    private UUID uuid;

    public ShopService() {
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

    public Order addOrder(String orderId, List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product mit der Id: " + productId + "existiert nicht und konnte nicht bestellt werden!"));
            products.add(productToOrder);
        }

        Order newOrder = new Order(orderId, products);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
        return orderRepo.getOrders().stream()
                .filter(order -> order.orderStatus().equals(orderStatus)).toList();
    }

    public HashMap<OrderStatus, Order> getOldestOrdersByStatus() {
        HashMap<OrderStatus, Order> temp = new HashMap<>();

        Optional<Order> processing = getOrdersByStatus(OrderStatus.PROCESSING).stream()
                .min(Comparator.comparing(Order::creationTimeUTC));

        Optional<Order> in_delivery = getOrdersByStatus(OrderStatus.IN_DELIVERY).stream()
                .min(Comparator.comparing(Order::creationTimeUTC));

        Optional<Order> completed = getOrdersByStatus(OrderStatus.COMPLETED).stream()
                .min(Comparator.comparing(Order::creationTimeUTC));

        // todo: not the greatest solution to explicitly return null, but as first implementation, it works
        temp.put(OrderStatus.PROCESSING, processing.orElse(null));
        temp.put(OrderStatus.IN_DELIVERY, in_delivery.orElse(null));
        temp.put(OrderStatus.COMPLETED, completed.orElse(null));

        return temp;
    }

    // fixme: with every status change, the timestamp of "creation" is also changed
    public void setStatus(String orderID, OrderStatus newStatus) {
        orderRepo.addOrder(orderRepo.getOrderById(orderID).withOrderStatus(newStatus));
    }

    public void printOrders(){
        orderRepo.getOrders().forEach(System.out::println);
    }
}
