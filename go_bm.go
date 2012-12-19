package main

import (
	"fmt"
	"labix.org/v2/mgo"
	"time"
)

type Doodaa struct {
	A int
	B int
	C int
	D int
	E int
	F int
}

func main() {
	session, err := mgo.Dial("localhost")
	if err != nil {
		panic(err)
	}
	defer session.Close()

	//        fields := []string{"hello"}
	// Optional. Switch the session to a monotonic behavior.
	session.SetMode(mgo.Monotonic, true)

	c := session.DB("benchmark").C("doodaas")

	var results []Doodaa

	fmt.Println("Starting the query")

	before := time.Now()

	iter := c.Find(nil).Iter()
	err = iter.All(&results)
	if err != nil {
		panic(err)
	}

	aggregate := Doodaa{}

	fmt.Println("Starting to aggregate")
	for _, result := range results {
		aggregate.A += result.A
		aggregate.B += result.B
		aggregate.C += result.C
		aggregate.D += result.D
		aggregate.E += result.E
		aggregate.F += result.F
	}

	fmt.Println(aggregate)
	lasted := time.Now().Sub(before)
	fmt.Println(lasted)
}
