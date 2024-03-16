package hu.ponte.repository;

import hu.ponte.domain.CustomUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public CustomUser save(CustomUser toSave) {
        entityManager.persist(toSave);
        return toSave;
    }

    public Optional<CustomUser> findById(Integer userId) {
        return Optional.ofNullable(entityManager.find(CustomUser.class, userId));
    }

    public Optional<CustomUser> findByEmail(String email) {
        CustomUser userByEmail = null;
        try {
            userByEmail = entityManager.createQuery("SELECT c from CustomUser c where c.email = :email", CustomUser.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(userByEmail);
    }


    public Optional<CustomUser> findByActivation(String code) {
        CustomUser userByActivation = null;
        try {
            userByActivation = entityManager.createQuery("SELECT c from CustomUser c where c.activation = :activation", CustomUser.class)
                    .setParameter("activation", code)
                    .getSingleResult();
        } catch (Exception e) {
            log.info("User with activation code not found");
        }
        return Optional.ofNullable(userByActivation);
    }

    public List<CustomUser> findAll() {
        return entityManager.createQuery("SELECT c FROM CustomUser c",CustomUser.class)
                .getResultList();
    }
}