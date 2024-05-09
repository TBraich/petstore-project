package petstore.common.entity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "orders")
public class Order {
  @Id private String id;

  @Field(name = "description")
  private String description;

  @Field(name = "order_time")
  private LocalDateTime orderTime;

  @Field(name = "user_id")
  private String userId;

  @Field(name = "status")
  private String status;

  @Field(name = "quantity")
  private Integer quantity;

  @Field(name = "details")
  private List<OrderDetail> details;

  @Data
  @Builder
  public static class OrderDetail {
    private String category;

    private Integer quantity;

    private List<String> petIds;
  }
}
