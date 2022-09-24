package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOperationRepo extends JpaRepository<Operation, Integer> {

}
