package alararestaurant.repository;

import alararestaurant.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o " +
            "FROM alararestaurant.domain.entities.Order o " +
            "JOIN o.employee e " +
            "JOIN o.orderItems oi " +
            "JOIN oi.item i " +
            "WHERE e.position.name = 'Burger Flipper' " +
            "GROUP BY o.customer " +
            "ORDER BY e.name, o.id "
    )
    List<Order>findBurgerFlippersOrders1(); }
