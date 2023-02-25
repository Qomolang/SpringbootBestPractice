package com.magnus.service.dept;

import com.magnus.domain.dept.model.Dept;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  BizService类
 * </p>
 *
 * @author gs
 * @since 2023-02-25
 */
@Service
public class DeptBizService {

    @Resource
    private DeptWriteService writeService;
    @Resource
    private DeptReadService readService;

    /**
     * 保存 
     *
     * @param domain
     * @return
     */
    public Dept createDept(Dept domain){
        //todo 补充校验
         return writeService.createDept(domain);
    };

}
