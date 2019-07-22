package com.example.demo.service;

import com.example.demo.dao.DynamicRuleMapper;
import com.example.demo.vo.EnergyTaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DynamicRuleService {
    @Autowired
    private DynamicRuleMapper dynamicRuleMapper;

    /**
     * 能量规则类型
     *
     * @param type
     * @return
     */
    public List<EnergyTaskVO> selectRuleList(Integer type) {
        List<EnergyTaskVO> list = dynamicRuleMapper.selectRuleList(type);
        return list;
    }


}
