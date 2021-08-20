package pl.sda.crm.service;

import org.junit.jupiter.api.Test;
import pl.sda.crm.exception.CustomerAlreadyExistsException;
import pl.sda.crm.util.HibernateUtil;

import static org.junit.jupiter.api.Assertions.*;

class PersonCustomerRegistrationTest {

    private final PersonCustomerRegistration registration =
            new PersonCustomerRegistration(HibernateUtil.getSessionFactory());
    @Test
    void schouldRegisterPersonCustomer(){
        //given
        final var form = new RegisterPersonForm("Jan", "Kowalski", "12345678910");

        //when
        final var registeredCustomerId = registration.registerPerson(form);

        //then
        assertNotNull(registeredCustomerId.getId());
    }

    @Test
    void shouldNotRegisterPersonIfLastNameAndPeselAlreadyExists(){
        //given
        final var form = new RegisterPersonForm("Anna", "Nowak", "01987654321");
        registration.registerPerson(form);

        //when & then
        assertThrows(CustomerAlreadyExistsException.class, () -> registration.registerPerson(form));

    }

}