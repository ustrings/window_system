package com.vaolan.sspserver.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * cookmaping
 * @author fushengli
 *
 */
@Table("ad_cookie_mapping")
public class AdCookieMapping
{
	@Column("adx_cid")
	private String adxCid;

	// 广告名称
	@Column("vdsp_cid")
	private String vdspCid;

	// 广告描述
	@Column("adx_vendor")
	private String adxVendor;

	/**
	 * @return the adxCid
	 */
	public String getAdxCid()
	{
		return adxCid;
	}

	/**
	 * @param adxCid the adxCid to set
	 */
	public void setAdxCid(String adxCid)
	{
		this.adxCid = adxCid;
	}

	/**
	 * @return the vdspCid
	 */
	public String getVdspCid()
	{
		return vdspCid;
	}

	/**
	 * @param vdspCid the vdspCid to set
	 */
	public void setVdspCid(String vdspCid)
	{
		this.vdspCid = vdspCid;
	}

	/**
	 * @return the adxVendor
	 */
	public String getAdxVendor()
	{
		return adxVendor;
	}

	/**
	 * @param adxVendor the adxVendor to set
	 */
	public void setAdxVendor(String adxVendor)
	{
		this.adxVendor = adxVendor;
	}
}
