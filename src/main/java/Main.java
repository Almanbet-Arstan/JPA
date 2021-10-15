//import entity.Country;
//import entity.Department;
//import entity.Employee;
//import org.hibernate.Session;
//import util.HibernateUtil;
//
//import javax.persistence.Query;
//import java.util.List;
//import java.util.Locale;
//
//public class Main {
//    public static void main(String[] args) {
//        Country country = saveEntity(Country.builder()
//                .name("KG")
//                .build());
//        Country country2 = saveEntity(Country.builder()
//                .name("RU")
//                .build());
//
//        Department javaDepartment = saveEntity(Department.builder()
//                .name("Java")
//                .build());
//        Department frontendDepartment = saveEntity(Department.builder()
//                .name("Frontend")
//                .build());
//        Department mobileDepartment = saveEntity(Department.builder()
//                .name("Mobile")
//                .build());
//
//        Employee employee = saveEntity(Employee.builder()
//                .fullName("Иван Иванов")
//                .age(18)
//                .salary(200)
//                .country(country)
//                .department(javaDepartment)
//                .build());
//        Employee employee2 = saveEntity(Employee.builder()
//                .fullName("Максат Турдуев")
//                .age(18)
//                .salary(400)
//                .country(country2)
//                .department(frontendDepartment)
//                .build());
//        Employee employee3 = saveEntity(Employee.builder()
//                .fullName("Амир Аманкулов")
//                .age(18)
//                .salary(200)
//                .country(country)
//                .department(mobileDepartment)
//                .build());
//        Employee employee4 = saveEntity(Employee.builder()
//                .fullName("Максат Турдуев")
//                .age(28)
//                .salary(400)
//                .country(country2)
//                .department(javaDepartment)
//                .build());
//
////        List<Employee> employeeList = getAllBySearchStringAndAge(18, "аН");
////        List<Employee> employeeList = getAllEmployeesByCountryNameAndDepartmentName("Kg", "jAVa");
////        List<Department> departments = getAllDepartmentsFromCountry("ru");
//        List<Employee> employeeList = getTopThreeSalaryEmployees();
//        System.out.println(employeeList);
//    }
//
//    public static List<Employee> getTopThreeSalaryEmployees(){
//        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
//        List<Employee> employees = hibernateSession.createQuery("from Employee e order by e.salary desc", Employee.class)
//                .setMaxResults(3).list();
//        return employees;
//    }
//
//    public static List<Department> getAllDepartmentsFromCountry(String countryName){
//        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
//        List<Department> departments = hibernateSession.createQuery("select e.department from Employee e where upper(e.country.name) = upper(:countryName)", Department.class)
//                .setParameter("countryName", countryName).list();
//        return departments;
//    }
//
//    public static List<Employee> getAllEmployeesByCountryNameAndDepartmentName(String countryName, String departmentName){
//        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
//        List<Employee> employees = hibernateSession.createQuery("from Employee e where upper(e.country.name) = upper(:countryName) and upper(e.department.name) = upper(:departmentName)", Employee.class)
//                .setParameter("countryName", countryName)
//                .setParameter("departmentName", departmentName).list();
//        return employees;
//    }
//
//    public static List<Employee> getAllBySearchStringAndAge(Integer age, String searchString){
//        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
//        List<Employee> employees = hibernateSession.createQuery("from Employee where age >= :age and upper(fullName) like upper(:searchString)", Employee.class)
//                .setParameter("age", age)
//                .setParameter("searchString", "%" + searchString + "%").list();
//        return employees;
//    }
//
//    public static List<Employee> getEmployeesByName(String searchFullName) {
//        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
//        List<Employee> employees = hibernateSession.createQuery("from Employee where fullName = :fullNameParameter", Employee.class)
//                .setParameter("fullNameParameter", searchFullName).list();
//        hibernateSession.close();
//        return employees;
//    }
//    public static <T> T saveEntity(T entity) {
//        Session session =
//                HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();
//        session.save(entity);
//        session.getTransaction().commit();
//        session.close();
//        return entity;
//    }
//
//    public static List<Employee> getAll(){
//        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
//        List<Employee> employees = hibernateSession.createQuery("from Employee", Employee.class).list();
//        hibernateSession.close();
//        System.out.println("Найдено " + employees.size() + " сотрудников");
//        return employees;
//    }
//    public static void update(Employee employee){
//        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
//        hibernateSession.beginTransaction();
//        Employee newEmployee = hibernateSession.load(Employee.class, employee.getId());
//        newEmployee.setFullName(employee.getFullName());
//        newEmployee.setAge(employee.getAge());
//        hibernateSession.getTransaction().commit();
//        hibernateSession.close();
//        System.out.println("Успешно изменено " + employee);
//    }
//    public static Employee findById(Long id){
//        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
//        Employee employee = hibernateSession.load(Employee.class, id);
//        hibernateSession.close();
//        return employee;
//    }
//    public static void delete(Long id){
//        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
//        hibernateSession.beginTransaction();
//        Employee employee = findById(id);
//        hibernateSession.delete(employee);
//        hibernateSession.getTransaction().commit();
//        hibernateSession.close();
//        System.out.println("Успешно удалено " + employee);
//    }
//    public static void deleteAll(){
//        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
//        hibernateSession.beginTransaction();
//        Query query = hibernateSession.createQuery("DELETE from Employee");
//        query.executeUpdate();
//        hibernateSession.getTransaction().commit();
//        hibernateSession.close();
//        System.out.println("Успешно удалены все записи в " + Employee.class.getName());
//    }
//}
