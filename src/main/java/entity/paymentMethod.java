package entity;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "paymentMethod")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class paymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 10)
    private String method;
}
