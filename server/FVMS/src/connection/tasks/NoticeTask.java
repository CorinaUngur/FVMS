package connection.tasks;

import java.util.HashMap;

import utils.Logger;

import com.rabbitmq.client.QueueingConsumer;

public abstract class NoticeTask extends Task {

	public NoticeTask(QueueingConsumer loginQ) {
		super(loginQ);
	}

	@Override
	public void run() {
		HashMap<String, Object> request = null;
		while (true) {
			QueueingConsumer.Delivery delivery;
			try {
				delivery = getQueue().nextDelivery();
				request = getConn().getMessage(delivery.getBody());
				executeRequest(request);
			} catch (Exception e) {
				Logger.logERROR(e);
				run();
			}
		}
	}

	protected abstract void executeRequest(HashMap<String, Object> request);

}
