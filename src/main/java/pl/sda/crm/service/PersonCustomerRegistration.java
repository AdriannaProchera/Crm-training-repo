package pl.sda.crm.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pl.sda.crm.entity.Person;
import pl.sda.crm.exception.CustomerAlreadyExistsException;

import static java.util.Objects.requireNonNull;

public class PersonCustomerRegistration {

    private final SessionFactory sessionFactory;

    public PersonCustomerRegistration(SessionFactory sessionFactory) {
        this.sessionFactory = requireNonNull(sessionFactory);
    }


    public RegisteredCustomerId registerPerson(RegisterPersonForm form){
        //0. otworzenie transakcji
        final var session = sessionFactory.openSession();
        final var tx = session.beginTransaction();

        //1. walidacja last name + pesel
        //HQL - hibernate query language
        //zwracanie boolean
        if(personExists(form, session)){
            throw new CustomerAlreadyExistsException("customer exists, chech data" + form);
        }

        //2. zamiana form w encje Person
        final var person = Person.from(form);

        //3. zapisanie Person do DB
        session.save(person);

        //4. commit & zwrocenie id
        tx.commit();
        session.close();
        return new RegisteredCustomerId(person.getId());
    }

    private Boolean personExists(RegisterPersonForm form, Session session) {
        return session.createQuery("select count(p) > 0 from Person p where p.lastName = ?1 and p.pesel.value = ?2", Boolean.class)
                .setParameter(1, form.getLastName())
                .setParameter(2, form.getPesel())
                .getSingleResult();
    }

}
