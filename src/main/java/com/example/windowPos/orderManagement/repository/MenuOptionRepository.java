package com.example.windowPos.orderManagement.repository;

import com.example.windowPos.orderManagement.entity.MenuOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {
}
