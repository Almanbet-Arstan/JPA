package entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "employee_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EmployeeAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "house_number", nullable = false)
    private Integer houseNumber;

    @Column(name = "flat_number")
    private Integer flatNumber;
//    @OneToOne
//    @JoinColumn(name = "employee_id")
//    private Employee employee;
}
