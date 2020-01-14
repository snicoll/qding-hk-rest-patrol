package com.qding.hk.rest.patrol.bean.response;

import com.qding.hk.rest.common.base.QdingBaseResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author  liaoly
 * @date   2018/9/21  15:17
 * @param: 
 * @return 
 */ 

@Data
@ApiModel(value = "app response",description = "app response")
public class BaseResponse extends QdingBaseResponse {

    private Object data;


}
