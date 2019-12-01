package com.storeorder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.storeorder.entity.StoreOrder;

@Repository
public interface StoreOrderRepository extends JpaRepository<StoreOrder, Integer> {

	@Query("select distinct u.orderId from StoreOrder u")
	public List<String> findAllUniqueOrderIds();

	@Query("select distinct u.productId  from StoreOrder u")
	public List<String> findAllUniqueProductIds();

	@Query("select distinct u.customerID from StoreOrder u")
	public List<String> findAllUniqueCustomerIds();
}
