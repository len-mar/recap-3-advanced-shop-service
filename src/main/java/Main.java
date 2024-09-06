import java.io.*;
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

        // bonus: (added by me) write into a file "transactions.txt"
//        try {
//            BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt"));
//            writer.write(
//            "addOrder A 1 2 3\n" +
//                "addOrder B 4 1\n" +
//                "setStatus A COMPLETED\n" +
//                "printOrders");
//            writer.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        BufferedReader reader = new BufferedReader(new Reader("transactions.txt"));
        // todo: call addorder with order id and product ideas
        //     public Order addOrder(String orderId, List<String> productIds) {


    }
}
