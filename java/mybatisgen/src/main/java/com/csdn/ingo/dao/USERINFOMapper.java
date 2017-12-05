package com.csdn.ingo.dao;

import com.csdn.ingo.entity.USERINFO;

public interface USERINFOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(USERINFO record);

    int insertSelective(USERINFO record);

    USERINFO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(USERINFO record);

    int updateByPrimaryKey(USERINFO record);
}