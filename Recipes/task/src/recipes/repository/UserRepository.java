package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findUserByEmail(String email);
}
