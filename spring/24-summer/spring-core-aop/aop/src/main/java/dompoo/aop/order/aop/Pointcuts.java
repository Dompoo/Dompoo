package dompoo.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    //dompoo.aop.order 패키지와 그 하위 패키지
    @Pointcut("execution(* dompoo.aop.order..*(..))")
    public void allOrder() {}

    //클래스명이 *Service 패턴
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {}

    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}
}
