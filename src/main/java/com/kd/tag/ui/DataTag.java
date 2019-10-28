package com.kd.tag.ui;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.kd.manage.entity.BaseData;
import com.kd.manage.support.DataDictDefault;



/**
 * tag 基础数据(获取名称 、 获取JSON串)
 * @author zlm
 * @version 1.0
 */
public class DataTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	protected String style="zlmui";
	private String code;	//代码
	private String type;	//代码类型
	private boolean json;	//json类型返回
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			String menu = (String) this.pageContext.getSession().getAttribute("baseDataCache"+ type +  code + json +style);
			if(menu!=null){
				out.print(menu);
			}else{
				menu=end().toString();
				if(json){
					menu = "{" + menu +"}";
				}
				this.pageContext.getSession().setAttribute("baseDataCache" + type + code + json + style,menu);
				out.print(menu);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public StringBuffer end() {	
		StringBuffer sb = new StringBuffer();
		if(type != null){
			List<BaseData> db = DataDictDefault.getList(type);
			int count = 0 ,len = db !=null ? db.size() : 0;
			for (BaseData baseData : db) {
				count++;
				if(code != null && baseData.getCode().equals(code) ){
					sb.append(baseData.getName());
					break;
				}else if(json){
					sb.append("\"" + baseData.getCode() + "\":\"" + baseData.getName()+"\"");
					if(len != count){
						sb.append(",");
					}
				}
			}
		}
		return sb;
	}
	public void setStyle(String style) {
		this.style = style;
	}

	public boolean isJson() {
		return json;
	}

	public void setJson(boolean json) {
		this.json = json;
	}



	

}
