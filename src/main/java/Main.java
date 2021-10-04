import entity.Employee;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.Query;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Employee employee = Employee.builder().fullName("Иван Иванов").age(18).build();
        createEmployee(employee);
        Employee employee2 = Employee.builder().fullName("Максат Турдуев").age(18).build();
        createEmployee(employee2);
        Employee employee3 = Employee.builder().fullName("Амир Аманкулов").age(18).build();
        createEmployee(employee3);
        List<Employee> employees = getAll();
        employees.stream()
                .filter(x -> x.getFullName()
                .toUpperCase(Locale.ROOT)
                .contains("А"))
                .forEach(x -> System.out.println(x));
        update(Employee.builder().id(1L).fullName("Улан Ырымбеков").age(25).build());
        System.out.println(findById(2L));
        delete(2L);
        deleteAll();
    }
    public static Long createEmployee(Employee employee) {
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        hibernateSession.beginTransaction();
        hibernateSession.save(employee);
        hibernateSession.getTransaction().commit();
        hibernateSession.close();
        System.out.println("Saved: " + employee.getId() + ", " + employee.getFullName() + ", " + employee.getAge());
        return employee.getId();
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
