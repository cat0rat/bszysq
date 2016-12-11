package com.bszy.admin.mapper;

import java.util.List;

import com.bszy.admin.pojo.Slider;
import com.bszy.app.vo.AppSliderSimple;
import com.mao.ssm.BaseMapper;
import com.mao.ssm.BaseSearch;

/**
 * 
 * @author Mao 2016年10月26日 上午1:23:12
 */
public interface SliderMapper extends BaseMapper<Slider> {

	public List<AppSliderSimple> list_simple(BaseSearch bs);
	public Long lscount_simple(BaseSearch bs);
}