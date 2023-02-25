package com.magnus.service.dept;

import com.magnus.domain.dept.model.Dept;
import com.magnus.domain.dept.repository.DeptRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  WriteService类
 * </p>
 *
 * @author gs
 * @since 2023-02-25
 */
@Service
public class DeptWriteService {

    @Resource
    private DeptRepository repository;

    /**
     * 保存 
     *
     * @param domain
     * @return
     */
    public Dept createDept(Dept domain){
        //todo 补充校验
         return repository.create(domain);
    };

    /**
     * 根据 ID 删除某个 
     *
     * @param id
     * @return 删除是否成功
     */
    public boolean deleteDeptById(Long id){
        //todo 补充校验
        return repository.deleteById(id);
    };
}
