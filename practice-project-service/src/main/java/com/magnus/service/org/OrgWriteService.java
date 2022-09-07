package com.magnus.service.org;

import com.magnus.domain.org.model.Org;
import com.magnus.domain.org.repository.OrgRepository;
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
public class OrgWriteService {

    @Resource
    private OrgRepository repository;

    /**
     * 保存 
     *
     * @param domain
     * @return
    */
    public Org createOrg(Org domain){
        //todo 补充校验
         return repository.create(domain);
    };

    /**
     * 根据 ID 删除某个 
     *
     * @param id
     * @return 删除是否成功
     */
    public boolean deleteOrgById(Long id){
        //todo 补充校验
        return repository.deleteById(id);
    };
}
