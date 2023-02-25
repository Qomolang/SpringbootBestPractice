package com.magnus.service.dept;

import com.magnus.domain.dept.model.Dept;
import com.magnus.domain.dept.repository.DeptRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  ReadService类
 * </p>
 *
 * @author gs
 * @since 2023-02-25
 */
@Service
public class DeptReadService {

    @Resource
    private DeptRepository repository;

    /**
     * 根据 ID 查找 
     * @param id
     * @return
     */
    public Dept getDeptById(Long id){
        //todo 补充校验
        return repository.getById(id);
    };
}
