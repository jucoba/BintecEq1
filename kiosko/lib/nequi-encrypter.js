var NequiEncrypter = NequiEncrypter || {};
NequiEncrypter.encryptRSA = function(text, publicKey){
        var encrypt = new JSEncrypt();
        encrypt.setPublicKey(publicKey);
        return encrypt.encrypt(text);
};