package org.grep4j.core.command.linux;

import java.io.IOException;
import java.io.InputStream;

import net.schmizz.sshj.common.IOUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Test {

	public static void main(String[] arg) {
		try {
			JSch jsch = new JSch();
			JSch.setConfig("StrictHostKeyChecking", "no");

			Session session = jsch.getSession("appdev", "10.106.97.11", 22);

			/*
			String xhost="127.0.0.1";
			int xport=0;
			String display=JOptionPane.showInputDialog("Enter display name", 
			                                           xhost+":"+xport);
			xhost=display.substring(0, display.indexOf(':'));
			xport=Integer.parseInt(display.substring(display.indexOf(':')+1));
			session.setX11Host(xhost);
			session.setX11Port(xport+6000);
			*/

			// username and password will be given via UserInfo interface.
			session.setPassword("4hF2A#p?");
			session.connect();
			runCommand(session, "ls /opt/ops/logs/3month/dubdc2-jeerampbir-05/server.log.2012-09-02*");
			runCommand(session, "gunzip -c /opt/ops/logs/3month/dubdc2-jeerampbir-05/server.log.2012-08-26.gz | grep -E 'ERROR' -m 1");

			session.disconnect();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void runCommand(Session session, String command) throws JSchException, IOException {
		Channel channel = session.openChannel("exec");
		((ChannelExec) channel)
				.setCommand(command);

		// X Forwarding
		// channel.setXForwarding(true);

		//channel.setInputStream(System.in);
		channel.setInputStream(null);

		//channel.setOutputStream(System.out);

		//FileOutputStream fos=new FileOutputStream("/tmp/stderr");
		//((ChannelExec)channel).setErrStream(fos);
		((ChannelExec) channel).setErrStream(System.err);

		channel.getInputStream();

		channel.connect();

		System.out.println(IOUtils.readFully(channel.getInputStream()).toString());

		channel.disconnect();

	}

}
