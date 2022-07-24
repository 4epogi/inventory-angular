package com.chepogi.inventory.repository;

import com.chepogi.inventory.model.DeviceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceUserRepository extends JpaRepository<DeviceUser, Long> {
}
