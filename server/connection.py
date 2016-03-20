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
		user = json.loads(body)
		self.db_connection.check_password(user[config.username], user[config.password])
		ch.basic_ack(delivery_tag = method.delivery_tag)

		


	def start(self):
		"""starts the consuming"""
		self.channel.basic_consume(self.on_receive_credentials, queue=config.QLOGIN)

		print " [x] Awaiting for user and password"

		self.channel.start_consuming()
