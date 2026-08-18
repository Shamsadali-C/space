package Book.my.sapce.Repository;

import Book.my.sapce.Model.User;
import Book.my.sapce.Model.Venue;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

    List<Venue> findByOwner(User owner);
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Query("SELECT v FROM Venue v WHERE v.id = :id")
//    Optional<Venue> findByIdForUpdate(@Param("id") Long id);


}
