// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AppUserAuth.java

package com.bszy.app.vo;

import java.io.Serializable;

public class AppUserAuth
	implements Serializable
{

	private static final long serialVersionUID = 1L;
	protected Long id;
	protected Integer authx;
	protected Integer isdel;

	public AppUserAuth()
	{
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Integer getAuthx()
	{
		return authx;
	}

	public void setAuthx(Integer authx)
	{
		this.authx = authx;
	}

	public Integer getIsdel()
	{
		return isdel;
	}

	public void setIsdel(Integer isdel)
	{
		this.isdel = isdel;
	}

	public String toString()
	{
		return (new StringBuilder("AppUserAuth [id=")).append(id).append(", authx=").append(authx).append(", isdel=").append(isdel).append("]").toString();
	}
}
