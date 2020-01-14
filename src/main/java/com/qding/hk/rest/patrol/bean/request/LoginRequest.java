package com.qding.hk.rest.patrol.bean.request;

import com.qding.hk.rest.common.base.QdingBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author liaoly
 * @date 2018/9/24 0024 上午 12:14
 * @param:
 * @return
 */

@Data
@ApiModel(value = "request parm")
public class LoginRequest extends QdingBaseRequest {

    private String myParam;

    private Integer sourceSystem;

    private int isJson = 1;


}
