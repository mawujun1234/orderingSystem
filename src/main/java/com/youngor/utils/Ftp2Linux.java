package com.youngor.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

public class Ftp2Linux {
	private static Log log = LogFactory.getLog(Ftp2Linux.class);
	private static String linux_dir="/usr/local/tomcat/webapps";
	private static String od_file="E:\\eclipse\\aaa\\orderingSystem\\target\\od.war";
	private static String linux_dir_od=linux_dir+"/od";
	private static String stop_tomcat="sh /usr/local/tomcat/bin/shutdown.sh";
	private static String start_tomcat="sh /usr/local/tomcat/bin/startup.sh";
	private static List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	static{
		Map<String,String> params=new HashMap<String,String>();
		params.put("host", "192.168.188.22");
		params.put("username", "root");
		params.put("password", "youngor");
		list.add(params);
		
		Map<String,String> params1=new HashMap<String,String>();
		params1.put("host", "192.168.188.23");
		params1.put("username", "root");
		params1.put("password", "youngor");
		list.add(params1);
		
		Map<String,String> params2=new HashMap<String,String>();
		params2.put("host", "192.168.188.24");
		params2.put("username", "root");
		params2.put("password", "youngor");
		list.add(params2);
	}

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
            
			log.info("正在开始上传文件:"+od_file);
			Channel channel = session.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			SFTPUtil.upload(linux_dir, od_file, sftp);
			log.info("成功上传文件："+od_file);
			
			log.info("正在删除文件夹："+linux_dir_od);
			sftp.rmdir(linux_dir_od);
			log.info("成功删除文件夹："+linux_dir_od);
			
			//启动服务器
			log.info("正在启动服务器："+map.get("host"));
			execCmd(session,start_tomcat);
			log.info("成功启动服务器："+map.get("host"));
			
			log.info("执行：channel.disconnect()");
			channel.disconnect();
			log.info("执行：session.disconnect()");
			session.disconnect();
		}
		
		
	}
	
	private static void execCmd(Session session,String command) throws JSchException{
		BufferedReader reader = null;
		Channel channel = null;

		try {
			while (command != null) {
				channel = session.openChannel("exec");
				((ChannelExec) channel).setCommand(command);

				channel.setInputStream(null);
				((ChannelExec) channel).setErrStream(System.err);

				channel.connect();
				InputStream in = channel.getInputStream();
				reader = new BufferedReader(new InputStreamReader(in));
				String buf = null;
				while ((buf = reader.readLine()) != null) {
					System.out.println(buf);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSchException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			channel.disconnect();
			//session.disconnect();
		}
	}

}
