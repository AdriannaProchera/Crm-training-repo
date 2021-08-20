package pl.sda.crm.util;

import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HibernateUtilTest {
    //sprawdzamy czy jest polaczenie do bazy danych
    @Test
    void testConnection(){
        //testujemy polaczenie sie z baza h2
        //given ->przygotowanie danych do testu
        final var sessionFactory = HibernateUtil.getSessionFactory();
        final var session = sessionFactory.openSession();

        //when -> wykonanie zapytania proba
        //tworzy sie kolumna x (jedyna kolumna w wynikach) z wartoscia 1
        //select polecenie projekci danych, wybiera kolumny z ktorych ma wyswietlic dane,
        final NativeQuery<Object> query = session.createSQLQuery("SELECT 1");//zapytaniez ktorego trzeba wyciagnac rezultat
        final List<Object> result = query.getResultList();//select 1 wyszukuje i uzupelnia 1 z automatu zwroci 1

        //then -> oczekiwane rezultaty w tescie
        assertEquals(1, result.get(0));
    }

}