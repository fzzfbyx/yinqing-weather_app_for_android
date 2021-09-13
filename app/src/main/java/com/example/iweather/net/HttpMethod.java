package com.example.iweather.net;


/*
* 用来统一管理请求方法的类型，可有可无但是本着类多代码少的原则，
* 类的数量多，类中代码少，这样整体的架构将会更加清晰，代码看着也更加整洁
* */
public enum HttpMethod {
    GET,
    POST,
    POST_RAW,
    PUT,
    PUT_RAW,
    DELETE,
    UPLOAD
}
