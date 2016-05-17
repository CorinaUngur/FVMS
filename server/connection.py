import pika
import db
import json
import config

#queues: login for login

class Controller(object):
	def __init__(self, host='localhost'):
		connection = pika.BlockingConnection(pika.ConnectionParameters(
		host=config.broker_ip))

		self.channel = connection.channel()
		self.channel.queue_declare(queue=config.QLOGIN, durable=False)

		self.db_connection = db.DB_Connection()

	def on_receive_credentials(self, ch, method, props, body):
		print "unaltered body: " + body
		user = json.loads(json.loads(body))
		print user[config.username]
		authorized = self.db_connection.check_password(user[config.username], user[config.password])
		ch.basic_ack(delivery_tag = method.delivery_tag)
		if(authorized):
			self.send_message(config.QLOGIN, "authorized", props)
		else:
			self.send_message(config.QLOGIN, "not authorized", props)

	def test_method(self, ch, method, props, body):
		print body
	
	def send_message(self, queue, message, props):
		self.channel.basic_publish(exchange='',
							routing_key=props.reply_to,
							properties=pika.BasicProperties(correlation_id = \
													props.correlation_id,
													delivery_mode = 1),
							body=json.dumps(message))	


	def start(self):
		"""starts the consuming"""
		self.channel.basic_consume(self.on_receive_credentials, queue=config.QLOGIN)

		print " [x] Awaiting for user and password"

		self.channel.start_consuming()
