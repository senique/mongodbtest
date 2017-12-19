package com.mng.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import org.springframework.data.annotation.Id;

//@Entity
public class Dog
{
    private Long id;
    
    private String name;

    private Breed breed;
    
    private String remark;

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
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    @ManyToOne
    public Breed getBreed()
    {
        return breed;
    }

    public void setBreed(Breed breed)
    {
        this.breed = breed;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}
