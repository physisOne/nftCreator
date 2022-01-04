package one.physis.nft.data.repositories;

import one.physis.nft.data.entities.Nft;
import one.physis.nft.data.entities.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NftRepository extends CrudRepository<Nft, Integer> {

   @Query(nativeQuery = true, value = "SELECT * FROM nft n WHERE n.taken = 0 and n.project_id = :project_id order by RAND() LIMIT :count")
   List<Nft> findNotTaken(@Param("count") int count, @Param("project_id") int projectId);

   Optional<Nft> findByIdAndProject(Integer id, Project project);
}
