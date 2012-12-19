require 'mongo'

client = Mongo::Connection.new
db = client['benchmark']
coll = db['doodaas']

fields = ['a','b','c','d','e','f']

aggregate = {}
fields.each{|f| aggregate[f] = 0}

start = Time.now

puts "start!"
coll.find({}).each do |row|
	fields.each do |field|
		aggregate[field] += row[field]
	end
end
finish = Time.now - start
p finish
puts "done!"