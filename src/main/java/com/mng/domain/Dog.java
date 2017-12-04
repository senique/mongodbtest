package com.mng.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import org.springframework.data.annotation.Id;

//@Entity
public class Dog
{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "dog")
    @TableGenerator(
            name = "dog",
            table = "sequences",
            pkColumnName = "key",
            pkColumnValue = "dog",
            valueColumnName = "seed")
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }
    
    private Long id;
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    private String name;
    
    @ManyToOne
    public Breed getBreed()
    {
        return breed;
    }
    
    public void setBreed(Breed breed)
    {
        this.breed = breed;
    }
    
    private Breed breed;
    
    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    private String remark;
}
