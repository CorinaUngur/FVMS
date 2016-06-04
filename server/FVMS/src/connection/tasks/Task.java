package connection.tasks;

import com.rabbitmq.client.QueueingConsumer;

import connection.Connector;

public abstract class Task implements Runnable {
	private QueueingConsumer loginQ = null;

	public QueueingConsumer getLoginQ() {
		return loginQ;
	}

	public Connector getConn() {
		return conn;
	}

	private Connector conn = null;

	public Task(QueueingConsumer loginQ, Connector conn) {
		this.loginQ = loginQ;
		this.conn = conn;
	}

}
