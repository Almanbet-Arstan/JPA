package model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CountryEmployeeInfoModel {
    private String countryName;
    private Long employeeCount;
}
