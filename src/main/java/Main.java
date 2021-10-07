import entity.Department;
import entity.Employee;
import entity.EmployeeAddress;
import entity.Homework.Championship;
import entity.Homework.Country;
import entity.Homework.KindOfSport;
import entity.Homework.Team;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.Query;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {

        Country spain = saveEntity(Country.builder().name("Spain").build());
        KindOfSport football = saveEntity(KindOfSport.builder().name("Football").build());

        Team realMadrid = saveEntity(Team.builder()
                .name("Real-Madrid")
                .logo("https://avatars.mds.yandex.net/i?id=c3c61b6d9db8ea3fb7e0d82c2486026f-4865125-images-thumbs&n=13")
                .webSite("https://www.realmadrid.com/en")
                .kindOfSport(football)
                .country(spain)
                .build());

        Team barcelona = saveEntity(Team.builder()
                .name("Barcelona")
                .logo("https://www.vippng.com/png/full/524-5245161_fc-barcelona-new-logo-png-new-fc-barcelona.png")
                .webSite("https://www.fcbarcelona.com/en/")
                .kindOfSport(football)
                .country(spain)
                .build());

        Championship laLiga = saveEntity(Championship.builder()
                .country(spain)
                .name("La-Liga")
                .kindOfSport(football)
                .build());

        System.out.println(realMadrid);
        System.out.println(barcelona);
        System.out.println(laLiga);






//        Department javaBackendDepartment = Department.builder().name("Java Backend").build();
//        Department frontendDepartment = Department.builder().name("Frontend").build();
//        Department mobileDepartment = Department.builder().name("Mobile").build();
//        saveEntity(javaBackendDepartment);
//        saveEntity(frontendDepartment);
//        saveEntity(mobileDepartment);
//
//        EmployeeAddress employeeAddress = EmployeeAddress.builder()
//                .district("6 мкр")
//                .houseNumber(8)
//                .flatNumber(20)
//                .build();
//        EmployeeAddress employeeAddress2 = EmployeeAddress.builder()
//                .district("8 мкр")
//                .houseNumber(12)
//                .flatNumber(53)
//                .build();
//        EmployeeAddress employeeAddress3 = EmployeeAddress.builder()
//                .district("Кок-Жар")
//                .houseNumber(4)
//                .build();
//        saveEntity(employeeAddress);
//        saveEntity(employeeAddress2);
//        saveEntity(employeeAddress3);
//
//        Employee employee = Employee.builder()
//                .fullName("Иван Иванов")
//                .age(18)
//                .employeeAddress(employeeAddress)
//                .department(javaBackendDepartment)
//                .build();
//        Employee employee2 = Employee.builder()
//                .fullName("Максат Турдуев")
//                .age(18)
//                .employeeAddress(employeeAddress2)
//                .department(frontendDepartment)
//                .build();
//        Employee employee3 = Employee.builder()
//                .fullName("Амир Аманкулов")
//                .age(18)
//                .employeeAddress(employeeAddress3)
//                .department(mobileDepartment)
//                .build();
//        saveEntity(employee);
//        saveEntity(employee2);
//        saveEntity(employee3);
//        System.out.println(getAll());
    }
//    public static Long createEmployee(Employee employee) {
//        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
//        hibernateSession.beginTransaction();
//        hibernateSession.save(employee);
//        hibernateSession.getTransaction().commit();
//        hibernateSession.close();
//        System.out.println("Saved: " + employee.getId() + ", " + employee.getFullName() + ", " + employee.getAge());
//        return employee.getId();
//    }
    public static <T> T saveEntity(T entity){
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        hibernateSession.beginTransaction();
        hibernateSession.saveOrUpdate(entity);
        hibernateSession.getTransaction().commit();
        hibernateSession.close();
        return entity;
    }

    public static List<Employee> getAll(){
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        List<Employee> employees = hibernateSession.createQuery("from Employee", Employee.class).list();
        hibernateSession.close();
        System.out.println("Найдено " + employees.size() + " сотрудников");
        return employees;
    }
    public static void update(Employee employee){
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        hibernateSession.beginTransaction();
        Employee newEmployee = hibernateSession.load(Employee.class, employee.getId());
        newEmployee.setFullName(employee.getFullName());
        newEmployee.setAge(employee.getAge());
        hibernateSession.getTransaction().commit();
        hibernateSession.close();
        System.out.println("Успешно изменено " + employee);
    }
    public static Employee findById(Long id){
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Employee employee = hibernateSession.load(Employee.class, id);
        hibernateSession.close();
        return employee;
    }
    public static void delete(Long id){
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        hibernateSession.beginTransaction();
        Employee employee = findById(id);
        hibernateSession.delete(employee);
        hibernateSession.getTransaction().commit();
        hibernateSession.close();
        System.out.println("Успешно удалено " + employee);
    }
    public static void deleteAll(){
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        hibernateSession.beginTransaction();
        Query query = hibernateSession.createQuery("DELETE from Employee");
        query.executeUpdate();
        hibernateSession.getTransaction().commit();
        hibernateSession.close();
        System.out.println("Успешно удалены все записи в " + Employee.class.getName());
    }
}
