import entity.paymentMethod;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        paymentMethod paymentMethod = create(entity.paymentMethod.builder().method("Наличный расчет").build());
    }
    public static <T> T create(T entity){
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        hibernateSession.beginTransaction();
        hibernateSession.saveOrUpdate(entity);
        hibernateSession.getTransaction().commit();
        hibernateSession.close();
        return entity;
    }

//    public static List<Employee> read(){
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
}
