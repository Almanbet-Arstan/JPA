import entity.Classwork.Like;
import entity.Classwork.Post;
import entity.Classwork.User;
import org.hibernate.Session;
import util.HibernateUtil;

public class Instagram {
    public static void main(String[] args) {
        User user1 = saveOrUpdate(User.builder()
                .login("user1")
                .password("12345678")
                .biography("java developer")
                .build());
        User user2 = saveOrUpdate(User.builder()
                .login("user2")
                .password("password")
                .biography("c# developer")
                .build());
        User user3 = saveOrUpdate(User.builder()
                .login("user3")
                .password("12345")
                .biography("python developer")
                .build());

        Post post1 = saveOrUpdate(Post.builder()
                .text("Работаю на любимой работе")
                .user(user1)
                .build());
        Post post2 = saveOrUpdate(Post.builder()
                .text("Играю в теннис")
                .user(user2)
                .build());
        Post post3 = saveOrUpdate(Post.builder()
                .text("я на ИК")
                .user(user3)
                .build());
        Like like1 = saveOrUpdate(Like.builder().user(user1).post(post3).build());
        Like like2 = saveOrUpdate(Like.builder().user(user2).post(post3).build());

        user1.setLogin("user228");
        saveOrUpdate(user1);
    }
    public static <T> T saveOrUpdate(T entity) {
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        hibernateSession.beginTransaction();
        hibernateSession.saveOrUpdate(entity);
        hibernateSession.getTransaction().commit();
        hibernateSession.close();
        return entity;
    }
}
