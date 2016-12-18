// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AppVersionxAsia.java

package com.bszy.app.vo;

import java.io.Serializable;
import java.util.List;

// Referenced classes of package com.bszy.app.vo:
//			AppVersionx

public class AppVersionxAsia
	implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String version;
	private String versionCode;
	private String describe;
	private boolean force;
	private String url;
	private String reqVersion;
	private String reqVersionCode;
	private String reqVersionDescribe;
	private String reqVersionUrl;

	public AppVersionxAsia()
	{
	}

	public static AppVersionxAsia fromAppVersionxs(List vxs)
	{
		if (vxs == null || vxs.size() == 0)
			return null;
		AppVersionx vx0 = (AppVersionx)vxs.get(0);
		if (vx0 == null || vx0.getVercode() == null)
			return null;
		AppVersionxAsia aa = new AppVersionxAsia();
		aa.version = vx0.getVerx();
		aa.versionCode = vx0.getVercode();
		aa.describe = vx0.getDescx();
		aa.force = Integer.valueOf(1).equals(vx0.getDescx());
		aa.url = vx0.getUrl();
		if (vxs.size() > 1)
		{
			AppVersionx vx1 = (AppVersionx)vxs.get(1);
			if (vx1 != null && vx1.getVercode() != null)
			{
				aa.reqVersion = vx1.getVerx();
				aa.reqVersionCode = vx1.getVercode();
				aa.reqVersionDescribe = vx1.getDescx();
				aa.reqVersionUrl = vx1.getUrl();
			}
		}
		return aa;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getVersionCode()
	{
		return versionCode;
	}

	public void setVersionCode(String versionCode)
	{
		this.versionCode = versionCode;
	}

	public String getDescribe()
	{
		return describe;
	}

	public void setDescribe(String describe)
	{
		this.describe = describe;
	}

	public boolean isForce()
	{
		return force;
	}

	public void setForce(boolean force)
	{
		this.force = force;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getReqVersion()
	{
		return reqVersion;
	}

	public void setReqVersion(String reqVersion)
	{
		this.reqVersion = reqVersion;
	}

	public String getReqVersionCode()
	{
		return reqVersionCode;
	}

	public void setReqVersionCode(String reqVersionCode)
	{
		this.reqVersionCode = reqVersionCode;
	}

	public String getReqVersionDescribe()
	{
		return reqVersionDescribe;
	}

	public void setReqVersionDescribe(String reqVersionDescribe)
	{
		this.reqVersionDescribe = reqVersionDescribe;
	}

	public String getReqVersionUrl()
	{
		return reqVersionUrl;
	}

	public void setReqVersionUrl(String reqVersionUrl)
	{
		this.reqVersionUrl = reqVersionUrl;
	}
}
