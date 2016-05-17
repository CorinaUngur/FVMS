import mysql.connector
import re
import hashlib

print "insert_user(user, password, email) \n close_connection() \n check_password(user, password)"

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
			hasher = hashlib.md5()
			hasher.update(password)
			password = hasher.hexdigest()
			
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
		result = False
		hasher = hashlib.md5()
		hasher.update(c_password)
		actual_password = hasher.hexdigest()

		query = ("select pass as password from users where user_name=\'{}\'".format(c_user))
		self.cursor.execute(query)
		
		for password in self.cursor:
			if password[0] == actual_password:
				print 'authorized'
				result = True
			else:
				print "not authorized"
				print password
				print actual_password
		return result

d=DB_Connection()

#d.insert_user("user1", "password1", "email1@email.co")

d.check_password("user1", "password1")

d.check_password("user1", "wrong_password")
