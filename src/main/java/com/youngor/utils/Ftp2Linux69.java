package com.youngor.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Ftp2Linux69 {
	private static Log log = LogFactory.getLog(Ftp2Linux69.class);
	private static String linux_dir="/opt/apache-tomcat-8.0.33/webapps";
	private static String od_file="E:\\eclipse\\aaa\\orderingSystem\\target\\ROOT.war";
	private static String linux_dir_od=linux_dir+"/ROOT";
	private static String stop_tomcat="sh /opt/apache-tomcat-8.0.33/bin/shutdown.sh";
	private static String start_tomcat="sh /opt/apache-tomcat-8.0.33/bin/startup.sh";
	private static List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	static{
		Map<String,String> params=new HashMap<String,String>();
		params.put("host", "192.168.188.69");
		params.put("username", "root");
		params.put("password", "youngor");
		list.add(params);
	}
	static SimpleDateFormat yyyyMMdd=new SimpleDateFormat("yyyyMMdd");
	public static void main(String[] args) throws Exception {
		for(Map<String,String> map:list){
			log.info("正在连接"+map.get("host"));
			ChannelSftp sftp = null;
			Session session = null;
			session =SFTPUtil.connect(map.get("host"), 22, map.get("username"), map.get("password"));
			//先停服务器
			log.info("正在停止服务器："+map.get("host"));
			execCmd(session,stop_tomcat);
			log.info("成功停止服务器："+map.get("host"));
			
			log.info("备份文件ROOT。war："+map.get("host"));
			execCmd(session,"mv "+linux_dir+"/ROOT.war "+linux_dir+"/ROOT.war"+yyyyMMdd.format(new Date()));
			log.info("成功备份文件ROOT.war："+map.get("host"));
            
			log.info("正在开始上传文件:"+od_file);
			Channel channel = session.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			SFTPUtil.upload(linux_dir, od_file, sftp);
			log.info("成功上传文件："+od_file);
			
			log.info("正在删除文件夹："+linux_dir_od);
			//sftp.rmdir(linux_dir_od);
			execCmd(session,"rm -rf "+linux_dir+"/ROOT");
			log.info("成功删除文件夹："+linux_dir_od);
			
			Thread.sleep(10000);//暂停10s，再启动试试
			//启动服务器
			log.info("正在启动服务器："+map.get("host"));
			execCmd(session,start_tomcat);
			log.info("成功启动服务器："+map.get("host"));
			
			log.info("执行：channel.disconnect()");
			channel.disconnect();
			log.info("执行：session.disconnect()");
			session.disconnect();
			log.info("执行：sftp.disconnect()");
			sftp.disconnect();
		}
		
		
	}
	
	private static void execCmd(Session session,String command) throws JSchException{
		BufferedReader reader = null;
		Channel channel = null;

		try {
			if (command != null) {
				channel = session.openChannel("exec");
				((ChannelExec) channel).setCommand(command);
				

				channel.setInputStream(null);
				((ChannelExec) channel).setErrStream(System.err);

				channel.connect();
				InputStream in = channel.getInputStream();
				reader = new BufferedReader(new InputStreamReader(in,Charset.forName("UTF-8")));
				String buf = null;
				while ((buf = reader.readLine()) != null) {
					//System.out.println(buf);
					log.info(buf);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e);
		} catch (JSchException e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e);
			}
			channel.disconnect();
			//session.disconnect();
		}
	}

}
