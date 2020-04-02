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

	// テキストメッセージ受信時のイベント
	@EventMapping
	public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		System.out.println("event: " + event);
		System.out.println(currentState);

		switch(currentState) {
		case INITIAL:
			
			if(event.getMessage().getText().equals("登録")) {
				currentState = currentState.accept();
				return new TextMessage(event.getMessage().getText() + "します");
			}
			break;

		case WAIT_FOOD_NAME:
			
			foodName = event.getMessage().getText();
			currentState = currentState.record();
			return new TemplateMessage("入力確認用テンプレートメッセージ",
					new ConfirmTemplate(event.getMessage().getText(),
							new MessageAction("はい", "はい"),
							new MessageAction("いいえ", "いいえ")
							)
					);

		case SET_FOOD_NAME:
			System.out.println("event: " + event.getMessage().getText());
			if(event.getMessage().getText().equals("はい")) {
				myData.setFoodName(foodName);
				currentState = currentState.record();
				return new TextMessage("カロリー[kcal]を整数で入力してください");
			}else {
				foodName = "";
				currentState = currentState.cancel();
			}
			break;

		case WAIT_FOOD_CALORIE:
			
			foodCalorie = Integer.parseInt(event.getMessage().getText());
			currentState = currentState.record();
			return new TemplateMessage("入力確認用テンプレートメッセージ",
					new ConfirmTemplate(event.getMessage().getText(),
							new MessageAction("はい", "はい"),
							new MessageAction("いいえ", "いいえ")
							)
					);

		case SET_FOOD_CALORIE:
			
			if(event.getMessage().getText().equals("はい")) {
				myData.setFoodCalorie(foodCalorie);
				currentState = currentState.record();
				// ここでタイムスタンプ取得，その後DBにflushして終了
				Date time = new Date();
				myData.setTime(time.toString());
				repository.saveAndFlush(myData);
				return new TextMessage(foodName + foodCalorie + "を登録しました");
			}else {
				foodCalorie = 0;
				currentState = currentState.cancel();
			}
			break;
		}
		return new TextMessage(event.getMessage().getText());
	}

	@EventMapping
	public Message handleDefaultMessageEvent(Event event) {
		System.out.println("event: " + event);
		return new TextMessage("テキストメッセージで入力してください");
	}

}
