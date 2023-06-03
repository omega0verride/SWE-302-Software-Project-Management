package com.redscooter.API.OrderLine;

import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository

public interface OrderLineRepository extends JpaRepository<OrderLine, Long>{
}
