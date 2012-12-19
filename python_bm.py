import pymongo
import math
import time

conn = pymongo.Connection()
db = conn['benchmark']
coll = db['doodaas']

fields = ['a','b','c','d','e','f']

aggregate = {}

for field in fields:
	aggregate[field] = 0

print "Starting"
start = time.time()

for i in coll.find():
	for field in fields:
		aggregate[field] += i[field]


print "Done"
print "Results: "
print time.time() - start
print aggregate