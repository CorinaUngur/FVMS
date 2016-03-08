import mysql.connector
import re
import hashlib


class DB_Connection(object):

	def __init__(self):
		config = {'user' : 'root',
			'password' : 'parola',
			'host' : '127.0.0.1',
			'database' : 'fvms'}
		self.cnx = mysql.connector.connect(**config)
		self.cursor = self.cnx.cursor()

	def insert_user(self, user, password, email):
		user_ok = self.check_user(user)
		email_ok = self.check_email(email)

		if user_ok==0 and email_ok==0:
			query = ("INSERT INTO users "
               "(email, user_name, pass) "
               "VALUES (%s, %s, %s)")
			h = hashlib.md5()
			h.update(password)
			password = h.hexdigest()
			
			data = (email, user, password)
			self.cursor.execute(query, data)
			self.cnx.commit()
		else:
			print 'username already exists'
		
	def close_connection(self):
		self.cursor.close()
		self.cnx.close()

	def check_user(self, user):
		if user == '':
			return 1

		query = ("SELECT user_name FROM users where user_name = \'{}\'".format(user))
		self.cursor.execute(query)
	
		i = 0
		for (user_name) in self.cursor:
			print user_name
			i=i+1
		return i

	def check_email(self, email):
		if email == '':
			return 1;
		r = re.compile('.*@.*\..*');
		if r.match(email) is None:
			print 'the email is not in the correct format'
			return 1
		return 0

	def check_password(self, c_user, c_password):
		h = hashlib.md5()
		h.update(c_password)
		current_password = h.hexdigest()

		query = ("select pass as password, user from users where user_name=\'{}\'".format(c_user))
		self.cursor.execute(query)
		
		for (password, user) in self.cursor:
			if password == current_password and user == c_user:
				print 'authorized'
			else:
				print "not authorized"
				print password
				print current_password

d=DB_Connection()
user = "cici3"
d.insert_user(user, "parolalui", "email@e.c")

d.check_password(user,"parolalui")

d.check_password(user,"parola")

