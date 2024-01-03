package com.mee.ride.user.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mee.ride.user.model.UserWallet;

public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {
	List<UserWallet> findAllByUserId(String userId);
	List<UserWallet> findAllByUserIdAndType(String userId, String type);
}
