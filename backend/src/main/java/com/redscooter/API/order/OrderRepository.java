package com.redscooter.API.order;

import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository

public interface OrderRepository extends JpaRepository<Order, Long>, com.redscooter.API.order.OrderDynamicQueryRepository {
}
