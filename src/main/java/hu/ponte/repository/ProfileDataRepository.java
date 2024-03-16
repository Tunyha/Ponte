package hu.ponte.repository;

import hu.ponte.domain.ProfileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileDataRepository extends JpaRepository<ProfileData, Integer> {
}
