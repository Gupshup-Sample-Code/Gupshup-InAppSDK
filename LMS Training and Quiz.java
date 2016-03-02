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

	public static final String authKey = "YOUR API KEY";
	public static final String roomMember1 = "member1@domain.com";
	public static final String roomMember2 = "member2@domain.com";
	public static void main(String[] args) throws Exception 
	{
		TeamchatAPI api = TeamchatAPIImpl.fromFile("config.json")
				.setAuthenticationKey(authKey);
		Room r = api.context().create().setName("LMS Training and Quiz")
				.add(roomMember1)
				.add(roomMember2);

		api.perform(r.post(new TextChatlet("Hello! To take a quiz post the keyword 'Quiz' in the room")));
		api.startReceivingEvents(new LMSBot());
	}
	
	@OnKeyword("Quiz")
	public void onTest(TeamchatAPI api) 
	{
		String user = api.context().currentSender().getEmail();
		String userName = api.context().currentSender().getName();
		System.out.println(user);
		String id = createp2pRoom(user,userName);
		Room p2pRoom = api.context().byId(id);
		p2pRoom.post(new PollChatlet()
				.setQuestionHtml("<img src=\"http://gs.tc.im/kZtXdIFd9MQ\">")
				.alias("welcomePoll"));
		api.perform(p2pRoom);
	}
	private String createp2pRoom(String user, String userName)
	{
		TeamchatAPI api = TeamchatAPIImpl.fromFile("config.json")
				.setAuthenticationKey(authKey);
		Room room = api.context().p2p(user).post(new TextChatlet("Welcome "+userName));
		api.perform(room);
		System.out.println(room.getId());
		String id = room.getId();
		return id;
	}
	
	@OnAlias("welcomePoll")
	public void onreplytopoll(TeamchatAPI api)
	{
		String pollReply = api.context().currentReply().getField("resp");
		System.out.println(pollReply);
		String user = api.context().currentReply().senderEmail();
		System.out.println(user);
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
						.alias("reply")));
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
			
		}
	}
}
