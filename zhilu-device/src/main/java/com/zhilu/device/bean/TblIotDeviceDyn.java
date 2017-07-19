/**
 * @author : wuhongliang@zhilutec.com
 * @version ：2017年7月19日 上午10:20:19
 */

package com.zhilu.device.bean;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "tbl_iot_device_dyn")
public class TblIotDeviceDyn {
	// `deviceid` varchar(16) NOT NULL,
	@Id
	private String deviceid;
	// `wanaddr` varchar(130) DEFAULT NULL,
	private String wanaddr;
	// `ip` varchar(20) DEFAULT NULL,
	private String ip;
	// `mask` varchar(20) DEFAULT NULL,
	private String mask;
	// `gateway` varchar(20) DEFAULT NULL,
	private String gateway;
	// `dhcpswitch` tinyint(1) DEFAULT NULL,
	private Integer dhcpswitch;
	// `startip` varchar(20) DEFAULT NULL,
	private String startip;
	// `endip` varchar(20) DEFAULT NULL,
	private String endip;
	// `leasetime` int(10) NOT NULL DEFAULT '3600',
	private Long leasetime;
	// `dnsserver1` varchar(20) DEFAULT NULL,
	private String dnsserver1;
	// `dnsserver2` varchar(20) DEFAULT NULL,
	private String dnsserver2;
	// `betaflag` tinyint(1) DEFAULT '0',
	private Integer betaflag;
	// `balancetime` tinyint(11) DEFAULT NULL,
	private Integer balancetime;
	// `addtime` tinyint(11) DEFAULT NULL,
	private Integer addtime;
	// `onlinenum` int(10) DEFAULT NULL,
	private Long onlinenum;
	// `maxonline` int(10) DEFAULT NULL,
	private Long maxonline;
	// `rebootnum` int(10) DEFAULT NULL,
	private Long rebootnum;
	// `upflux` bigint(10) DEFAULT '0',
	private BigInteger upflux;
	// `downflux` bigint(10) DEFAULT '0',
	private BigInteger downflux;
	// `totalflux` bigint(10) DEFAULT '0',

	private BigInteger totalflux;
	// `payload` float(10,2) DEFAULT NULL,

	private Float payload;
	// `totalmem` int(10) DEFAULT NULL,

	private Long totalmem;
	// `freemem` int(10) DEFAULT NULL,

	private Long freemem;
	// `hotspot` varchar(16) DEFAULT NULL,

	private String hotspot;
	// `onlinetime` int(10) DEFAULT NULL,

	private Long onlinetime;
	// `portalid` varchar(16) DEFAULT NULL,

	private String portalid;
	// `ssid` varchar(16) DEFAULT NULL,
	private String ssid;

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getWanaddr() {
		return wanaddr;
	}

	public void setWanaddr(String wanaddr) {
		this.wanaddr = wanaddr;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public Integer getDhcpswitch() {
		return dhcpswitch;
	}

	public void setDhcpswitch(Integer dhcpswitch) {
		this.dhcpswitch = dhcpswitch;
	}

	public String getStartip() {
		return startip;
	}

	public void setStartip(String startip) {
		this.startip = startip;
	}

	public String getEndip() {
		return endip;
	}

	public void setEndip(String endip) {
		this.endip = endip;
	}

	public Long getLeasetime() {
		return leasetime;
	}

	public void setLeasetime(Long leasetime) {
		this.leasetime = leasetime;
	}

	public String getDnsserver1() {
		return dnsserver1;
	}

	public void setDnsserver1(String dnsserver1) {
		this.dnsserver1 = dnsserver1;
	}

	public String getDnsserver2() {
		return dnsserver2;
	}

	public void setDnsserver2(String dnsserver2) {
		this.dnsserver2 = dnsserver2;
	}

	public Integer getBetaflag() {
		return betaflag;
	}

	public void setBetaflag(Integer betaflag) {
		this.betaflag = betaflag;
	}

	public Integer getBalancetime() {
		return balancetime;
	}

	public void setBalancetime(Integer balancetime) {
		this.balancetime = balancetime;
	}

	public Integer getAddtime() {
		return addtime;
	}

	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}

	public Long getOnlinenum() {
		return onlinenum;
	}

	public void setOnlinenum(Long onlinenum) {
		this.onlinenum = onlinenum;
	}

	public Long getMaxonline() {
		return maxonline;
	}

	public void setMaxonline(Long maxonline) {
		this.maxonline = maxonline;
	}

	public Long getRebootnum() {
		return rebootnum;
	}

	public void setRebootnum(Long rebootnum) {
		this.rebootnum = rebootnum;
	}

	public BigInteger getUpflux() {
		return upflux;
	}

	public void setUpflux(BigInteger upflux) {
		this.upflux = upflux;
	}

	public BigInteger getDownflux() {
		return downflux;
	}

	public void setDownflux(BigInteger downflux) {
		this.downflux = downflux;
	}

	public BigInteger getTotalflux() {
		return totalflux;
	}

	public void setTotalflux(BigInteger totalflux) {
		this.totalflux = totalflux;
	}

	public Float getPayload() {
		return payload;
	}

	public void setPayload(Float payload) {
		this.payload = payload;
	}

	public Long getTotalmem() {
		return totalmem;
	}

	public void setTotalmem(Long totalmem) {
		this.totalmem = totalmem;
	}

	public Long getFreemem() {
		return freemem;
	}

	public void setFreemem(Long freemem) {
		this.freemem = freemem;
	}

	public String getHotspot() {
		return hotspot;
	}

	public void setHotspot(String hotspot) {
		this.hotspot = hotspot;
	}

	public Long getOnlinetime() {
		return onlinetime;
	}

	public void setOnlinetime(Long onlinetime) {
		this.onlinetime = onlinetime;
	}

	public String getPortalid() {
		return portalid;
	}

	public void setPortalid(String portalid) {
		this.portalid = portalid;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

}
