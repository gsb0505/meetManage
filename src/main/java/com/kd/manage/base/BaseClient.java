/**
 * 
 */
package com.kd.manage.base;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.kd.common.unit.util.TemporaryUtils;
import com.kd.manage.controller.util.PropertiesUtil;
import com.kd.manage.entity.PageCount;


/**
 * 
 * @类名称：BaseClient.java
 * @类描述：http请求客户端

 * @创建时间：2015年1月22日-上午11:21:31
 * @修改备注:
 * @version
 */
public class BaseClient{
	
	

	private static JerseyClient jerseyClient = getJerseyClient();
	private static Client client;
	public static final String agentRoleServerUri;
		
	static{
		agentRoleServerUri = PropertiesUtil.readValue("agentRoleServerUri");
	} 
	
	/**
	 * 
	 *@描述:这个类用来生成权限验证的target对象
	 *@创建时间：2015年1月28日 下午1:06:14
	 *@修改人：
	 *@修改时间：
	 *@修改描述：
	 *@param
	 *@return
	 *
	 * @param URL
	 * @return
	 */
	public static JerseyWebTarget getWebTarget(String URL) {

		JerseyWebTarget target = jerseyClient.target(URL).register(
				HttpAuthenticationFeature.basicBuilder()
						.credentials("test", "testtest11").build());

		return target;
	}
	

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		/*JerseyWebTarget target = BaseClient
				.getWebTarget(agentRoleServerUri+"query/");
		List<AgentRole> agentList = target.request().get(
				new GenericType<List<AgentRole>>() {
				});
		System.out.println(agentList.size());*/
		
		System.out.println(BaseUri.class.getName());
	
	}
	/**
	 * 
	 *@描述  ：用于生成分页查询的报文参数报文
	 *@创建时间：2015年1月28日 下午1:07:02
	 *@修改人：
	 *@修改时间：
	 *@修改描述：
	 *@param
	 *@return
	 *
	 * @param page 分页对象
	 * @return
	 */
	
	public static String createPageMessage(PageCount page){
		String currentPage=""+page.getCurrentPage();
		String showCount=""+page.getShowCount();
		String sortName=page.getSortName();
		String sortOrder=page.getSortOrder();
		String sendData =(TemporaryUtils.isEmpty(currentPage)?"":"&currentPage="+currentPage)+
				(TemporaryUtils.isEmpty(showCount)?"":"&showCount="+showCount)+
				(TemporaryUtils.isEmpty(sortName)?"":"&sortName="+sortName)+
				(TemporaryUtils.isEmpty(sortOrder)?"":"&sortOrder="+sortOrder);
		
		return sendData;
		
	}
	
	/**
	 * get Client
	 * @return Client
	 */
	public static Client getClient(){
		if(client == null){
			client = ClientBuilder.newClient();
		}
		return client;
	}
	
	/**
	 * get JerseyClient
	 * @return JerseyClient
	 */
	public static JerseyClient getJerseyClient(){
		if(jerseyClient == null || jerseyClient.isClosed()){
			jerseyClient = JerseyClientBuilder.createClient();
		}
		return jerseyClient;
	}
	
	
	
}
