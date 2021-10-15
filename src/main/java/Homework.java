import entity.Homework.Country;
import entity.Homework.Championship;
import entity.Homework.KindOfSport;
import entity.Homework.Team;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.Arrays;
import java.util.List;

public class Homework {
    public static void main(String[] args) {
        Country spain = saveEntity(Country.builder().name("Spain").build());
        Country italy = saveEntity(Country.builder().name("Italy").build());
        KindOfSport football = saveEntity(KindOfSport.builder().name("Football").build());
        KindOfSport basketball = saveEntity(KindOfSport.builder().name("Basketball").build());

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


        Team juventus = saveEntity(Team.builder()
                .name("Juventus")
                .logo("https://yandex.ru/images/search?rpt=simage&noreask=1&source=qa&text=Ювентус&stype=image&lr=10309&parent-reqid=1634334173791098-11050246359203937384-vla1-3092-vla-l7-balancer-8080-BAL-3088")
                .webSite("https://www.juventus.com/en/")
                .kindOfSport(football)
                .country(italy)
                .build());

        Team milan = saveEntity(Team.builder()
                .name("Milan")
                .logo("https://yandex.ru/images/search?rpt=simage&noreask=1&source=qa&text=Милан+футбольный+клуб&stype=image&lr=10309&parent-reqid=1634334245469364-9610475824546517398-vla1-3092-vla-l7-balancer-8080-BAL-4577")
                .webSite("https://www.acmilan.com/en")
                .kindOfSport(football)
                .country(italy)
                .build());

        Championship serieA = saveEntity(Championship.builder()
                .country(italy)
                .name("Serie A")
                .kindOfSport(football)
                .build());
        Team inter = saveEntity(Team.builder()
                .name("Inter")
                .logo("inter-logo")
                .webSite("https://www.inter.com/en")
                .kindOfSport(basketball)
                .country(italy)
                .build());
        Team lazio = saveEntity(Team.builder()
                .name("Lazio")
                .logo("lazio-logo")
                .webSite("https://www.lazio.com/en")
                .kindOfSport(basketball)
                .country(italy)
                .build());

        Championship ItalyLeague = saveEntity(Championship.builder()
                .country(italy)
                .name("Lega Basket")
                .kindOfSport(basketball)
                .build());

//        Championship championship = getChampionshipByName("lA-liGa");
//        Championship championship = getChampionshipByName("sErIE a");
//        System.out.println(championship);

//        List<Team> teams = getAllItalianTeams();
//        System.out.println(teams);
        List<Object[]> teams = getAllTeamNameAndWebsite();
        getAllTeamNameAndWebsite().forEach(i -> System.out.println(Arrays.toString(i)));
    }

    public static <T> T saveEntity(T entity){
        try(Session hibernateSession = HibernateUtil.getSessionFactory().openSession()){
            hibernateSession.beginTransaction();
            hibernateSession.saveOrUpdate(entity);
            hibernateSession.getTransaction().commit();
            hibernateSession.close();
            return entity;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Championship getChampionshipByName(String championshipName){
        try(Session hibernateSession = HibernateUtil.getSessionFactory().openSession()){
            Championship championship = hibernateSession.createQuery("from Championship c where upper(c.name) = upper(:championshipName)", Championship.class)
                    .setParameter("championshipName", championshipName).uniqueResult();
            hibernateSession.close();
            return championship;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static List<Team> getAllItalianTeams(){
        try(Session hibernateSession = HibernateUtil.getSessionFactory().openSession()){
            List<Team> teams = hibernateSession.createQuery("from Team where country.name = 'Italy' order by kindOfSport.id", Team.class).list();
            hibernateSession.close();
            return teams;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static List<Object[]> getAllTeamNameAndWebsite() {
        try(Session hibernateSession = HibernateUtil.getSessionFactory().openSession()){
            List<Object[]> teams = hibernateSession.createQuery(
                    "select t.name, t.webSite from Team t order by t.id", Object[].class).setMaxResults(5).list();
            hibernateSession.close();
            return teams;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
