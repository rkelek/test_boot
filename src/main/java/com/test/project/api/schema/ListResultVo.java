package com.test.project.api.schema;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ListResultVo extends ResultVo {
   private List<?> list;
}
