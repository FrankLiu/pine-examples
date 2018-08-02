package io.pine.examples.loanapp.caching;

import io.pine.examples.loanapp.domain.ProductRate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;

@Aspect
public abstract class BaseCacheAspect {
	
	private Log log = LogFactory.getLog(BaseCacheAspect.class);
	
	@Around("methodAroundGetProductRates()")
	public List<ProductRate> doAroundGetProductRates(ProceedingJoinPoint pjp) {
		
		log.debug("doAroundGetProductRates() called.");
		
		// Initialize the cache if needed
		init();
		
		// Check if the product rate list is already in cache
        List<ProductRate> productRateList = null;
        
        // First attempt to load from cache
        String cacheKey = "ProductRates";
        productRateList = (List<ProductRate>)get(cacheKey);
        
        if (productRateList == null) {
  	      	log.debug("Data not found in cache.");
  	      	try {
  	      		productRateList = (List<ProductRate>)pjp.proceed();
  	      	} catch (Throwable t) {
  	      		log.error(t);
  	      	}
  	      	put(cacheKey, productRateList);
        } else {
        	log.debug("Data found in cache.");
        }
        return productRateList;
	}
	
	@Pointcut("execution(public * com.ideature.ProductRate.findProductRates(..))")
    public void methodAroundGetProductRates(){}
	
	public abstract void init();
	public abstract void cleanup();
	public abstract Object get(String cacheKey);
	public abstract void put(String cacheKey, Object cacheObj);

}
