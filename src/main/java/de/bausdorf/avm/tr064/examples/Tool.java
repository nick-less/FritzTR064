/***********************************************************************************************************************
 *
 * javaAVMTR064 - open source Java TR-064 API
 *===========================================
 *
 * Copyright 2015 Marin Pollmann <pollmann.m@gmail.com>
 * 
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************/
package de.bausdorf.avm.tr064.examples;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bausdorf.avm.tr064.FritzConnection;
import de.bausdorf.avm.tr064.ParseException;

/**
 * Base class for tools based on TR064.
 */
public abstract class Tool {
	private static final Logger LOG = LoggerFactory.getLogger(Tool.class);

	protected final String ip;
	protected final String user;
	protected final String password;

	/** 
	 * Creates a {@link Tool}.
	 *
	 * @param args
	 */
	public Tool(String[] args) {
		if( args.length < 2 ) {
			try {
				Properties properties = new Properties();
				properties.load(new FileInputStream(new File(".fritzbox")));
				
				ip = properties.getProperty("fritzbox.ip");
				user = properties.getProperty("fritzbox.user");
				password = properties.getProperty("fritzbox.password");
				
				LOG.info("Connecting to '" + ip +"' with user '" + user + "', using password '" + password.replaceAll(".", "*") +"'.");
			} catch (IOException ex) {
				throw new IllegalArgumentException("args: <fb-ip> <password> [user]", ex);
			}
		} else {
			ip = args[0];
			password = args[1];
			if( args.length > 2 ) {
				user = args[2];
			} else {
				// Note: When user is null, no authentication is performed.
				user ="";
			}
		}
	}
	
	/** 
	 * Create a new FritzConnection with username and password.
	 */
	protected FritzConnection openConnection() {
		FritzConnection fc = new FritzConnection(ip,user,password);
		try {
			//The connection has to be initiated. This will load the tr64desc.xml respectively igddesc.xml 
			//and all the defined Services and Actions. 
			fc.init(null);
		} catch (IOException | ParseException e2) {
			//Any HTTP related error.
			LOG.error(e2.getMessage(), e2);
		}
		return fc;
	}

}
