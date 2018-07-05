package com.leiholmes.androidipcdevelop.aidl;

interface ISecurityCenter {
    String encrypt(String content);
    String decrypy(String password);
}
