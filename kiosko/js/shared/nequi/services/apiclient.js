'use strict';

/**
 * @ngdoc service
 * @name nequiwebappApp.ApiClient
 * @description
 * # ApiClient
 * Service in the nequiwebappApp.
 * Este servicio encapsula el uso del SDK de JS para consumir el api de nequi
 */
angular.module('bintec.core')
  .service('ApiClient',[ '$q', function ($q) {

        //Variables iniciales
    	var k3y = 'xrQ6q19y5n3ToPQxYf5za6xZmaBkICfN5uPbcR9j';
        var publicKey = '';
    	var apiClient;
    	var defaultHeaders = {};

        //Metodo privado que retorna el cliente del api o lo inicializa en caso que aun no se haya usado
        var getApiClient = function(){
            if(!!!apiClient){
                apiClient = apigClientFactory.newClient({
                        apiKey: k3y,
                        accessKey: 'AKIAISR34JAZBQFU4ARQ',
                        secretKey: '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'
                });
            }
            return apiClient;
        };

      //Metodo publico que retorna una promesa para el servicio de validate client
      this.validateClient = function(phoneNumber, value){
        if(!!phoneNumber && value !== ''){
          return consumeApiMethod('servicesClientserviceValidateclientPost', getBodyValidateClient(phoneNumber, value));          
        }
      };

       //Metodo publico que retorna una promesa para el servicio de cash in
    	this.cashIn = function(phoneNumber, mount){
    		if(!!phoneNumber && !!mount){
    			return consumeApiMethod('servicesCashinserviceCashinPost', getBodyCashIn(phoneNumber, mount));			    
    		}
    	};

       //Metodo publico que retorna una promesa para el servicio de generacion de QR
    	this.generateQR = function(phoneNumber, value){    		
    		if(!!phoneNumber && !!value){
    			return consumeApiMethod('servicesPaymentserviceGeneratecodeqrPost', getBodyGenerateQR(phoneNumber, value));			    
    		}
    	};

        //Metodo publico que retorna una promesa para el servicio de validacion de estado de pago
    	this.getStatusPayment = function(phoneNumber, codeQR){    		
    		if(!!phoneNumber && !!codeQR){
    			return consumeApiMethod('servicesPaymentserviceGetstatuspaymentPost', getBodyGetStatusPayment(phoneNumber, codeQR));			    
    		}
    	};

        //Metodo publico que retorna una promesa para el servicio de cash out
        this.cashOut = function(phoneNumber, value, reference, token){
            if(!!phoneNumber && !!value && !!token){
            	if(!!publicKey){
                	return consumeApiMethod('servicesCashoutserviceCashoutPost', getBodyCashOut(phoneNumber, value, reference, token));
            	}else{
            		var deferred = $q.defer();
		            consumeApiMethod('servicesKeysserviceGetpublicPost').then(function(result){
		            	if(!!result && !!result.data){
		            		publicKey = result.data;
		            		consumeApiMethod('servicesCashoutserviceCashoutPost', getBodyCashOut(phoneNumber, value, reference, token)).then(
		            			function(data){
		            				deferred.resolve(data);
		            			}, function(error){
		            				deferred.reject(error);
		            			});
		            	}else{
		            		deferred.reject('Llave publica no retornada');
		            	}
		            }, function(error){
		            	deferred.reject(error);
		            })
		            return deferred.promise; 
            	}
            }
        };

        //Metodo publico que retorna una promesa para el servicio de cash out
        this.cashOutConsult = function(phoneNumber){
            if(!!phoneNumber){                
                return consumeApiMethod('servicesCashoutserviceCashoutconsultPost', getBodyCashOutConsult(phoneNumber));              
            }
        };

        /*
         Metodo privado que inicializa una promesa y la resuelve o rechaza 
         segun el resultado de consumir un metodo, funcion o servicio del api
        */
        var consumeApiMethod = function(method, body){
            var deferred = $q.defer();
            if(!!method && !!getApiClient()[method]){
                getApiClient()[method](defaultHeaders, body)
                .then(function(result){
                    deferred.resolve(result);
                }).catch( function(error){                  
                    deferred.reject(error);
                });
            }else{
                 deferred.reject();
            }
            return deferred.promise;
        };

        //Metodo publico que extrae a partir de un response el codigo QR segun la estructura JSON de la respuesta del servicio
    	this.getCodeQR = function(response){
    		var codeQR;
    		if(validateExistAny(response) && !!response.data.ResponseMessage.ResponseBody.any.generateCodeQRRS &&
    			!!response.data.ResponseMessage.ResponseBody.any.generateCodeQRRS.codeQR){
    			codeQR = response.data.ResponseMessage.ResponseBody.any.generateCodeQRRS.codeQR;
    		}
    		return codeQR;
    	};

        //Metodo publico que genera la cadena con la que se puede crear una imagen de codigo QR
    	this.getStringQR = function(codeQR){
    		return 'bancadigital-' + codeQR;
    	};

        //Metodo publico que verifica segun la respuesta del servicio de validacion de pago, si el estado del pago es de listo y retorna booleano
    	this.isPaymentComplete = function(response){
    		var result = false;
    		if(validateExistAny(response) && !!response.data.ResponseMessage.ResponseBody.any.getStatusPaymentRS &&
    			!!response.data.ResponseMessage.ResponseBody.any.getStatusPaymentRS.status){
    			var status = response.data.ResponseMessage.ResponseBody.any.getStatusPaymentRS.status;
    			if(status === '35'){
    				result = true;
    			}
    		}
    		return result;
    	};

        var validateExistAny = function(response){
            return !!response.data && !!response.data.ResponseMessage && 
                !!response.data.ResponseMessage && !!response.data.ResponseMessage.ResponseBody &&
                !!response.data.ResponseMessage.ResponseBody.any;
        }

    	//Metodo privado que genera el cuerpo para una peticion de cash in
    	var getBodyCashIn = function(phoneNumber, mount){
    	var messageId = new Date().getTime().toString();
			return {
			   "RequestMessage": {
			       "RequestHeader": {
			           "Channel": "MF-001",                                   
			           "RequestDate": "015-12-07",                         
			           "MessageID": messageId.substring(messageId.length-9),                                               
			           "ClientID": "3004229986",
		                "Destination": {
                               "ServiceName": "CashInService",
                               "ServiceOperation": "cashIn",
                               "ServiceRegion": "C001",
                               "ServiceVersion": "1.0.0"
                          }
			       }, 
			       "RequestBody": {
			           "any": {
			               "cashInRQ": {
	                                       "phoneNumber": phoneNumber.toString(),              
	                                       "code": "1",                                       
	                                       "value": mount.toString()
                                       }
			           }
			       }
			   }
			};

    	};

        //Metodo privado que genera el cuerpo para una peticion de generacion de QR
    	var getBodyGenerateQR = function(phoneNumber, value){
    		var messageId = new Date().getTime().toString();
			return {
				   "RequestMessage": {
				       "RequestHeader": {
				           "Channel": "MF-001",                                       
				           "RequestDate": new Date().toJSON(), 
				           "MessageID": messageId.substring(messageId.length-9), 
				           "ClientID": phoneNumber.toString(),
                    "Destination": {
                                   "ServiceName": "PaymentsService", 
                                   "ServiceOperation": "generateCodeQR", 
                                   "ServiceRegion": "C001", 
                                   "ServiceVersion": "1.0.0" 
                    }

				       }, 
				       "RequestBody": {
				           "any": {
				               "generateCodeQRRQ": {
				                   "phoneNumber": phoneNumber.toString(), 
				                   "value": value.toString()
				               }
				           }
				       }
				   }
			};
    	};

        //Metodo privado que genera el cuerpo para una peticion de validacion de pago
    	var getBodyGetStatusPayment = function(phoneNumber, codeQR){
    		var messageId = new Date().getTime().toString();
			return {
			   "RequestMessage": {
			       "RequestHeader": {
			           "Channel": "MF-001", 
			           "RequestDate": new Date().toJSON(), 
			           "MessageID": messageId.substring(messageId.length-9), 
			           "ClientID": phoneNumber.toString(),
                 "Destination": {
                  "ServiceName": "PaymentsService", 
                  "ServiceOperation": "getStatusPayment", 
                  "ServiceRegion": "C001", 
                  "ServiceVersion": "1.0.0" 
              }
			       }, 
			       "RequestBody": {
			           "any": {
			               "getStatusPaymentRQ": {
	                                               "codeQR": codeQR
	                                             }
			           }
			       }
			   }
			};
    	};

        var getBodyCashOut = function(phoneNumber, value, reference, token){
            var tokenEncrypted = NequiEncrypter.encryptRSA(token, publicKey);
            console.log(tokenEncrypted,token, publicKey);
            var messageId = new Date().getTime().toString();
            return {
              "RequestMessage": {
                "RequestHeader": {
                  "Channel": "MF-001",
                  "RequestDate": new Date().toJSON(),
                  "MessageID": messageId.substring(messageId.length-9),
                  "ClientID": phoneNumber.toString(),
                  "Destination": {
                      "ServiceName": "CashOutServices",
                      "ServiceOperation": "cashOut",
                      "ServiceRegion": "C001",
                      "ServiceVersion": "1.0.0"
                	}
                },
                "RequestBody": {
                  "any": {
                    "cashOutRQ": {
                      "phoneNumber": phoneNumber.toString(),
                      "token": tokenEncrypted,
                      "code": "1",
                      "reference": reference,
                      "value": value.toString(),
                      "externalData": {
                        "tranPart": messageId.substring(messageId.length-9),
                        "idTerminal": "123"
                      }
                    }
                  }
                }
              }
            }
        };  

        var getBodyCashOutConsult = function(phoneNumber){
        	var messageId = new Date().getTime().toString();
            return {
              "RequestMessage": {
                "RequestHeader": {
                  "Channel": "MF-001",
                  "RequestDate": new Date().toJSON(),
                  "MessageID": messageId.substring(messageId.length-9),
                  "ClientID": phoneNumber.toString(),
                  "Destination": {
                      "ServiceName": "CashOutServices",
                      "ServiceOperation": "cashOutConsult",
                      "ServiceRegion": "C001",
                      "ServiceVersion": "1.0.0"
                	}
                },
                "RequestBody": {
                  "any": {
                    "cashOutConsultRQ": {
                      "phoneNumber": phoneNumber.toString()                    
                    }
                  }
                }
              }
            }
        };  

        var getBodyValidateClient = function(phoneNumber, value){
        	var messageId = new Date().getTime().toString();
            return {
              "RequestMessage": {
                "RequestHeader": {
                  "Channel": "MF-001",
                  "RequestDate": new Date().toJSON(),
                  "MessageID": messageId.substring(messageId.length-9),
                  "ClientID": phoneNumber.toString(),
                  "Address": {
                    "DeviceAddress": "1.1.1.1",
                    "NetworkAddress": "1.1.1.1"
                  },
                  "Destination": {
                    "ServiceName": "RechargeService",
                    "ServiceOperation": "validateClient",
                    "ServiceRegion": "C001",
                    "ServiceVersion": "1.0.0"
                  }
                },
                "RequestBody": {
                  "any": {
                    "validateClientRQ": {
                      "phoneNumber": phoneNumber.toString(),
                      "value": value.toString()
                    }
                  }
                }
              }
            }
        };    

  }]);
