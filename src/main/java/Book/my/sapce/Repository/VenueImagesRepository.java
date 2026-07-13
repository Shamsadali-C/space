package Book.my.sapce.Repository;

import Book.my.sapce.Model.VenueImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueImagesRepository extends JpaRepository<VenueImages, Long> {
    List<VenueImages> findByVenueId(Long venueId);
    Long countByVenueId(Long venueId);

}
