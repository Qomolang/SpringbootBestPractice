package com.magnus.service.user;

import com.magnus.domain.user.model.User;
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
public class UserBizService {

    @Resource
    private UserWriteService writeService;
    @Resource
    private UserReadService readService;

    /**
     * 保存 
     *
     * @param domain
     * @return
    */
    public User createUser(User domain){
        //todo 补充校验
         return writeService.createUser(domain);
    };

}
