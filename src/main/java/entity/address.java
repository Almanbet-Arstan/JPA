package entity;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 20)
    private String city;

    @Column(nullable = false, length = 20)
    private String street;

    @Column(nullable = false)
    private Byte home;

    @Column(nullable = false, length = 20)
    private String apartmentOffice;

    @Column(nullable = false)
    private Byte porch;

    @Column(nullable = false)
    private Byte floor;
}
