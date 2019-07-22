package com.example.demo.dao;

import com.example.demo.model.DynamicRuleDO;
import com.example.demo.vo.EnergyTaskVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-11 09:14:25
 */
public interface DynamicRuleMapper extends BaseDao<DynamicRuleDO> {

    List<DynamicRuleDO> queryRuleList(@Param("keycode") String keycode, @Param("enabled") Integer enabled);

    List<EnergyTaskVO> selectRuleList(@Param("type") Integer type);

    EnergyTaskVO selectRuleForType(@Param("type") Integer type, @Param("cate") Integer cate);

    DynamicRuleDO selectRuleForKey(@Param("keycode") String keycode, @Param("enabled") Integer enabled);
}
