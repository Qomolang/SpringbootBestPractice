package com.magnus.service.org;

import com.magnus.domain.org.model.Org;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  BizService类
 * </p>
 *
 * @author gs
 * @since 2022-09-08
 */
@Service
public class OrgBizService {

    @Resource
    private OrgWriteService writeService;
    @Resource
    private OrgReadService readService;

    /**
     * 保存 
     *
     * @param domain
     * @return
    */
    public Org createOrg(Org domain){
        //todo 补充校验
         return writeService.createOrg(domain);
    };

}
