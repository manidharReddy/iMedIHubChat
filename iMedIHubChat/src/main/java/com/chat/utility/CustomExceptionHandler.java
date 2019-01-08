package com.chat.utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.chat.bean.ChatterBoxBean;


public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	private static final Logger LOG = Logger.getLogger(CustomExceptionHandler.class);
	private final javax.faces.context.ExceptionHandler wrapped;
	private ChatterBoxBean tabbedViewBean = null;
	
	
	public CustomExceptionHandler(final ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}
	
	@Override
	public javax.faces.context.ExceptionHandler getWrapped() {
		return this.wrapped;
	}
	
	
	@Override
	public void handle() throws FacesException {
		//System.out.println("CustomExceptionHandler.handle()");
		
		final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context =
                    (ExceptionQueuedEventContext) event.getSource();
 
            // get the exception from context
            Throwable t = context.getException();
 
            final FacesContext fc = FacesContext.getCurrentInstance();
            final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
            final NavigationHandler nav = fc.getApplication().getNavigationHandler();
 
            //here you do what ever you want with exception
            try {
            	
            	if( tabbedViewBean == null){
            		FacesContext fcontext = FacesContext.getCurrentInstance();
            		tabbedViewBean = fcontext.getApplication().evaluateExpressionGet(fcontext, "#{chatterBoxBean}", ChatterBoxBean.class);
            	}
            	
                //log error ?
                //LOG.log(Level.FATAL, "Critical Exception!", t);
                LOG.error("Critical Exception!", t);
                
                System.out.println("Critical Exception!!  :"+t.getMessage());
                StringWriter exp = new StringWriter();
                t.printStackTrace(new PrintWriter(exp));
               
                
                
                //sent an mail
               /* if(testInet("google.com")){
                	//Mail.sendMail(exp.toString(), "khajahussain8@gmail.com", "Error Message in "+tabbedViewBean.getLoggedUser().getUser().getCustomer().getCustId()+"\t"+tabbedViewBean.getLoggedUser().getUser().getCustomer().getCustName());
                }*/
                
                t.printStackTrace(System.out);
                
                //redirect error page
                requestMap.put("exceptionMessage", exp.toString());
                nav.handleNavigation(fc, null, "Lims_ExceptionMessage.xhtml");
                fc.renderResponse();
 
                // remove the comment below if you want to report the error in a jsf error message
                //JsfUtil.addErrorMessage(t.getMessage());
 
            } finally {
                //remove it from queue
                i.remove();
            }
        }
        //parent hanle
        getWrapped().handle();
		
		//super.handle();
	}
	
	public static boolean testInet(String site) {
	    Socket sock = new Socket();
	    InetSocketAddress addr = new InetSocketAddress(site,80);
	    try {
	        sock.connect(addr,3000);
	        return true;
	      
	    } catch (IOException e) {
	    	return false;
	    } finally {
	        try {sock.close();}
	        catch (IOException e) {}
	    }
	}

}
