Overview
====
Timer expose APIs for creating and monitoring scheduled tasks.
Currently supporting URL execution task - execute a given POST URL at a given delay

Use
----
Build application by executing:

./gradlew build

Run application by executing:

./gradlew bootRun

Create a new timer - run POST http://localhost:8081/timers API with the following JSON body:

{
"type" : "UrlTimerRequest",
"hours": <>,
"minutes": <>,
"seconds": <>,
"url": "<>"
}

For example: 

curl --header "Content-Type: application/json" --request POST --data '{"hours": 0, "minutes": 0,"seconds": 1,"url": "http://172.16.10.131:8081/timers", "type" : "UrlTimerRequest"}' http://localhost:8081/timers

Should get the created timer ID as a response. For example: {"id":"59"}

Now you can monitor how much time left for created task execution by executing:

curl --header "Content-Type: application/json" --request GET  http://localhost:8081/timers/{id}


