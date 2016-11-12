package com.mao.ssm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mao.lang.MUtil;

@Service
@SuppressWarnings("rawtypes")
public class BaseService<T extends BasePojo, M extends BaseMapper<T>> {
	public M mapper(){return null;}
	
	public T get(Long id){
		return mapper().get(id);
	}
	@Transactional
	public boolean delete(Long id){
		Long rn = mapper().delete(id);
		return rn != null && rn == 1;
	}
	@Transactional
	public boolean dels(String ids){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		Long rn = mapper().dels(map);
		return rn != null && rn > 0;
	}
	@Transactional
	public boolean add(T mo){
		Long rn = mapper().add(mo);
		return rn != null && rn == 1;
	}
	@Transactional
	public boolean update(T mo){
		Long rn = mapper().update(mo);
		return rn != null && rn == 1;
	}
	
	public List<T> list(){
		return mapper().list();
	}
	
	/**
	 * 基方法 查询
	 * @param bs
	 */
	public BasePage<T> list(BaseSearch bs){
		BasePage<T> bp = new BasePage<T>();
		bs.start_i();
		List<T> rows = mapper().list(bs);
		Long total = mapper().lscount(bs);

		bp.t_param(bs.page_i(), bs.limit_i());
		bp.t_result(total, rows);
		if(rows != null && rows.size() > 0){
			T mo = rows.get(rows.size() - 1);
			if(mo != null){
				Long lastid = mo.getId();
				bp.setLastid(lastid != null ? lastid : 0L);
			}
		}
		
		return bp;
	}
	
	/**
	 * 基方法 查询
	 * @param bs
	 */
	public BasePage<Map> list_map(BaseSearch bs){
		BasePage<Map> bp = new BasePage<Map>();
		bs.start_i();
		List<Map> rows = mapper().list_map(bs);
		Long total = mapper().lscount(bs);

		bp.t_param(bs.page_i(), bs.limit_i());
		bp.t_result(total, rows);
		if(rows != null && rows.size() > 0){
			Map mo = rows.get(rows.size() - 1);
			if(mo != null && mo.size() > 0){
				Long lastid = MUtil.toLong(mo.get("id"), 0L);
				bp.setLastid(lastid);
			}
		}
		
		return bp;
	}

	/**
	 * 基方法 查询
	 * @param bs
	 */
	public BasePage<T> list_idval(BaseSearch bs){
		BasePage<T> bp = new BasePage<T>();
		bs.start_i();
		List<T> rows = mapper().list_idval(bs);
		Long total = mapper().lscount(bs);

		bp.t_param(bs.page_i(), bs.limit_i());
		bp.t_result(total, rows);
		if(rows != null && rows.size() > 0){
			T mo = rows.get(rows.size() - 1);
			if(mo != null){
				Long lastid = mo.getId();
				bp.setLastid(lastid != null ? lastid : 0L);
			}
		}
		
		return bp;
	}
	
}
