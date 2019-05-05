package com.ebiz.service;

import com.ebiz.dao.EbizCompanyMapper;
import com.ebiz.model.EbizCompany;
import com.ebiz.model.EbizCompanyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EBizCompanyService {
    @Autowired
    private EbizCompanyMapper ebizCompanyMapper;

    public EbizCompany getEbizCompanyByCompanyName(String companyName) {
        EbizCompanyExample example = new EbizCompanyExample();
        example.createCriteria().andCompanyNameEqualTo(companyName);
        List<EbizCompany> ebizCompanies = ebizCompanyMapper.selectByExample(example);
        if (ebizCompanies.size() > 0) {
            return ebizCompanies.get(0);
        }
        return null;
    }

    public void add(EbizCompany record) {
        ebizCompanyMapper.insert(record);
    }
}
