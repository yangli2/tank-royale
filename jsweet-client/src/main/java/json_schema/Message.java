package json_schema;

import static def.jquery.Globals.$;

public class Message extends jsweet.lang.Object {

	public Message(String messageType) {
		$set("message-type", messageType);
	}

	public String getMessageType() {
		return (String) $get("message-type");
	}

	public static Message map(Object obj) {
		return (Message) $.extend(false, new Message(null), obj);
	}
}