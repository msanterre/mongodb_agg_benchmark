#include <cstdlib>
#include <iostream>
#include <time.h>
#include "mongo/client/dbclient.h"

using namespace mongo;

char* fields[6] = {"a","b","c","d","e","f"};
std::map<string, int> aggregate;
time_t start;
long duration;

void init(){
	for(int i = 0; i < 6; i++){
		aggregate[fields[i]] = 0;
	}
}
void run() {
  mongo::DBClientConnection c;
  c.connect("localhost");
  
  auto_ptr<DBClientCursor> cursor = c.query("benchmark.doodaas", BSONObj());

  while (cursor->more())
  {
      BSONObj p = cursor->next();
      for(int i = 0; i < 6; i++){
      	aggregate[fields[i]] += p.getIntField(fields[i]);
      }
  }
}
void print(){
	for(int i = 0; i < 6; i++){
		std::cout << fields[i] << ": " << aggregate[fields[i]] << std::endl;
	}
}

int main() {
  try {
  	init();
  	start = time(NULL);
    run();
    duration = time(NULL) - start;
    print();

    std::cout << "It took " << duration << "s to run." << std::endl;
  } catch( const mongo::DBException &e ) {
    std::cout << "caught " << e.what() << std::endl;
  }
  return EXIT_SUCCESS;
}