package pl.sda.crm.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sda.crm.util.HibernateUtil;

import static org.junit.jupiter.api.Assertions.*;


class CompanyTest {
    private final SessionFactory factory = HibernateUtil.getSessionFactory();
    private Session session;
    private Transaction tx;

    @BeforeEach
    void before() {
        session = factory.openSession();
        tx = session.beginTransaction();

    }

    @AfterEach
    void after() {
        tx.rollback();
        session.close();
    }

    @Test
    void shouldSaveCompanyInDatabase() {
        //given
        final var company = new Company("Gwiazdy", "1234567891234");
        //when
        saveAndFlush(company);
        //then
        final var readCompany = session.get(Company.class, company.getId());
        //assertEquals(company, readCompany);
        assertEquals(company.getAddresses(), readCompany.getAddresses());
    }


    private void saveAndFlush(Company company) {
        session.save(company);
        session.flush();
        session.clear();
    }
}