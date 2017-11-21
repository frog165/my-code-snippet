package com.mymvc.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.mymvc.demo.dao.EmployeeDAO;
import com.mymvc.demo.model.EmployeeVO;

@Service
public class EmployeeManagerImpl implements EmployeeManager{
    @Autowired
    EmployeeDAO dao;
     
    public List<EmployeeVO> getAllEmployees()
    {
        return dao.getAllEmployees();
    }
}
