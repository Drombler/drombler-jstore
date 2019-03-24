package org.drombler.jstore.web.filter;

import org.drombler.jstore.model.RequestInfo;

public interface RequestInfoSupportService {
   default RequestInfo createRequestInfo(){
       return new RequestInfo();
   }
}
