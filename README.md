## GETTING STARTED
Bot SDK enables user to create their own bots that works over Teamchat messaging platform. Bots can send and receive messages just like other Teamchat users. 

You can create your own bot using teamchat Bot SDK. To download Bot SDK

<table>
	<thead>
      <tr>
         <th>Version</th>
         <th>Binaries</th>
         <th>Samples</th>
         <th>Documentation</th>
         <th>All <span style="font-size: 11px; display: block; text-transform: capitalize;">(Binaries, Samples and Docs)</span></th>
         <th>Release Notes</th>
         <th>Release Date</th>
      </tr>
   </thead>
   <tbody>	  
		<td>2.0</td>
		<td>
			<a style="border-bottom: 1px solid #d8d8d8; padding-bottom: 5px; margin-bottom: 5px; display: block;" href="http://teamchat.gupshuptechnolog.netdna-cdn.com/wp-content/uploads/sdk/Java/v2.0/teamchat-sdk-java-2.0.zip">Click Here</a><a href="http://teamchat.gupshuptechnolog.netdna-cdn.com/wp-content/uploads/sdk/Java/v2.0/teamchat-upload-sdk-java-2.0.zip">Upload Component</a> (Optional)
		</td>
		<td>
			<a href="http://teamchat.gupshuptechnolog.netdna-cdn.com/wp-content/uploads/sdk/Java/v2.0/sample-java-2.0.zip">Click Here</a>
		</td>
		<td>
			<a style="margin-right: 10px;" href="http://teamchat.gupshuptechnolog.netdna-cdn.com/wp-content/uploads/sdk/Java/v2.0/doc/index.html" target="_blank">View</a> <a style="margin-right: 10px;" href="http://teamchat.gupshuptechnolog.netdna-cdn.com/wp-content/uploads/sdk/Java/v2.0/doc-java-2.0.zip">Download</a>
		</td>
		<td>
			<a href="http://teamchat.gupshuptechnolog.netdna-cdn.com/wp-content/uploads/sdk/Java/v2.0/teamchat-java-2.0.zip">Click Here</a>
		</td>
		<td>
			<a style="margin-right: 10px;" href="http://www.teamchat.com/en/client-sdk-release-notes/#tab-id-1/" target="_blank">View</a>
		</td>
		<td>15th January 2016</td>
   </tbody>
</table>

### Pre-requisites (for JAVA Bot SDK)



The Java development kit and optionally an IDE. We recommend Eclipse.You can get the tools from

* <p><strong>JDK:</strong> <a href="http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html" target="_blank">http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html</a>
</p>
* <p><strong>Eclipse:</strong> <a href="http://eclipse.org/downloads/packages/eclipse-ide-java-developers/lunasr2" target="_blank">http://eclipse.org/downloads/packages/eclipse-ide-java-developers/lunasr2</a>
</p>

### Create and Verify Bot

Create and verify an email address that you wish to use as your bot identity.

<p>Following steps to be followed while using <a class="to-inappsdkAPI" href="http://www.gupshup.io/developer/sm/overview.html#inappsdk-api">Bot SDK : API to manage your bots</a></p>

1.&nbsp;&nbsp;&nbsp;**Create Bot:** Use create Bot API to register a bot. The API will return followin response as well as email a verification code via email.

```JSON
{
  "botEmail": "hitesh123456@dispostable.com",
  "botVerified": false,
  "created": "2015-10-08T10:36:35.113Z",
  "id": "56164733e4b09632d98e7ce7",
  "lastUpdated": "2015-10-08T10:36:35.113Z",
  "name": "My funky bot",
  "type": "BOT"
}
```

The "id" in above response will be required to verify bot.

2.&nbsp;&nbsp;&nbsp;**Verify Bot:** Verify bot using email, bot id and code received via email.

Once verify you will receive the **apikey** for the bot. You will need this key to work with bot SDK.

```JSON
{
  "apikey": "b822768d07fb4a1bca7672bb66db7640",
  "botEmail": "hitesh123456@dispostable.com",
  "botVerified": true,
  "consumerId": "99b9e5bd-e746-436f-c3cd-ae420f0691be",
  "created": "2015-10-08T10:36:35.113Z",
  "id": "56164733e4b09632d98e7ce7",
  "lastUpdated": "2015-10-08T10:44:13.714Z",
  "name": "My funky bot",
  "type": "BOT"
}
```

### Configure Eclipse to use SDK

* Create a new java project in Eclipse. Add a folder called lib in this project.

* Copy the Jar file (teamchat-client-sdk.1.8.jar) into the lib folder you just created.

* Add the JAR file to the project's build path



### Create Hello World bot


* Create a new pacakage with name test

* Create a new Java Class called HelloWorldBot in the above package

* Copy the snippet below to HelloWorldBot.java

* Ensure to change the apikey to the one you received after verifying bot.

* Compile and Run as Java Application


```javascript

package test;

import com.teamchat.client.annotations.OnKeyword;
import com.teamchat.client.sdk.TeamchatAPI;
import com.teamchat.client.sdk.chatlets.TextChatlet;

/**
 * A simple bot that response to the word "hi" that is posted in
 * any room containing the bot.
 * 
 * Please remember to update the authkey
 */

public class HelloWorldBot 
{
	public static final String authKey = "enter your bot apikey here";

    /**
     * This method creates instance of teamchat client, login using specified 
     * email/password and wait for messages from other users
     **/

    public static void main(String[] args) 
	{
        TeamchatAPI api = TeamchatAPI.fromFile("config.json")
                .setAuthenticationKey(authKey);

        api.startReceivingEvents(new HelloWorldBot());
    }

    /**
     * This method posts Hello World message, when any user posts hi message to
     * the logged in user (see main method)
     **/

    @OnKeyword("hi")
    public void HelloWorld(TeamchatAPI api) 
	{   
        api.perform( 
                api.context().currentRoom().post(
                        new TextChatlet("Hello World")
					)
                );
    }
}
```

<p>Now that you are familiar with teamchat Bot sdk , go ahead and explore our advanced tutorial <a class="to-inappsdk-guide-1" href="http://www.gupshup.io/developer/sm/overview.html#inappsdk-guide">here</a></p>
