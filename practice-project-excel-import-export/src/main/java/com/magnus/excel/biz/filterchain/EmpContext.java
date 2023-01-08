package com.magnus.excel.biz.filterchain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.ByteArrayInputStream;

/**
 * @author gaosong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpContext {

    private ByteArrayInputStream fileStream;

}
