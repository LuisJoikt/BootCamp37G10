package com.nttdata.bootcamp.msaccounts.dto;

import java.util.ArrayList;
import java.util.List;


import lombok.Getter;
import lombok.Setter;


    
@Getter
@Setter

public class CreditListDTO {

    private List<CreditDTO> creditodtotrs;


    public CreditListDTO(){
        creditodtotrs = new ArrayList<CreditDTO>();  
    }


}
