package com.test.project.api.schema;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ItemResultVo extends ResultVo {
  private Map<String,Object> item; 
  private Map<String,Object> subItem; 
}
