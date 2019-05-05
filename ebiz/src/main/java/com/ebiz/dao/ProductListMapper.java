package com.ebiz.dao;

import com.ebiz.model.ProductList;
import com.ebiz.model.ProductListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductListMapper {
    int countByExample(ProductListExample example);

    int deleteByExample(ProductListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductList record);

    int insertSelective(ProductList record);

    List<ProductList> selectByExample(ProductListExample example);

    ProductList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductList record, @Param("example") ProductListExample example);

    int updateByExample(@Param("record") ProductList record, @Param("example") ProductListExample example);

    int updateByPrimaryKeySelective(ProductList record);

    int updateByPrimaryKey(ProductList record);

    int insertGetPrimaryId(ProductList product);
}