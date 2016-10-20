(function() {
    'use strict';
    angular
        .module('bintec.core', ['ui.router', 'firebase']).config(config)
        .constant("CONFIG", {
          "apiKey":"AIzaSyDyqrVMuSYwpZeTl87drBouwjM_Dc7dISo",
          "authDomain": "bintec-e5dd4.firebaseapp.com",
          "databaseURL": "https://bintec-e5dd4.firebaseio.com",
          "storageBucket": "bintec-e5dd4.appspot.com",
          "messagingSenderId": "89671576509"
        });

config.$inject = ['$stateProvider','$urlRouterProvider'];

function config($stateProvider,$urlRouterProvider) {
  $stateProvider
      .state('vincular', {
        url: '/vincular',
        templateUrl: 'templates/vincular.html',
        controller: 'VincularController',
        controllerAs: 'vc'
      });

  $urlRouterProvider.otherwise('/vincular');
}

})();