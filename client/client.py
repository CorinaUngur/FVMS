import pika
import json
import uuid

class Client(object):

	logged_in = False

	def __init__(self):
		self.connection = pika.BlockingConnection(pika.ConnectionParameters("localhost"))
		self.channel = self.connection.channel()

 		self.callback_queue_reg = self.channel.queue_declare(exclusive=True).method.queue
		self.channel.queue_declare(queue='login', durable=False)
		self.channel.basic_consume(self.on_register_response, no_ack=True, queue=self.callback_queue_reg)


	def login(self, user_name, password):
		self.response = None

		credentials = (user_name, password)

		corr_id = str(uuid.uuid4())
		self.channel.basic_publish(exchange='',
									routing_key='login',
									properties=pika.BasicProperties(
									reply_to = self.callback_queue_reg,
									correlation_id = corr_id,
									content_type="application/json",
									delivery_mode = 1
									),
									body=json.dumps(credentials))

		while self.response is None:
			self.connection.process_data_events()

		if self.response == "OK":
			self.logged_in = True;
			self.user_name = user_name

		print "end of registration"

	def is_logged_in(self):
		if logged_in == True:
			print "User: " + self.user_name
		else:
			print "Not logged in"

	def on_register_response(self):
		print "logged in"
