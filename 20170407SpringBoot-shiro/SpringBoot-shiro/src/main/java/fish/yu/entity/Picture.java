package fish.yu.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * @author yuliang-ds1
 * 图片表实体
 */
@TableName("t_picture")
public class Picture  extends Model<Picture>{
	
	private static final long serialVersionUID = 1L;
	@TableId(type=IdType.AUTO)
	private String id;
	
	@TableField("pictures_id")
	private String picturesId;
	
	private String url;
	
	@TableField("last_update_date")
	private Date lastUpdateDate;
	
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getPicturesId() {
		return picturesId;
	}



	public void setPicturesId(String picturesId) {
		this.picturesId = picturesId;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}



	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}



	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}

}
