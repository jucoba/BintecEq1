(function() {
    'use strict';
    angular
        .module('bintec.core')
        .factory('ref', ref);
    ref.$inject = ['CONFIG'];
    /* @ngInject */
    function ref(CONFIG) {
    	return firebase.initializeApp(CONFIG);
    }
})();