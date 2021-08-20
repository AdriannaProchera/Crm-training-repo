package pl.sda.crm.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pl.sda.crm.entity.Company;
import pl.sda.crm.exception.CustomerAlreadyExistsException;

public class CompanyCustomerRegistration {
    private final SessionFactory sessionFactory;

    public CompanyCustomerRegistration(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public RegisteredCustomerId registerCustomer (RegisterCompanyForm form){
        final var session = sessionFactory.openSession();
        final var tx = session.beginTransaction();

        if (companyExists(form, session)){
            throw new CustomerAlreadyExistsException("customer exists, check data"+ form);
        }

        final var company = Company.from(form);
        session.save(company);

        tx.commit();
        session.close();
        return new RegisteredCustomerId(company.getId());
    }
    private Boolean companyExists(RegisterCompanyForm form, Session session) {
        return session.createQuery("select count(p) > 0 from Company p where p.name = ?1 or p.numberNip = ?2", Boolean.class)
                .setParameter(1, form.getName())
                .setParameter(2, form.getNumberNip())
                .getSingleResult();
    }

}
