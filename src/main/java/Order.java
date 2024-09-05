import lombok.Builder;
import lombok.With;

import java.time.Instant;
import java.util.List;

// todo: write wither for status
@Builder
public record Order(
        String id,
        List<Product> products,
        @With OrderStatus orderStatus,
        @With Instant creationTimeUTC
) {
    public Order(String id, List<Product> products){
        this(id, products, OrderStatus.PROCESSING, Instant.now());
    }

}
