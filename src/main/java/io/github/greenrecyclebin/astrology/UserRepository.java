package io.github.greenrecyclebin.astrology;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
interface UserRepository extends CrudRepository<User, Long> {
  @Cacheable("firstUserByFirstName")
  User findFirstByFirstName(String firstName);

  @Cacheable("usersByFirstName")
  List<User> findByFirstName(String firstName);
}
