var express        = require('express');  
var app            = express();  
var http = require("http");
var httpServer = http.createServer(app);  
var five = require("johnny-five");  
var io=require('socket.io')(httpServer);

var port = 3000;

var options = {
  host: "localhost",
  port: 8080,
  path: '/BintecWeb/api/user/pay/',
  method: 'GET',
  headers: { 'Content-Type': 'application/json' }
};



app.use(express.static(__dirname + '/public'));

app.get('/', function(req, res) {  
        res.sendFile(__dirname + '/public/index.html');
});

httpServer.listen(port);  
console.log('Server available at http://localhost:' + port);  
var led;

//Arduino board connection

var board = new five.Board();  
board.on("ready", function() {  
    console.log('Arduino connected');
    servo = new five.Servo(9);
    servo.home();
});

//Socket connection handler
io.on('connection', function (socket) {  
        console.log(socket.id);

        socket.on('servo:180', function (data) {
           servo.to(180);
           board.wait(2000, function() {
              servo.home();
              servo.stop();
            });
           //led.on();
           console.log('Servo a 180');
        });

        socket.on('servo:off', function (data) {
            servo.stop();
            console.log('LED OFF RECEIVED');

        });

        socket.on('checkstudent', function (data) {
            var id = data.carnet;

            options.path=options.path+id;

            http.request(options, function(res) {

                res.on("data", function (data) {
                    var jsonObject = JSON.parse(data);
                    // append this chunk to our growing `data` var
                    
                    if(jsonObject!=null && jsonObject.ResponseMessage!=null){
                        if(jsonObject.ResponseMessage.ResponseHeader.Status.StatusCode=="0"){
                            servo.to(180);
                            board.wait(2000, function() {
                              servo.home();
                              servo.stop();
                            });
                        }
                    }
                });
                
                res.setEncoding('utf8');

              /*console.log('STATUS: ' + res.statusCode);
              console.log('HEADERS: ' + JSON.stringify(res.headers));
              res.setEncoding('utf8');
              res.on('data', function (chunk) {
                console.log('BODY: ' + chunk);
              });*/  
            }).end(); 

            
            console.log(id);

        });

        
    });

console.log('Waiting for connection');
