package com.example.demo.dao;

import com.example.demo.model.PositiveEnergyDO;
import com.platform.entity.PositiveEnergyDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PositiveEnergyMapper extends BaseDao<PositiveEnergyDO> {


    PositiveEnergyDO selectUserEnergy(@Param("uid") Long uid);

    void updateEnergyList(@Param("list") List<PositiveEnergyDO> list);

}
