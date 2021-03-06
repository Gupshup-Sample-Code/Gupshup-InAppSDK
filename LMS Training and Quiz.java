package com.LMSTraningAndQuiz;

import com.teamchat.client.annotations.OnAlias;
import com.teamchat.client.annotations.OnKeyword;

import com.teamchat.client.sdk.Room;
import com.teamchat.client.sdk.TeamchatAPI;

import com.teamchat.client.sdk.chatlets.PollChatlet;
import com.teamchat.client.sdk.chatlets.PrimaryChatlet;
import com.teamchat.client.sdk.chatlets.TextChatlet;
import com.teamchat.client.sdk.impl.TeamchatAPIImpl;

public class LMSBot 
{
	// Get your API from gupshup.io
	int questionNo; 
	public static final String authKey = "YOUR API KEY";
	
	//  list the email address of the members to want in the room.
	public static final String roomMember1 = "member1@domain.com";
	public static final String roomMember2 = "member2@domain.com";
	
	public static void main(String[] args) throws Exception 
	{
		// Create a TeamchatApi Instance
		TeamchatAPI api = TeamchatAPIImpl.fromFile("config.json")
				.setAuthenticationKey(authKey);
				
		// Create a group/Room.
		Room r = api.context().create().setName("LMS Training and Quiz")
				.add(roomMember1)
				.add(roomMember2);

		api.perform(r.post(new TextChatlet("Hello! To take a quiz post the keyword 'Quiz' in the room")));
		
		// Setups bot to listen to the events from teamchat server.
		api.startReceivingEvents(new LMSBot());
	}
	
	// Register for a keyword “Quiz” using @OnKeyword() annotaion.
	@OnKeyword("Quiz")
	public void onTest(TeamchatAPI api) 
	{
		String user = api.context().currentSender().getEmail();
		String userName = api.context().currentSender().getName();
		System.out.println(user);
		
		//  Create a P2P Room
		String id = createp2pRoom(user,userName);
		
		Room p2pRoom = api.context().byId(id);
		p2pRoom.post(new PollChatlet()
				.setQuestionHtml("<img src=\"http://gs.tc.im/kZtXdIFd9MQ\">")//replace with your source url or plain text.
				.alias("welcomePoll"));
		api.perform(p2pRoom);
	}
	private String createp2pRoom(String user, String userName)
	{
		TeamchatAPI api = TeamchatAPIImpl.fromFile("config.json")
				.setAuthenticationKey(authKey);
		
		// Create p2p room
		Room room = api.context().p2p(user).post(new TextChatlet("Welcome "+userName));
		api.perform(room);
		String id = room.getId();
		return id;
	}
	
	@OnAlias("welcomePoll")
	public void onreplytopoll(TeamchatAPI api)
	{
		// capture the 
		String pollReply = api.context().currentReply().getField("resp");
		String user = api.context().currentReply().senderEmail();
		if(pollReply.equalsIgnoreCase("Yes"))
		{
			api.performPostInCurrentRoom(new PollChatlet().setQuestionHtml("<html><center><img src=\"http://c.asstatic.com/images/1963067_635174270222126250-1.jpg\" >"
					+ "<br>"
					+ "<a href=\"https://youtu.be/gRSJG6QB03I\">Click to watch the video</a>"
					+ "<br>"
					+ "Once you watch the video, click on Thumbs Up to start the quiz."
					+ "</center></html>").alias("Questionnaire"));
		}else
		{
			api.performPostInCurrentRoom(new TextChatlet("If you wish to continue the quiz later post Quiz in room to get started!"));
		}
	}
	
	@OnAlias("Questionnaire")
	public void quizQuestions(TeamchatAPI api)
	{
		// Your question can be fetch --- coding for same is required -----
		//QuestionSet can be fetch from your database or any backend resource.
		if(questionNo <= QuestionSet.length)
		{
			questionNo++;
			api.perform(api
					.context()
					.currentRoom()
					.post(new PrimaryChatlet()
							.setReplyLabel("Submit")
							.setQuestionHtml("http://gs.tc.im/RfVfYRYw") // question can be variable and iterate. 
							.setReplyScreen(
									api.objects()
											.form().addField(api.objects()
															.input()
															.name("userAnswer").label("Enter Option Number").enableLastReplyUpdate()))
							.alias("reply")));
		}else
		{
				api.perform(api
					.context()
					.currentRoom()
					.post(new PrimaryChatlet()
							.setReplyLabel("Submit")
							.setQuestionHtml("http://gs.tc.im/RfVfYRYw")
							.setReplyScreen(
									api.objects()
											.form().addField(api.objects()
															.input()
															.name("userAnswer").label("Enter Option Number").enableLastReplyUpdate()))
							.alias("QuestionnaireEnd")));
		}
	}
	
	@OnAlias("reply")
	public void processReply(TeamchatAPI api)
	{
		String userAnswer = api.context().currentReply().getField("userAnswer");
		String databaseAnswer = "1";
		if(userAnswer.equals(databaseAnswer))
		{
			api.perform(api
					.context()
					.currentRoom()
					.post(new PrimaryChatlet()
							.setQuestionHtml("http://gs.tc.im/kZviNa8WyJp")
							.alias("Questionnaire")));
		}else
		{
			api.perform(api
					.context()
					.currentRoom()
					.post(new PrimaryChatlet()
							.setQuestionHtml("<html><b>Your Answer is Incorrect</b></html>")
							.alias("Questionnaire")));
		}
	}
	
	@OnAlias("QuestionnaireEnd")
	public void QuestionnaireEnd(TeamchatAPI api)
	{
		api.performPostInCurrentRoom(new TextChatlet("You have successfully completed the Quiz"));	
	}
}
