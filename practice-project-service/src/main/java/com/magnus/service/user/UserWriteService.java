package com.magnus.service.user;

import com.magnus.domain.user.model.User;
import com.magnus.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  WriteService类
 * </p>
 *
 * @author gs
 * @since 2022-09-08
 */
@Service
public class UserWriteService {

    @Resource
    private UserRepository repository;

    /**
     * 保存 
     *
     * @param domain
     * @return
    */
    public User createUser(User domain){
        //todo 补充校验
         return repository.create(domain);
    };

    /**
     * 根据 ID 删除某个 
     *
     * @param id
     * @return 删除是否成功
     */
    public boolean deleteUserById(Long id){
        //todo 补充校验
        return repository.deleteLogicallyById(id);
    };
}
