import pika
import db
import json

class Controller(object):
	def __init__(self, host='localhost'):
		connection = pika.BlockingConnection(pika.ConnectionParameters(
		host='localhost'))

		self.channel = connection.channel()
		self.channel.queue_declare(queue='login', durable=False)

		self.db_connection = db.DB_Connection()

	def on_receive_credentials(self, ch, method, props, body):
		user = json.loads(body)
		db_connection.check_password(user["username"], user["password"])

		


	def start(self):
		"""starts the consuming"""
		self.channel.basic_consume(self.on_receive_credentials, queue='login')

		print " [x] Awaiting for user and password"

		self.channel.start_consuming()
