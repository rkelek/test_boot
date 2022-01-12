package com.test.project.api.schema;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataResultVo extends ResultVo {
   private Object data;

}
