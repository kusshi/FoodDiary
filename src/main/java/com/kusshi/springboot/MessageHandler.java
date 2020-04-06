package com.kusshi.springboot;

import org.springframework.beans.factory.annotation.Autowired;

import com.kusshi.springboot.repositories.MyDataRepository;
import com.kusshi.springboot.StatePattern;
import com.kusshi.springboot.State;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ConfirmTemplate;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.Date;

@LineMessageHandler
public class MessageHandler {

	@Autowired
	MyDataRepository repository;

	MyData myData = new MyData();

	State currentState = State.INITIAL;

	String foodName = "";
	int foodCalorie = 0;
	String currentTime = "";

	// テキストメッセージ受信時のイベント
	@EventMapping
	public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		System.out.println("event: " + event);
		System.out.println(currentState);

		switch(currentState) {
		case INITIAL:
			if(event.getMessage().getText().equals("登録")) {
				currentState = currentState.record();
				return new TemplateMessage("入力確認用テンプレートメッセージ",
						new ConfirmTemplate("登録しますか？",
								new MessageAction("はい", "はい"),
								new MessageAction("いいえ", "いいえ")
								)
						);
			} else if(event.getMessage().getText().equals("閲覧")) {
				currentState = currentState.browse();
				return new TemplateMessage("入力確認用テンプレートメッセージ",
						new ConfirmTemplate("記録を閲覧しますか？",
								new MessageAction("はい", "はい"),
								new MessageAction("いいえ", "いいえ")
								)
						);
			}
			break;
			
		case START_RECORDIG_FOOD:
			if(event.getMessage().getText().equals("はい")) {
				currentState = currentState.accept();
				return new TextMessage("食品名を入力してください");
			}else {
				currentState = currentState.cancel();
				return new TextMessage("キャンセルします");
			}
			
		case WAIT_FOOD_NAME:
			foodName = event.getMessage().getText();
			currentState = currentState.accept();
			return new TemplateMessage("入力確認用テンプレートメッセージ",
					new ConfirmTemplate(event.getMessage().getText() + "でよろしいですか？",
							new MessageAction("はい", "はい"),
							new MessageAction("いいえ", "いいえ")
							)
					);

		case SET_FOOD_NAME:
			System.out.println("event: " + event.getMessage().getText());
			if(event.getMessage().getText().equals("はい")) {
				myData.setFoodName(foodName);
				currentState = currentState.accept();
				return new TextMessage("食品のカロリー[kcal]を整数で入力してください");
			}else {
				foodName = "";
				currentState = currentState.cancel();
				return new TextMessage("キャンセルします");
			}

		case WAIT_FOOD_CALORIE:
			foodCalorie = Integer.parseInt(event.getMessage().getText());
			currentState = currentState.accept();
			return new TemplateMessage("入力確認用テンプレートメッセージ",
					new ConfirmTemplate(event.getMessage().getText() + "でよろしいですか？",
							new MessageAction("はい", "はい"),
							new MessageAction("いいえ", "いいえ")
							)
					);

		case SET_FOOD_CALORIE:
			if(event.getMessage().getText().equals("はい")) {
				myData.setFoodCalorie(foodCalorie);
				// ここでタイムスタンプ取得
				Date time = new Date();
				currentTime = time.toString();
				myData.setTime(currentTime);
				currentState = currentState.accept();
				return new TemplateMessage("入力確認用テンプレートメッセージ",
						new ConfirmTemplate(foodName + "(" + foodCalorie + ")" + ":" + currentTime + "を登録しますか？",
								new MessageAction("はい", "はい"),
								new MessageAction("いいえ", "いいえ")
								)
						);
			}else {
				foodCalorie = 0;
				currentState = currentState.cancel();
				return new TextMessage("キャンセルします");
			}
			
		case END_RECORDING_FOOD:
			if(event.getMessage().getText().equals("はい")) {
				repository.saveAndFlush(myData);
				currentState = currentState.accept();
				return new TextMessage(foodName + "(" + foodCalorie + ")" + ":" + currentTime + "を登録しました");
			}else {
				foodCalorie = 0;
				currentState = currentState.cancel();
				return new TextMessage("キャンセルします");
			}
			
		case START_BROWSING_RECORD:
			if(event.getMessage().getText().equals("はい")) {
				currentState = currentState.accept();
				return new TextMessage("閲覧します");
			}else {
				currentState = currentState.cancel();
				return new TextMessage("キャンセルします");
			}
			
			
		}
		
		return new TextMessage(event.getMessage().getText());
	}

	@EventMapping
	public Message handleDefaultMessageEvent(Event event) {
		System.out.println("event: " + event);
		return new TextMessage("テキストメッセージで入力してください");
	}

}
