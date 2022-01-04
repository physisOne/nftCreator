package one.physis.nft.data.repositories;

import one.physis.nft.data.entities.Address;
import one.physis.nft.data.entities.Project;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Integer> {

   Address findTopByTakenAndProject(boolean taken, Project project);
}
