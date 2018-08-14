### 2018-08-14
    finish
    1. 请求保护的框架
    controller中的的方法只要第一个参数是HttpServletRequest request就可以被保护
    具体例子可以看BaseControler的后面两个方法

    2. 权限控制的框架
    我想的是把service大体上分为SystemUserService和NormalUserService，这两个接口的方法的第一个参数都是HttpServletRequest request，我就会对其进行权限控制

    TBD
    用户教育api的实现
