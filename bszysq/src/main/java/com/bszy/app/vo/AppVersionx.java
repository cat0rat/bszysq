// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AppVersionx.java

package com.bszy.app.vo;

import java.io.Serializable;
import java.util.Date;

public class AppVersionx
	implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String verx;
	private String vercode;
	private String descx;
	private Date upgradex;
	private Integer force;
	private Integer release;
	private String url;

	public AppVersionx()
	{
	}

	public String getVerx()
	{
		return verx;
	}

	public void setVerx(String verx)
	{
		this.verx = verx;
	}

	public String getVercode()
	{
		return vercode;
	}

	public void setVercode(String vercode)
	{
		this.vercode = vercode;
	}

	public String getDescx()
	{
		return descx;
	}

	public void setDescx(String descx)
	{
		this.descx = descx;
	}

	public Date getUpgradex()
	{
		return upgradex;
	}

	public void setUpgradex(Date upgradex)
	{
		this.upgradex = upgradex;
	}

	public Integer getForce()
	{
		return force;
	}

	public void setForce(Integer force)
	{
		this.force = force;
	}

	public Integer getRelease()
	{
		return release;
	}

	public void setRelease(Integer release)
	{
		this.release = release;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
}
