import pymongo
import math
import time
import random

conn = pymongo.Connection()
db = conn['benchmark']
coll = db['doodaas']

fields = ['a','b','c','d','e','f']
rows = 900000

for i in range(rows):
	doc = {}
	for field in fields:
		doc[field] = random.randint(0,20)

	coll.insert(doc)