package com.mapache.Enotes_API_Service.service.impl;

import com.mapache.Enotes_API_Service.entity.AccountStatus;
import com.mapache.Enotes_API_Service.entity.User;
import com.mapache.Enotes_API_Service.exception.ResourceNotFoundException;
import com.mapache.Enotes_API_Service.exception.SuccessException;
import com.mapache.Enotes_API_Service.repository.UserRepository;
import com.mapache.Enotes_API_Service.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HomeServiceImpl implements HomeService {

    private final UserRepository userRepository;

    public HomeServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Boolean verifyAccount(Integer userId, String verificationCode) throws ResourceNotFoundException {
        log.info("HomeServiceImpl : verifyAccount() : Start");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid User"));

        if (user.getStatus().getVerificationCode() == null) {
            log.warn("Message : Account already verified");
            throw new SuccessException("Account already verified");
        }

        if(user.getStatus().getVerificationCode().equals(verificationCode)){
            AccountStatus status = user.getStatus();
            status.setIsActive(true);
            status.setVerificationCode(null);
            userRepository.save(user);
            log.info("Message : Account verified successfully");
            return true;
        }
        log.info("HomeServiceImpl : verifyAccount() : end");
        return false;
    }
}
