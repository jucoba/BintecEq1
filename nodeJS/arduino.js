



  five = require("johnny-five");
  board = new five.Board();
  var servo;


  board.on("ready", function() {
    servo = new five.Servo(9);
    console.log("Arduino ready");

    this.on("exit", function() {
      console.log("Board exiting");
      servo.stop();
    });

    servo.home();
      servo.to(180);
      board.wait(2000, function() {
          servo.home();
          servo.stop();
        });
  });

  

