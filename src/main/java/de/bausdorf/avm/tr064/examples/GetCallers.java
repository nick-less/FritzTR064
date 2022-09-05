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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bausdorf.avm.tr064.Action;
import de.bausdorf.avm.tr064.FritzConnection;
import de.bausdorf.avm.tr064.Response;
import de.bausdorf.avm.tr064.Service;

/**
 * Tool to retrieve the call list.
 */
public class GetCallers extends Tool {
	private static final Logger LOG = LoggerFactory.getLogger(GetCallers.class);
	
	public static void main(String[] args) throws Exception {
		new GetCallers(args).run();
	}
	
	public GetCallers(String[] args) {
		super(args);
	}
	
	private void run() throws IOException, NoSuchFieldException {
		FritzConnection fc = openConnection();
		
		getCallers(fc);
	}

	private void getCallers(FritzConnection fc) throws IOException, NoSuchFieldException {
		Service service = fc.getService("X_AVM-DE_OnTel:1");
		Action action = service.getAction("GetCallList");
		
		Response response = action.execute();
		String callListUrl = response.getValueAsString("NewCallListURL");

		System.out.println(callListUrl);
		
		URL url = new URL(callListUrl + "&days=2");
		char[] buffer = new char[4096];
		try (InputStream in = url.openStream()) {
			try (InputStreamReader r = new InputStreamReader(in)) {
				while (true) {
					int direct = r.read(buffer);
					if (direct < 0) {
						break;
					}
					
					System.out.print(String.valueOf(buffer, 0, direct));
				}
			}
		}
	}

}
