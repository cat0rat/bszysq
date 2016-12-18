// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SysmsgForm.java

package com.bszy.admin.form;

import com.mao.ssm.BaseForm;

public class SysmsgForm extends BaseForm
{

	private static final long serialVersionUID = 1L;
	protected String name;
	protected String content;
	protected String typex;
	protected String extra;
	protected String userid;
	protected String getuicid;
	protected String ids;

	public SysmsgForm()
	{
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getTypex()
	{
		return typex;
	}

	public void setTypex(String typex)
	{
		this.typex = typex;
	}

	public String getExtra()
	{
		return extra;
	}

	public void setExtra(String extra)
	{
		this.extra = extra;
	}

	public String getUserid()
	{
		return userid;
	}

	public void setUserid(String userid)
	{
		this.userid = userid;
	}

	public String getGetuicid()
	{
		return getuicid;
	}

	public void setGetuicid(String getuicid)
	{
		this.getuicid = getuicid;
	}

	public String getIds()
	{
		return ids;
	}

	public void setIds(String ids)
	{
		this.ids = ids;
	}
}
