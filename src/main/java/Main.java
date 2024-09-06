import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // bonus: creates new shop service with two fresh repos and a random id
        ProductRepo productRepo = new ProductRepo();
        OrderMapRepo orderRepo = new OrderMapRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo, IdService.generateID());


        // bonus: adds one order of apfel, one of apfel & birne, one of birne, orange & gurke
        //    shopService.addOrder(List.of("1"));
        //    shopService.addOrder(List.of("1", "3"));
        //    shopService.addOrder(List.of("3", "4", "5"));

        // bonus: (added by me) write into a file "transactions.txt"
        //    try {
        //        BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt"));
        //        writer.write(
        //        "addOrder A 1 2 3\n" +
        //            "addOrder B 4 1\n" +
        //            "setStatus A COMPLETED\n" +
        //            "printOrders");
        //        writer.close();
        //
        //    } catch (IOException e) {
        //        e.printStackTrace();
        //    }

        // todo: find a less messy way of parsing each line
        // bonus: reads file and parses commands
        try {
            // creates file and scanner to read file
            File f = new File("transactions.txt");
            Scanner scanner = new Scanner(f);
            // creates local variables to store command
            String command;
            // repeats while there's lines to read
            while (scanner.hasNextLine()) {
                // reads one line from file, e.g., "addOrder A 1 2 3"
                String line = scanner.nextLine();
                // finds command by looking for the first word in line
                command = Arrays.stream(line.split(" ")).findFirst().orElseThrow(IOException::new);
                switch (command) {
                    case "addOrder":
                        // finds orderId by filtering first word after command
                        String orderIDCreate = Arrays.stream(line.split(" "))
                                .filter(s -> !s.equals("addOrder"))
                                .findFirst()
                                .orElseThrow(IOException::new);
                        // finds products by filtering list after command and orderId
                        List<String> products = Arrays.stream(line.split(" "))
                                .filter(s -> !s.equals("addOrder") && !s.equals(orderIDCreate)).toList();
                        // adds order with custom id
                        shopService.addOrder(orderIDCreate, products);
                        // checks for success and prints feedback
                        if (orderRepo.getOrderById(orderIDCreate) != null)
                            System.out.println("successfully added order!");
                        else {
                            System.out.println("something went wrong while adding order");
                        }
                        break;
                    case "setStatus":
                        // finds orderId by filtering first word after command
                        String orderIDSet = Arrays.stream(line
                                        .split(" "))
                                .filter(s -> !s.equals("setStatus"))
                                .findFirst()
                                .orElseThrow(IOException::new);
                        // finds new status by filtering word after command and orderId
                        OrderStatus newStatus = OrderStatus.valueOf(Arrays
                                .stream(line.split(" "))
                                .filter(s -> !s.equals("setStatus") && !s.equals(orderIDSet))
                                .findFirst()
                                .orElseThrow(IOException::new));
                        // sets new status
                        shopService.setStatus(orderIDSet, newStatus);
                        // prints
                        System.out.println("order successfully set to " + orderRepo.getOrderById(orderIDSet).orderStatus());
                        break;
                    case "printOrders":
                        // prints all orders
                        System.out.println("all orders: ");
                        shopService.printOrders();
                        break;
                    default:
                        System.out.println("error while trying to parse file, check the format");
                }
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
