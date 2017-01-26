==Stress test

Run Application and test the following.
A spring data jpa resository can easily manage thousands of concurrent requests
http://localhost:8080/stress-jpa?threadsCount=2000

On the other hand
 hibernate sessionfactory becomes unresponsive when the size of the concurrent requests are more than the maximumPoolSize which is 10.
http://localhost:8080/stress-sessionfactory?threadsCount=5  <- OK

Restart the application and test
http://localhost:8080/stress-sessionfactory?threadsCount=50 <-
2017-01-26 18:36:07.782 DEBUG 43324 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=50)

2017-01-26 18:36:11.902  WARN 43324 --- [      Thread-45] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 0, SQLState: null
2017-01-26 18:36:11.902 ERROR 43324 --- [      Thread-45] o.h.engine.jdbc.spi.SqlExceptionHelper   : HikariPool-1 - Connection is not available, request timed out after 30004ms.
	at org.springframework.orm.jpa.JpaTransactionManager.doBegin(JpaTransactionManager.java:431)
	at org.springframework.transaction.support.AbstractPlatformTransactionManager.getTransaction(AbstractPlatformTransactionManager.java:373)
	at org.springframework.transaction.interceptor.TransactionAspectSupport.createTransactionIfNecessary(TransactionAspectSupport.java:447)
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:277)
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:96)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179)
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:656)
	at com.example.dao.TypeDAOHibernate$$EnhancerBySpringCGLIB$$da09a027.findAll(<generated>)
	at com.example.controller.StressController.lambda$stressTest$0(StressController.java:36)
	at java.lang.Thread.run(Thread.java:745)


