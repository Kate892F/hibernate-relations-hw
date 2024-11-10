package mate.academy.hibernate.relations.dao.impl;

import java.util.Optional;
import mate.academy.hibernate.relations.dao.AbstractTest;
import mate.academy.hibernate.relations.dao.CountryDao;
import mate.academy.hibernate.relations.model.Country;
import org.junit.Assert;
import org.junit.Test;

public class CountryDaoImplTest extends AbstractTest {
  public static final Country usa = new Country("USA");
  public static final Country ukraine = new Country("Ukraine");

  @Override
  protected Class<?>[] entities() {
    return new Class[] {Country.class};
  }

  @Test
  public void create_Ok() {
    CountryDao countryDao = new CountryDaoImpl(getSessionFactory());
    verifyCreateCountryWorks(countryDao, usa.clone(), 1L);
    verifyCreateCountryWorks(countryDao, ukraine.clone(), 2L);
  }

  @Test
  public void getById_Ok() {
    CountryDao countryDao = new CountryDaoImpl(getSessionFactory());
    verifyCreateCountryWorks(countryDao, usa.clone(), 1L);
    Optional<Country> usaOptional = countryDao.get(1L);
    Assert.assertTrue(usaOptional.isPresent());
    Country actualUsa = usaOptional.get();
    Assert.assertNotNull(actualUsa);
    Assert.assertEquals(1L, actualUsa.getId().longValue());
    Assert.assertEquals(usa.getName(), actualUsa.getName());

    verifyCreateCountryWorks(countryDao, ukraine.clone(), 2L);
    Optional<Country> ukraineOptional = countryDao.get(2L);
    Assert.assertTrue(ukraineOptional.isPresent());
    Country actualUkraine = ukraineOptional.get();
    Assert.assertNotNull(actualUkraine);
    Assert.assertEquals(2L, actualUkraine.getId().longValue());
    Assert.assertEquals(ukraine.getName(), actualUkraine.getName());
  }

  @Test
  public void getByNotExistingId_Ok() {
    CountryDao countryDao = new CountryDaoImpl(getSessionFactory());
    Optional<Country> actual = countryDao.get(100L);
    Assert.assertFalse(actual.isPresent());
  }

  static void verifyCreateCountryWorks(CountryDao countryDao, Country country, Long expectedId) {
    Country actual = countryDao.add(country);
    Assert.assertNotNull(
        "Check you have implemented the `create` method " + "in the CountryDaoImpl class", actual);
    Assert.assertNotNull("ID for country should be autogenerated", actual.getId());
    Assert.assertEquals(expectedId, actual.getId());
    Assert.assertEquals(country.getName(), actual.getName());
  }
}
