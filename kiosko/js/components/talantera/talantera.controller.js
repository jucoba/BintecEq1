(function(){
  angular
    .module('bintec.core').controller('TalanteraController', TalanteraController);

  TalanteraController.$inject = ['mySocket'];

  function TalanteraController(mySocket) {
    var tc = this;
    tc.servo180=servo180;
    tc.servoOff=servoOff;
    tc.salida=salida;
    
    //Variables Iniciales
    tc.title = 'Talantera';


    function servo180 () {

        mySocket.emit('servo:180');
        console.log('Servo 180');
    };


    function servoOff () {

      mySocket.emit('servo:off');
      console.log('Servo OFF');  
    };

    function salida (id_carnet) {
      mySocket.emit('checkstudent', { carnet: id_carnet});
      console.log(id_carnet);
      //mySocket.emit('servo:off');
    };

    
    
    }

})();
