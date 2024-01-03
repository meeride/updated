package com.mee.ride.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mee.ride.admin.model.UtilModel;
import java.util.List;


public interface UtilsRepository extends JpaRepository<UtilModel, Long> {
	UtilModel findByCid(String cid);
	UtilModel findByID(long iD);

}
