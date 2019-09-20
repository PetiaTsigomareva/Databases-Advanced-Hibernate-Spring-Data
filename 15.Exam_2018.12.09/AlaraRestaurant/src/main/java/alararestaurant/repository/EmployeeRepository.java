package alararestaurant.repository;

import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Order;
import alararestaurant.domain.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByName(String employee);

    @Query(value = "SELECT e " +
                   "FROM alararestaurant.domain.entities.Employee e "+
                   "JOIN e.orders o " +
                   "WHERE e.position = :position " +
                   "GROUP BY e.id "
                  +"ORDER BY e.name,o.id ")
    Set<Employee> findBurgerFlippersOrders(@Param("position") Position position);

    List<Employee> findByPosition(Integer positionId);
}
