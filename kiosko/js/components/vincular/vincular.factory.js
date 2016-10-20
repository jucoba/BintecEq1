(function() {
    //'use strict';//Por safari
    angular
        .module('bintec.core')
        .factory('vincularfactory', vincularfactory);
    vincularfactory.$inject = ['crudfactory', '$firebaseArray'];
    /* @ngInject */
    function vincularfactory(crudfactory, $firebaseArray) {
        var service = {
            save: save,
            findByCarnet: findByCarnet
        };
        return service;

        function save(student) {
          var model=crudfactory.getModel("students");
          return crudfactory.updateToModel(model,student).then(saveSucess).catch(saveFail);

          function saveSucess(data){
            return data;
          }

          function saveFail(error){
            return error;
          }
        }

        function findByCarnet(carnet) {
          var model=crudfactory.getModel("students");
          return crudfactory.findBy(model, "id_carnet", carnet).then(onceValueComplete).catch(onceValueFail);

          function onceValueComplete(dataSnapshot){
            if(dataSnapshot==null){
              return null;
            }
            if(dataSnapshot.length==0){
              return dataSnapshot;
            }else{
              return dataSnapshot;  
            }
          }

          function onceValueFail(fail){
            console.log(fail);
            return null;
          }
        }

        
    }
})();