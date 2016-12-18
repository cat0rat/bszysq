package com.bszy.admin.mapper;

import java.util.List;

import com.bszy.admin.pojo.Versionx;
import com.bszy.admin.vo.VersionxSearch;
import com.bszy.app.vo.AppVersionx;
import com.mao.ssm.BaseMapper;

/**
 * 
 * @author Mao 2016年10月26日 上午1:23:12
 */
public interface VersionxMapper extends BaseMapper<Versionx> {
	public abstract List<AppVersionx> for_packagex(VersionxSearch versionxsearch);
}