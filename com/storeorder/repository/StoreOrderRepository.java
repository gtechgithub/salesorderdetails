package com.storeorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.storeorder.entity.StoreOrder;


@Repository
public interface StoreOrderRepository extends JpaRepository<StoreOrder, Integer> {

}
