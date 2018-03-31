package com.example.demo.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.EstimationFS;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
@RequestMapping("/test")
public class EstimationFSController {
	
	private List<EstimationFS> efsList = new ArrayList<EstimationFS>();
  
    
    @RequestMapping(value= {"/save/estimation"},method = { RequestMethod.POST })
    public void saveItems(@RequestBody(required=false)String reqContent) throws Exception{
    	EstimationFS efs = new EstimationFS(reqContent);
    	efsList.add(efs);
   
	}
     @RequestMapping(value= {"/estimation/{wipno}"}, method = { RequestMethod.GET })
    public Map<String,Object> getItems(@PathVariable String wipno) throws Exception {
    	 Map<String,Object> rsItem = new HashMap<String,Object>();
    	 int efsSize = efsList.size();
    	 for(int i=0;i<efsSize;i++) {
    		 EstimationFS efs = efsList.get(i);
    		 String wipValue = efs.getItemValue("WipNo");
    		 if(wipno.trim().equals(wipValue.trim())) {
    			 rsItem = efs.getItemMap();
    			 return rsItem;
    		}
    		 continue;
    	 }
    	return rsItem;
    }
}
