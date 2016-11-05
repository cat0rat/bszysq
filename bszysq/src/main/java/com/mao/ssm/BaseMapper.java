package com.mao.ssm;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface BaseMapper<T extends BasePojo> {
	public T get(Long id);
	public Long add(T mo);
	public Long update(T mo);
	public Long delete(Long id);
	public List<T> list();
	
	// TODO 查询 
	public List<T> list(BaseSearch bs);
	public List<Map> list_map(BaseSearch bs);
	public Long lscount(BaseSearch bs);
	
}
