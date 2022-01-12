package com.test.project.api.schema;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TokenResultVo extends ResultVo {
   private String accessToken;
   private Map<String,Object> info;
}
