package com.bszy.admin.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bszy.admin.mapper.SliderMapper;
import com.bszy.admin.pojo.Slider;
import com.bszy.app.vo.AppSliderSimple;
import com.mao.ssm.BasePage;
import com.mao.ssm.BaseSearch;
import com.mao.ssm.BaseService;

@Service
public class SliderService extends BaseService<Slider, SliderMapper> {
	
	@Inject
	private SliderMapper mapper;
	public SliderMapper mapper(){return mapper;}
	/**
	 * 基方法 查询
	 * @param bs
	 */
	public BasePage<AppSliderSimple> list_simple(BaseSearch bs){
		BasePage<AppSliderSimple> bp = new BasePage<AppSliderSimple>();
		bs.start_i();
		List<AppSliderSimple> rows = mapper().list_simple(bs);
		Long total = mapper().lscount_simple(bs);

		bp.t_param(bs.page_i(), bs.limit_i());
		bp.t_result(total, rows);
		if(rows != null && rows.size() > 0){
			AppSliderSimple mo = rows.get(rows.size() - 1);
			if(mo != null){
				Long lastid = mo.getId();
				bp.setLastid(lastid != null ? lastid : 0L);
			}
		}
		
		return bp;
	}
	
}
