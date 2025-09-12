package com.mapache.Enotes_API_Service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {

    @CreatedBy
    @Column(updatable = false)
    private Integer createdBy;
    @CreatedDate
    @Column(updatable = false)
    private Date createdOn;
    @LastModifiedBy
    @Column(insertable = false)
    private Integer updatedBy;
    @LastModifiedDate
    @Column(insertable = false)
    private Date updatedOn;
}

// esta clase se hace abstracta para que no se cree una tabla en la base de datos
// las demas clases que hereden de esta clase tendran estos atributos
// se usa @MappedSuperclass para que las demas clases hereden estos atributos
// se usa @CreatedBy, @CreatedDate, @LastModifiedBy, @LastModified para que se llenen automaticamente
// se usa @Column(updatable = false) para que no se puedan actualizar los campos creados
// se usa @Column(insertable = false) para que no se puedan insertar los campos actualizados
// ya que no se van a insertar ni actualizar manualmente, sino que se van a llenar automaticamente
// con el usuario que hizo la accion y la fecha de la accion
