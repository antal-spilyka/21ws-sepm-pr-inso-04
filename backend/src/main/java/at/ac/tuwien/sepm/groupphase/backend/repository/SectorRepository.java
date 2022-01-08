package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.HallplanElement;
import at.ac.tuwien.sepm.groupphase.backend.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
    @Query("SELECT sec FROM Performance p, IN(p.hall.sectors) sec")
    //@Query("select s FROM Hall h, IN (h.sectors) s where s.price <= :price+10")
    List<Sector> getSectorForPrice(@Param("price") Integer price);
}