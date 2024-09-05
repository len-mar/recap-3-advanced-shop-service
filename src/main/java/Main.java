import java.util.List;

public class Main {
    public static void main(String[] args) {
        // bonus: creates new shop service with two fresh repos and a random id
        ProductRepo productRepo = new ProductRepo();
        OrderMapRepo orderRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo, IdService.generateID());


        // bonus: adds one order of apfel, one of apfel & birne, one of birne, orange & gurke
        shopService.addOrder(List.of("1"));
        shopService.addOrder(List.of("1","3"));
        shopService.addOrder(List.of("3","4","5"));
    }
}
