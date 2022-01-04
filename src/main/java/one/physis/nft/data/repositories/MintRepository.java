package one.physis.nft.data.repositories;

import one.physis.nft.data.entities.Mint;
import one.physis.nft.data.entities.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MintRepository extends CrudRepository<Mint, String> {

   List<Mint> getAllByStateAndProject(int state, Project project);

}
