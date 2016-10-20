(function(){
  angular
    .module('bintec.core').controller('VincularController', VincularController);

  VincularController.$inject = ['ApiClient', 'vincularfactory'];

  function VincularController(ApiClient, vincularfactory) {
    var vc = this;
    //Variables Iniciales
    vc.title = 'Quiki';
    vc.vincular=vincular;
    vc.showError=false;
    vc.showSucess=false;
    vc.messageAlert="";

    function vincular(student){
      vc.showError=false;
      vc.showSucess=false;
      vc.messageAlert="";
      vincularfactory.findByCarnet(student.id_carnet).then(function(data){
        if(data && data.length>1){
          ApiClient.validateClient(student.cellphone, 0).then(function(result){
             if(!!result.data && !!result.data.ResponseMessage && !!result.data.ResponseMessage &&
                  !!result.data.ResponseMessage.ResponseHeader && !!result.data.ResponseMessage.ResponseHeader.Status &&
                  result.data.ResponseMessage.ResponseHeader.Status.StatusCode == '0'){
                
                data.$update(data[0].$id, student);
                vc.showSucess=true;
                vc.messageAlert="Perfecto, ahora eres un Kuiqi millennial";
              }else{
                vc.showError=true;
                vc.messageAlert="Debes tener Nequi, instalala";
              }
          }).catch(function(){
            vc.showError=true;
            vc.messageAlert="Opps nequi";
          })
        }else{
          //vc.showError=true;
          //vc.messageAlert="Lo sentimos no eres millennials";
          ApiClient.validateClient(student.cellphone, 0).then(function(result){
             if(!!result.data && !!result.data.ResponseMessage && !!result.data.ResponseMessage &&
                  !!result.data.ResponseMessage.ResponseHeader && !!result.data.ResponseMessage.ResponseHeader.Status &&
                  result.data.ResponseMessage.ResponseHeader.Status.StatusCode == '0'){
                
                data.$add(student);
                vc.showSucess=true;
                vc.messageAlert="Perfecto, ahora eres un Kuiqi millennial";
              }else{
                vc.showError=true;
                vc.messageAlert="Debes tener Nequi, instalala";
              }
          }).catch(function(){
            vc.showError=true;
            vc.messageAlert="Opps nequi";
          })
        }
      }).catch(function(error){
        vc.showError=true;
        vc.messageAlert="Ocurrio un problema";
      });
    }

}


})();
