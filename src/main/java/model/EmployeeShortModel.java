package model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmployeeShortModel {
    private Long id;
    private String fullName;
    private Integer age;
    private String countryName;
    private String departmentName;
}
