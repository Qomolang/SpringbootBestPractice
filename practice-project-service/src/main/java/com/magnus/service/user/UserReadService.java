package com.magnus.service.user;

import com.magnus.domain.user.model.User;
import com.magnus.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  ReadService类
 * </p>
 *
 * @author gs
 * @since 2022-09-08
 */
@Service
public class UserReadService {

    @Resource
    private UserRepository repository;

    /**
     * 根据 ID 查找 
     * @param id
     * @return
     */
    public User getUserById(Long id){
        //todo 补充校验
        return repository.getById(id);
    };
}
