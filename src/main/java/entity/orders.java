package entity;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 10)
    private String status;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_method", nullable = false, unique = true)
    private ordersMethod orders_method;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method", nullable = false, unique = true)
    private paymentMethod payment_method;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false, unique = true)
    private address address_id;
}
