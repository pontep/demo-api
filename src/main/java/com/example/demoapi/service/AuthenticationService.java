package com.example.demoapi.service;

import com.example.demoapi.entity.UserAccount;
import com.example.demoapi.exception.AuthenticationException;
import com.example.demoapi.exception.ErrorCode;
import com.example.demoapi.model.LoginWithUsernameAndPasswordRequest;
import com.example.demoapi.model.UserAccountDto;
import com.example.demoapi.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserAccountRepository userAccountRepository;
    private final ModelMapper modelMapper;

//    @Transactional
    public UserAccountDto loginWithUsernameAndPassword(LoginWithUsernameAndPasswordRequest request) {
        Optional<UserAccount> username = this.userAccountRepository.findUserAccountByUsername(request.getUsername());
        if (username.isPresent()) {
//            log.info("username {}", username.get());
            Optional<UserAccount> userAccount = this.userAccountRepository.loginWithUsernameAndPassword(request);
            if (userAccount.isPresent() && userAccount.get().getLoginFailedCount() < 3) {
//                log.info("userAccount {}", userAccount.get());
                Integer loginFailedCount = 0;
                if (this.userAccountRepository.setLoginFailedCount(username.get().getUsername(), loginFailedCount) == 0) {
                    throw new AuthenticationException(ErrorCode.ERR_AUTHENTICATION.code, "Cannot setLoginFailed!");
                }
                return modelMapper.map(userAccount.get(), UserAccountDto.class);
            } else {
                Integer loginFailedCount = username.get().getLoginFailedCount() + 1;
                if (this.userAccountRepository.setLoginFailedCount(username.get().getUsername(), loginFailedCount) == 0) {
                    throw new AuthenticationException(ErrorCode.ERR_AUTHENTICATION.code, "Cannot setLoginFailed!");
                }
                if (username.get().getLoginFailedCount() >= 3) {
                    throw new AuthenticationException(ErrorCode.ERR_AUTHENTICATION.code, "You have no chance to login!");
                }
                throw new AuthenticationException(ErrorCode.ERR_AUTHENTICATION.code, "Password is incorrect!, you have " + (3 - loginFailedCount) + " chance(s) left.");
            }
        } else {
//            this.userAccountRepository.countFailed(LoginWithUsernameAndPasswordRequest request);
            throw new AuthenticationException(ErrorCode.ERR_AUTHENTICATION.code, "Username is not exist!");
        }
    }
}
