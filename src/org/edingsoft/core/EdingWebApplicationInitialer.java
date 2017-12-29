package org.edingsoft.core;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;



public class EdingWebApplicationInitialer extends AbstractAnnotationConfigDispatcherServletInitializer
{

	@Override
	
	  protected Class<?>[] getRootConfigClasses() {
	        return new Class[] { Rootconfig.class };
	    }

	@Override
	protected Class<?>[] getServletConfigClasses() {
		 return new Class[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		 return new String[] { "/" };
	}
		
}
