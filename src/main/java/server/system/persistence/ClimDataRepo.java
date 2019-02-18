package server.system.persistence;

import org.springframework.data.jpa.repository.Query;
import server.system.domain.ClimData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClimDataRepo extends CrudRepository<ClimData, String> {
    
    @Query("select c from ClimData c where c.fecha like %:year% order by fecha")
    Iterable<ClimData> getByYear(@Param("year") String year);
    
}
