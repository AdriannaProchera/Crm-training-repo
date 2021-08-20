package pl.sda.crm.service;

import pl.sda.crm.util.HibernateUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pl.sda.crm.exception.CustomerAlreadyExistsException;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CompanyCustomerRegistrationTest {

    private final CompanyCustomerRegistration registration =
            new CompanyCustomerRegistration(HibernateUtil.getSessionFactory());


    @Test
    void shouldRegisterCompanyCustomer(){
        //given
        final var form = new RegisterCompanyForm("Gwiazdy", "1234567891234");
        //then
        final var registeredCustomerId = registration.registerCustomer(form);
        //when
        assertNotNull(registeredCustomerId.getId());
    }

    @Test
    void shouldNotRegisterCompanyIfNameAndNumberNipAlreadyExists(){
        //given
        final var form = new RegisterCompanyForm("Gwiazdy1", "9876543211234");
        registration.registerCustomer(form);
        //when & then
        assertThrows(CustomerAlreadyExistsException.class, () -> registration.registerCustomer(form));
    }
}
