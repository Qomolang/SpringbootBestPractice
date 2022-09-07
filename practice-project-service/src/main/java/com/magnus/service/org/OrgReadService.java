package com.magnus.service.org;

import com.magnus.domain.org.model.Org;
import com.magnus.domain.org.repository.OrgRepository;
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
public class OrgReadService {

    @Resource
    private OrgRepository repository;

    /**
     * 根据 ID 查找 
     * @param id
     * @return
     */
    public Org getOrgById(Long id){
        //todo 补充校验
        return repository.getById(id);
    };
}
