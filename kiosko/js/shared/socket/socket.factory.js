(function() {
    //'use strict';//Por safari
    angular
        .module('bintec.core')
        .factory('mySocket', mySocket);
    mySocket.$inject = ['socketFactory'];
    /* @ngInject */
    function mySocket(socketFactory) {
        var myIoSocket = io.connect('http://localhost:3000');

        mySocket = socketFactory({
          ioSocket: myIoSocket
        });

        return mySocket;
    }
})();