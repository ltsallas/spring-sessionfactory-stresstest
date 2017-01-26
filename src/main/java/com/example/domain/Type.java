package com.example.domain;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "TYPE")
@SequenceGenerator(name = "type_id_seq", sequenceName = "TYPE_ID_SQ", allocationSize = 1)
public class Type implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8293470196085195282L;
    /**
     * The id of the type
     */
    @Id
    @Column(name="ID")
    @GeneratedValue(generator = "type_id_seq")
    private long id;
    /**
     * The order number that specifies the order of appearance for the type
     */
    @Column(name="ORDERNR")
    private long orderNr;


    /**
     * Returns the iD of the type.
     *
     * @return Returns the iD.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of the type.
     *
     * @param id The id to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the order number of the type.
     *
     * @return Returns the order number of the type.
     */
    public long getOrderNr() {
        return orderNr;
    }

    /**
     * Sets the order number of the type.
     *
     * @param orderNr The order number to set.
     */
    public void setOrderNr(long orderNr) {
        this.orderNr = orderNr;
    }
}
