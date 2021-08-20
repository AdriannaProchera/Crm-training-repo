package pl.sda.crm.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pl.sda.crm.entity.*;

import java.io.IOException;
import java.util.Properties;

/*
statyczny konstruktor
służy głównie do inicjalizacji składowych statycznych,
nie można do niego przekazać argumentów (więc nie może być przeciążony),
brak modyfikatora dostępu,
w klasie może istnieć tylko jeden konstruktor statyczny...
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory; //potrzebne zeby z tego tworzyc sesje hibernate i tworzyc encje itd

    //kontruktor statyczny do session factory
    static {
        try{
            final var configuration = new Configuration(); //== final Configuration configuration
            configuration.setProperties(loadHibernateProperties());
            configureEntities(configuration);
            sessionFactory = configuration.buildSessionFactory();
        }catch (IOException ex){
            ex.printStackTrace();
            throw new IllegalStateException(ex.getMessage(), ex); //nieprawidlowy stan wtedy mamy wyjatek
        }
    }

    private HibernateUtil(){//tylko po to aby nikt nie robil obiektow z tej klasy
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    private static void configureEntities(Configuration configuration){
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Person.class);
        configuration.addAnnotatedClass(Company.class);
        configuration.addAnnotatedClass(Address.class);
        configuration.addAnnotatedClass(PremiumStatus.class);
        }

    private static Properties loadHibernateProperties() throws IOException {
        final var properties = new Properties();//typ jest inferowany przez kompilator
        properties.load(HibernateUtil.class.getClassLoader().getResourceAsStream("hibernate.properties"));
        return properties;
    }
}
