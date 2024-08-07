package com.example.windowPos.orderManagement.repository;

import com.example.windowPos.orderManagement.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
