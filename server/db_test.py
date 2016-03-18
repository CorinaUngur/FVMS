import db
import unittest

class DB_TestCase(unittest.TestCase):

	tables = ['users', 'teams', 'team_users', 'team_leaders']

	def setUp(self):
		config = {'user' : 'root',
			'password' : 'parola',
			'host' : '127.0.0.1',
			'database' : 'fvms'}
		self.cnx = mysql.connector.connect(**config)
		self.cursor = self.cnx.cursor()

		for table in tables:
			query="DELETE from ".table
			self.cursor.execute(query);
			print "deleted all from "+table
		self.d = DB_Connection();

		print "DB_Test: set up complete"

	def tearDown(self):
		print "DB_Test: test case complete"

	def insert_user_test(self):
		#d.insert_user("user1", "password1", "email1@email.c")
		print "just a test"
		assertEqual(true, true)

unittest.main()

