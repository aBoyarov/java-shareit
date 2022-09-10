package shareit.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shareit.user.model.User;

/**
 * @author Andrey Boyarov
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
