package com.mao.ssm;

import java.util.List;
import java.util.Map;

import com.bszy.admin.vo.IdName;

@SuppressWarnings("rawtypes")
public interface BaseMapper<T extends BasePojo> {
	public T get(Long id);
	public Long add(T mo);
	public Long update(T mo);
	public Long delete(Long id);
	public Long dels(Map<String, Object> map);
	public List<T> list();
	
	// TODO 查询 
	public List<T> list(BaseSearch bs);
	public Long lscount(BaseSearch bs);
	public List<IdName> list_idname(BaseSearch bs);
	public List<Map> list_map(BaseSearch bs);
	
}
