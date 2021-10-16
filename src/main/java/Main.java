import entity.Country;
import entity.Department;
import entity.Employee;
import entity.EmployeeAddress;
import model.CountryEmployeeInfoModel;
import model.EmployeeShortModel;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Country kgCountry = saveEntity(Country.builder().name("KG").build());
        Country usCountry = saveEntity(Country.builder().name("US").build());
        Department javaBackendDepartment = Department.builder().name("IT").build();
        Department frontendDepartment = Department.builder().name("frontend").build();
        Department mobileDepartment = Department.builder().name("IT").build();
        saveEntity(javaBackendDepartment);
        saveEntity(frontendDepartment);
        saveEntity(mobileDepartment);

        EmployeeAddress employeeAddress = EmployeeAddress.builder()
                .district("6 мкр")
                .houseNumber(8)
                .flatNumber(20)
                .build();
        EmployeeAddress employeeAddress2 = EmployeeAddress.builder()
                .district("8 мкр")
                .houseNumber(12)
                .flatNumber(53)
                .build();
        EmployeeAddress employeeAddress3 = EmployeeAddress.builder()
                .district("Кок-Жар")
                .houseNumber(4)
                .build();
        saveEntity(employeeAddress);
        saveEntity(employeeAddress2);
        saveEntity(employeeAddress3);

        Employee employee = Employee.builder()
                .fullName("Иван Иванов")
                .age(18)
                .employeeAddress(employeeAddress)
                .department(javaBackendDepartment)
                .country(kgCountry)
                .salary(200)
                .build();
        Employee employee2 = Employee.builder()
                .fullName("Максат Турдуев")
                .age(18)
                .employeeAddress(employeeAddress2)
                .department(frontendDepartment)
                .country(usCountry)
                .salary(600)
                .build();
        Employee employee3 = Employee.builder()
                .fullName("Амир Аманкулов")
                .age(18)
                .employeeAddress(employeeAddress3)
                .department(mobileDepartment)
                .country(kgCountry)
                .salary(300)
                .build();
        saveEntity(employee);
        saveEntity(employee2);
        saveEntity(employee3);
//        System.out.println(getAllKGEmployees());
//        System.out.println(getAllFrom18To60KGEmployees());
//        System.out.println(getAlLItOrUSEmployees());
//        System.out.println(getEmployeeShortModelsMultiSelectV2());
//        System.out.println(getEmployeeShortModelsMultiSelectV1());
        System.out.println(getCountryEmployeeInfo());
  }
    public static List<CountryEmployeeInfoModel> getCountryEmployeeInfo() {
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = hibernateSession.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        criteriaQuery.multiselect(
                employeeRoot.get("country").get("name"), criteriaBuilder.count(employeeRoot))
                .groupBy(employeeRoot.get("country").get("name"));

        Query hibernateQuery = hibernateSession.createQuery(criteriaQuery);
        List<Object[]> fieldList = hibernateQuery.getResultList();

        List<CountryEmployeeInfoModel> countryEmployeeInfoModels = new ArrayList<>();
        for(Object[] field : fieldList) {
            countryEmployeeInfoModels.add(
                    CountryEmployeeInfoModel.builder()
                            .countryName((String) field[0])
                            .employeeCount((Long) field[1])
                            .build());
        }

        hibernateSession.close();
        return countryEmployeeInfoModels;
    }

    public static List<EmployeeShortModel> getEmployeeShortModelsMultiSelectV1() {
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = hibernateSession.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        criteriaQuery.multiselect(
                employeeRoot.get("fullName"), employeeRoot.get("age"), employeeRoot.get("country").get("name"), employeeRoot.get("department").get("name")
        );

        Query hibernateQuery = hibernateSession.createQuery(criteriaQuery);
        List<Object[]> fieldList = hibernateQuery.getResultList(); //name, age, country.name

        List<EmployeeShortModel> employeeShortModels = new ArrayList<>();
        for(Object[] resultFields : fieldList) {
            employeeShortModels.add(EmployeeShortModel.builder()
                    .fullName((String) resultFields[0])
                    .age((Integer) resultFields[1])
                    .countryName((String) resultFields[2])
                    .departmentName((String) resultFields[3])
                    .build());
        }
        hibernateSession.close();
        return employeeShortModels;
    }

    public static List<EmployeeShortModel> getEmployeeShortModelsMultiSelectV2() {
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = hibernateSession.getCriteriaBuilder();
        CriteriaQuery<EmployeeShortModel> criteriaQuery = criteriaBuilder.createQuery(EmployeeShortModel.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        criteriaQuery.multiselect(
                employeeRoot.get("id"), employeeRoot.get("fullName"), employeeRoot.get("age"), employeeRoot.get("country").get("name"), employeeRoot.get("department").get("name")
        );

        Query hibernateQuery = hibernateSession.createQuery(criteriaQuery);
        List<EmployeeShortModel> employeeShortModels = hibernateQuery.getResultList(); //name, age, country.name

        hibernateSession.close();
        return employeeShortModels;
    }

    public static List<Employee> getAlLItOrUSEmployees() {
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = hibernateSession.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        criteriaQuery.select(employeeRoot).where(
                criteriaBuilder.or(
                        criteriaBuilder.equal(employeeRoot.get("department").get("name"), "IT"),
                        criteriaBuilder.equal(employeeRoot.get("country").get("name"), "US")
                )
        );
        TypedQuery<Employee> employeeTypedQuery = hibernateSession.createQuery(criteriaQuery);
        return employeeTypedQuery.getResultList();
    }

    public static List<Employee> getAllFrom18To60KGEmployees() {
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = hibernateSession.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        criteriaQuery.select(employeeRoot)
                .where(criteriaBuilder.and(
                        criteriaBuilder.between(employeeRoot.get("age"), 18, 60),
                        criteriaBuilder.equal(employeeRoot.get("country").get("name"), "KG")
                        )
                );
        TypedQuery<Employee> employeeTypedQuery = hibernateSession.createQuery(criteriaQuery);
        return employeeTypedQuery.getResultList();
    }


    public static List<Employee> getAllKGEmployees() {
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = hibernateSession.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        criteriaQuery
                .select(employeeRoot)
                .where(criteriaBuilder.equal(employeeRoot.get("country").get("name"), "KG"));

        TypedQuery<Employee> employeeTypedQuery = hibernateSession.createQuery(criteriaQuery);
        return employeeTypedQuery.getResultList();
    }
  public static <T> T saveEntity(T entity){
      Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
      hibernateSession.beginTransaction();
      hibernateSession.saveOrUpdate(entity);
      hibernateSession.getTransaction().commit();
      hibernateSession.close();
      return entity;
  }
}
