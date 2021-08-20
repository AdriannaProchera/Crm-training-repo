package pl.sda.crm.entity;

import com.neovisionaries.i18n.CountryCode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sda.crm.util.HibernateUtil;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    private final SessionFactory factory = HibernateUtil.getSessionFactory();
    private Session session;
    private Transaction tx;

    @BeforeEach
    void before(){
        session = factory.openSession();
        tx = session.beginTransaction();

    }

    @AfterEach
    void after(){
        tx.rollback();
        session.close();
    }

    @Test
    void shouldSavePersonInDatabase(){
        //w hibernacie save - zapisanie do cache , jak commit to zapis do bazy

        //given
        final var person = new Person("Jan", "Kowalski", new Pesel("12345678910"));

        //when proba zapisania do baz danych
        saveAndFlush(person);
        //session.save(person);//dodanie encji do cache - do bazy danych
        //zapis przez sesje , zapisuje do cache i trakuje go -> save oznacza zeby dodac obiekt do sesji hibernate
        //save ma w srodku Map<ID,ENTITY> = person.id, person(po identyfikztorze encji zapisana)

        //flush i clear tylko do TESTOW, nie uzywamy produkcyjnie
        //session.flush(); //zapisz encje w bazie danych(sprawdzi co ma w mapie i wysle obiekty w bazie danych), tu jest insert
        //w momencie wywolania flush nie jest czyszczony cache (TU JEST INSERT)
        //session.clear(); //czysci cache

        //then odczyt
        //pobierz Person po jego id
        final var readPerson = session.get(Person.class, person.getId());//jezeli obiekt jest w cachu to go bezposrednio zwraca
        //chcemy sciagnac obiekt z bazy danych, wymusic select
        assertEquals(person, readPerson);
        //tx.commit(); //powinien byc na koncu operacji z hibernatem, potwierdzenie zgodnosci danych
        //tx.rollback(); (JEST W AFTEREACH)wycofuje zmiany z bazy danych , aby 1 test nie wplynal na drugi test, aby baza danych byla pusta
        //wycofanie transakcji aby jeden test nie wplywal na reszte testow
    }
    @Test
    void shouldAddAddress(){
        //given
        final var customer = new Person("Jan", "Kowalski", new Pesel("12345678910"));
        final var address = new Address("street", "Wawa","11-300", CountryCode.PL);
        customer.addAddresses(address);

        //when
        //session.save(address); reczne zapisanie adresu
        saveAndFlush(customer);

        //then
        final var readCustomer = session.get(Customer.class, customer.getId());
        assertEquals(customer.getAddresses(), readCustomer.getAddresses());
    }

    @Test
    void shouldAddPremiumStatus(){
        //given
        final var customer = new Person("Jan", "Kowalski", new Pesel("12345678910"));
        PremiumStatus status = new PremiumStatus(LocalDate.of(2021,10,10), TypePremiumStatus.GOLD);
        customer.addPremiumStatus(new PremiumStatus(LocalDate.of(2021,10,10), TypePremiumStatus.GOLD));

        //when
        saveAndFlush(customer);

        //then
        final var readCustomer = session.get(Customer.class, customer.getId());
        assertEquals(status, readCustomer.getPremiumStatus());
    }

    @Test
    void shouldupdatePremiumStatus(){
        //given
        final var customer = new Person("Jan", "Kowalski", new Pesel("12345678910"));
        final var customer2 = new Person("Anna", "Nowak", new Pesel("98765432110"));
        customer2.addPremiumStatus(new PremiumStatus(LocalDate.now().plusMonths(6), TypePremiumStatus.GOLD));
        //when
        customer.updatePremiumStatus(TypePremiumStatus.GOLD);
        saveAndFlush(customer);
        saveAndFlush(customer2);
        //then
        assertEquals(customer2.getPremiumStatus(), customer.getPremiumStatus());
    }

    private void saveAndFlush(Person person) {
        session.save(person);
        //FLUSH TYLKO TEST I CLEAR
        session.flush();
        session.clear();
    }

}