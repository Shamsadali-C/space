package Book.my.sapce.Repository;

import Book.my.sapce.Model.OwnerRequest;
import Book.my.sapce.Model.OwnerRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRequestRepository extends JpaRepository<OwnerRequest, Long> {

    List<OwnerRequest> findByStatus(OwnerRequestStatus status);
    Optional<OwnerRequest> findTopByUserIdOrderByIdDesc(Long userId);
    Optional<OwnerRequest> findByUserIdAndStatus(
            Long userId,
            OwnerRequestStatus status
    );
}
