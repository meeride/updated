package com.mee.ride.rider.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mee.ride.rider.dto.RegisterDto;
import com.mee.ride.rider.model.RegisterRider;
import com.mee.ride.rider.repository.RiderRegisterRepository;
import com.mee.ride.rider.service.RiderRegisterService;

@Service
public class RiderRegisterServiceImpl implements RiderRegisterService {

	// CONSTRUCTOR BASE DEPENDENCY INJECTION
	private RiderRegisterRepository riderRegisterRepository;

	@Autowired
	private ModelMapper modelMapper;

	public RiderRegisterServiceImpl(RiderRegisterRepository riderRegisterRepository) {
		super();
		this.riderRegisterRepository = riderRegisterRepository;
	}

	@Override
	public RegisterRider registerRider(RegisterRider rider) {
		return riderRegisterRepository.save(rider);
	}

	@Override
	public RegisterRider updateRiderToken(RegisterRider rider) {
		return riderRegisterRepository.save(rider);
	}

	@Override
	public RegisterRider approveRider(RegisterRider rider) {
		return riderRegisterRepository.save(rider);
	}

	@Override
	public RegisterRider updateRider(RegisterRider rider) {
		return riderRegisterRepository.save(rider);
	}

	@Override
	public List<RegisterDto> getAllRiders() {
		List<RegisterRider> list = riderRegisterRepository.findAll();
		List<RegisterDto> riders = list.stream().map((rid) -> this.modelMapper.map(rid, RegisterDto.class))
				.collect(Collectors.toList());
		return riders;
	}

	@Override
	public void deleteRider(RegisterRider rider) {
		riderRegisterRepository.delete(rider);

	}

}
