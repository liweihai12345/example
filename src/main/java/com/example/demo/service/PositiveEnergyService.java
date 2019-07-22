package com.example.demo.service;

import com.example.demo.dao.PositiveEnergyRecordMapper;
import com.example.demo.dto.EnergyRecordDTO;
import com.example.demo.model.EnergyTaskModelVO;
import com.example.demo.model.PositiveEnergyDO;
import com.example.demo.util.BigDecimalUtil;
import com.example.demo.util.DateUtils;
import com.example.demo.util.SmsUtil;
import com.example.demo.vo.EnergyTaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 能量任务
 */
@Service
public class PositiveEnergyService {
    @Autowired
    private PositiveEnergyRecordMapper positiveEnergyRecordMapper;
    @Autowired
    private DynamicRuleService dynamicRuleService;


    // 查询用户能量值
    public Map<String, Object> selectUserEnergyList(Long userId) {

        String startDate = DateUtils.getYYYYMMDDForDate();//开始时间
        String endDate = DateUtils.minusDay(startDate, 1);//结束时间
        Map<String, Object> map = new HashMap<>();
        List<EnergyTaskVO> list = dynamicRuleService.selectRuleList(2);//查询任务规则
        List<EnergyRecordDTO> records = positiveEnergyRecordMapper.selectTodayEnergy(userId, startDate, endDate);//查询今日所有任务

//聚合每个任务次数
        Map<Integer, EnergyRecordDTO> mapR = records.stream().collect(Collectors.toMap(EnergyRecordDTO::getEnergyId, Function.identity(), EnergyRecordDTO.summation));
        for (EnergyTaskVO v : list) {
            EnergyRecordDTO r = mapR.get(v.getId());
            if (r != null) {
                int number = v.getLimitnum();//次数/值
                Integer num = r.getNum();//值

                boolean isTrue;
                String template = v.getTitle();
                String value;
                if ("shopping".equals(v.getKeycode())) {//购物按钱数返奖励
                    //用户总能量
                    BigDecimal energyValue = r.getEnergyValue();
                    //消费金额
                    BigDecimal amount = BigDecimalUtil.divide(energyValue.multiply(BigDecimal.valueOf(number)), v.getValue(), 2);
                    isTrue = amount.compareTo(BigDecimal.valueOf(number)) >= 0;//相比是否满足
                    value = amount.compareTo(BigDecimal.valueOf(number)) >= 0 ? String.valueOf(number) : BigDecimalUtil.formatStr(amount, 0);
                } else {
                    isTrue = num >= number;
                    value = num > number ? String.valueOf(number) : String.valueOf(num);
                }
                String title = SmsUtil.replaceStr(template, value);//模板替换
                if (isTrue) {
                    v.setStatus(true);
                } else {
                    v.setStatus(false);
                }
                v.setTitle(title);
            } else {
                String template = v.getTitle();
                String title = SmsUtil.replaceStr(template, "0");//模板替换
                v.setTitle(title);
                v.setStatus(false);
            }
        }
        List<EnergyTaskVO> top = list.stream().filter(o -> o.getCategory() == 1).collect(Collectors.toList());
        List<EnergyTaskVO> down = list.stream().filter(o -> o.getCategory() == 2).collect(Collectors.toList());
        List<EnergyTaskModelVO> modelList = new ArrayList<>();
        EnergyTaskModelVO modeltop = EnergyTaskModelVO.builder().list(top).modelNameBig("能量值").modelNameSmall("每天24点降为0").build();
        EnergyTaskModelVO modeldown = EnergyTaskModelVO.builder().list(down).modelNameBig("蓄能值").modelNameSmall("7日后降为0").build();
        modelList.add(modeltop);
        modelList.add(modeldown);
        map.put("list", modelList);
        PositiveEnergyDO energy = null;
        BigDecimal sum = BigDecimal.ZERO;
        if (energy != null) {
            sum = energy.getEnergyValue();
            BigDecimal lv = energy.getLongValue();
            sum = sum.add(lv);
            sum = sum.compareTo(BigDecimal.valueOf(100)) > 0 ? BigDecimal.valueOf(100) : sum;
        }
        map.put("number", BigDecimalUtil.format(sum, 2));
        return map;

    }


}
