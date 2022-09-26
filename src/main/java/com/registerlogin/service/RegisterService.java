package com.registerlogin.service;


import com.registerlogin.dao.RegisterRepository;
import com.registerlogin.model.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService {


    @Autowired
    private RegisterRepository regRepo;



    public Register addUsers(Register register) {
        return regRepo.save(register);
    }

    public List<Register> getUsersList() {
        return regRepo.findAll();
    }

    public Register getUsers(long id) {
        return regRepo.findById(id).orElse(null);
    }

    public void deleteUsers(long id) {
        regRepo.deleteById(id);
    }

    public Register updateProducts(Register register) {
        return regRepo.save(register);
    }


}
