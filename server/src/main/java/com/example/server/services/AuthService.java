package com.example.server.services;

import com.example.server.dtos.AuthBodyDtos;
import com.example.server.exception.UserNotFound;
import com.example.server.models.ApplicantsModel;
import com.example.server.models.CompaniesModel;
import com.example.server.repo.ApplicantsRepo;
import com.example.server.repo.CompaniesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private CompaniesRepo companiesRepo ;

    @Autowired
    private ApplicantsRepo applicantsRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<?> Auth (AuthBodyDtos body) {
        List<CompaniesModel> companiesLists = companiesRepo.findAll() ;
        List<ApplicantsModel> applicantsLists = applicantsRepo.findAll();

        for (int i = 0; i < companiesLists.size(); i++) {
            if ( companiesLists.get(i).getEmail().equals(body.getEmail())){
                if (passwordEncoder.matches(body.getPassword() , companiesLists.get(i).getPassword())){
                    return Optional.ofNullable(companiesLists.get(i));
                }
            }
        }
        for (int i = 0; i < applicantsLists.size(); i++) {
            if ( applicantsLists.get(i).getEmail().equals(body.getEmail())){
                if (passwordEncoder.matches(body.getPassword() , applicantsLists.get(i).getPassword())){
                    return Optional.ofNullable(applicantsLists.get(i));
                }
            }
        }

        return Optional.of(new UserNotFound("Not Found"));
    }
}
